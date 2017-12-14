package sudokumodel;

import java.util.ArrayList;

public class Cell implements CellValue {

	private int value;
	private boolean intialValue = false;
	private boolean[] candidates;
	private int cellNumber;
	private Group maLigne;
	private Sudoku model;
	private Group maColonne;
	private Group monBloc;

	public Cell(Sudoku sudokuModel, Group ligne, Group colonne, Group bloc, int cellNumber) {
		this.cellNumber = cellNumber;
		this.model = sudokuModel;
		this.maLigne = ligne;
		this.maColonne = colonne;
		this.monBloc = bloc;
		this.maLigne.addCell(this);
		this.maColonne.addCell(this);
		this.monBloc.addCell(this);
		this.candidates = new boolean[10];

	}

	public void setInitialValue(int value) {
		this.value = value;
		this.intialValue = true;
	}

	public Group getLine() {
		return this.maLigne;
	}

	public Group getColumn() {
		return this.maColonne;
	}

	public Group getBloc() {
		return this.monBloc;
	}

	public boolean isInitialValue() {
		boolean res = false;
		if (this.value != 0) {
			if (this.intialValue == true) {
				res = true;
			}
		}
		return res;
	}

	public int getValue() {
		return this.value;
	}

	public boolean isCandidate(int value) {
		return this.candidates[value];
	}

	public ArrayList<Integer> getCandidates() {
		ArrayList<Integer> liste = new ArrayList<Integer>();
		for (int i = 0; i < this.candidates.length; i++) {
			if (this.candidates[i] == true) {
				liste.add(i);
			}
		}
		return liste;
	}

	public void computeCandidates() {
		for (int i = 0; i < this.candidates.length; i++) {
			this.candidates[i] = this.maLigne.isCandidate(i) && this.maColonne.isCandidate(i)
					&& this.monBloc.isCandidate(i);
		}
	}

	public void reset() {
		if (!this.isInitialValue()) {
			this.clearValue(true);
		}
	}

	public void unsetCandidate(int value) {
		this.candidates[value] = false;

	}

	public boolean setValue(int value, boolean checkError) {
		this.intialValue = false;
		boolean res = false;
		if (checkError == false) {
			this.value = value;
			res = true;
		}
		if (this.value == 0) {
			this.clearValue(true);
		}
		return res;
	}

	public boolean clearValue(boolean computeGroupCandidates) {
		boolean res = false;
		if (!this.intialValue) {
			this.value = 0;
			res = true;
		}
		if (computeGroupCandidates) {
			this.computeCandidates();
			this.maLigne.computeCandidates();
			this.maColonne.computeCandidates();
			this.monBloc.computeCandidates();
		}
		return res;
	}

	public boolean isError() {
		boolean res = false;
		if (this.value != 0) {
			this.maLigne.checkError(this.value);
			if (this.maLigne.checkError(this.value) || this.maColonne.checkError(this.value)
					|| this.monBloc.checkError(this.value)) {
				res = true;
			}
		}
		return res;
	}

	public void toggleCandidate(int chiffre) {
		this.candidates[chiffre] = !this.candidates[chiffre];
	}

	public int getNumber() {
		return this.cellNumber;
	}

}
