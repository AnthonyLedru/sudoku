package sudokumodel;

import java.util.ArrayList;

public class Sudoku implements SudokuModel {

	private boolean grilleInitialisee;
	private ArrayList<Group> blocs;
	private ArrayList<Cell> cells;
	private ArrayList<Group> columns;
	private ArrayList<HintSolver> hints;
	private ArrayList<Group> lines;

	public Sudoku() {
		this.grilleInitialisee = false;
		this.cells = new ArrayList<Cell>(81);
		this.blocs = new ArrayList<Group>(9);
		this.columns = new ArrayList<Group>(9);
		this.lines = new ArrayList<Group>(9);
		this.hints = new ArrayList<HintSolver>();
	}

	public void init(String grid) {
		int ligne = 0;
		int col = 0;
		int bloc = 0;

		for (int i = 0; i < 9; i++) {
			this.blocs.add(new Group(i));
			this.columns.add(new Group(i));
			this.lines.add(new Group(i));
		}

		for (int i = 0; i < 81; i++) {

			if (col >= 9) {
				col = 0;
				ligne++;
			}

			if (col % 3 == 0 && i != 0) {
				bloc++;
				if (ligne < 3) {
					if (bloc == 3)
						bloc = 0;
				} else if (2 < ligne && ligne < 6) {
					if (bloc == 6)
						bloc = 3;
				} else if (ligne > 5) {
					if (bloc == 9)
						bloc = 6;
				}
			}
			this.cells.add(new Cell(this, this.lines.get(ligne), this.columns.get(col), this.blocs.get(bloc), i));
			this.cells.get(i).setInitialValue(Integer.parseInt("" + grid.charAt(i)));
			col++;
		}

		this.reset();
		this.grilleInitialisee = true;
	}

	public boolean isInit() {
		return this.grilleInitialisee;
	}

	public boolean isFull() {
		for (int i = 0; i < this.cells.size(); i++) {
			if (this.cells.get(i).getValue() == 0) {
				return false;
			}
		}
		return true;
	}

	public Group getLine(int lineNumber) {
		return this.lines.get(lineNumber);
	}

	public Group getColumn(int colNumber) {
		return this.columns.get(colNumber);
	}

	public Group getBloc(int blocNumber) {
		return this.blocs.get(blocNumber);
	}

	public Cell getCell(int cellNumber) {
		return this.cells.get(cellNumber);
	}

	public CellValue getCellValue(int cellNumber) {
		return this.cells.get(cellNumber);
	}

	public void reset() {
		for (int i = 0; i < this.cells.size(); i++)
			this.cells.get(i).reset();
		for (int i = 0; i < this.blocs.size(); i++)
			this.blocs.get(i).resetCandidates();
		for (int i = 0; i < this.columns.size(); i++)
			this.columns.get(i).resetCandidates();
		for (int i = 0; i < this.lines.size(); i++)
			this.lines.get(i).resetCandidates();
	}

	public boolean setValue(int value, int cellNumber, boolean checkError) {
		boolean res = false;
		if (!checkError && !this.cells.get(cellNumber).isInitialValue()) {
			this.cells.get(cellNumber).setValue(value, checkError);
			res = true;
		}
		if (value == 0) {
			if (!this.cells.get(cellNumber).isInitialValue())
				this.cells.get(cellNumber).clearValue(true);
			res = true;
		}
		this.cells.get(cellNumber).computeCandidates();
		this.cells.get(cellNumber).getColumn().computeCandidates();
		this.cells.get(cellNumber).getBloc().computeCandidates();
		this.cells.get(cellNumber).getLine().computeCandidates();
		return res;
	}

	public void computeCandidates() {
		for (int i = 0; i < this.cells.size(); i++)
			this.cells.get(i).computeCandidates();
	}

	public boolean isValide() {
		boolean res = false;
		boolean tabF[] = { false, false, false, false, false, false, false, false, false };
		boolean tabT[] = { false, false, false, false, false, false, false, false, false };
		boolean tab[];

		for (int i = 0; i < this.cells.size() && res; i++)
			if (this.cells.get(i).getValue() == 0)
				res = false;

		for (int i = 0; i < this.lines.size() && res; i++) {
			tab = tabF;
			for (int j = 0; j < 9; i++) {
				tab[this.lines.get(i).getCell(j).getValue()] = true;
			}
			if (tab != tabT)
				res = false;
		}

		for (int i = 0; i < this.columns.size() && res; i++) {
			tab = tabF;
			for (int j = 0; j < 9; i++) {
				tab[this.columns.get(i).getCell(j).getValue()] = true;
			}
			if (tab != tabT)
				res = false;
		}

		for (int i = 0; i < this.blocs.size() && res; i++) {
			tab = tabF;
			for (int j = 0; j < 9; i++) {
				tab[this.blocs.get(i).getCell(j).getValue()] = true;
			}
			if (tab != tabT)
				res = false;
		}
		return true;

	}

	public void toggleCandidate(int chiffre, int cellNumber) {
		this.getCell(cellNumber).toggleCandidate(chiffre);
	}

	public boolean solve() {
		for (int i = 0; i < this.hints.size(); i++) {
			this.hints.get(i).doHint(this);
		}
		return this.sudokuBacktracking(0);
	}

	private boolean sudokuBacktracking(int cellNumber) {
		ArrayList<Integer> candidats = new ArrayList<Integer>();
		if (cellNumber == 81) {
			return true;
		} else if (this.cells.get(cellNumber).isInitialValue()) {
			sudokuBacktracking(cellNumber + 1);
		} else {
			candidats = this.cells.get(cellNumber).getCandidates();
			for (int i = 0; i < candidats.size() - 1; i++) {
				this.cells.get(cellNumber).setValue(candidats.get(i + 1), true);
				if (i == candidats.size() - 1) {
					this.cells.get(cellNumber).clearValue(true);
					break;
				}

			}
			sudokuBacktracking(cellNumber + 1);
		}
		return false;
	}

	public String hint() {
		for (int i = 0; i < this.hints.size(); i++) {
			if (this.hints.get(i).hasHint(this)) {
				return this.hints.get(i).getExplanation();
			}
		}
		return null;
	}

}
