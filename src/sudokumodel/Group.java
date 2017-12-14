package sudokumodel;

import java.util.ArrayList;

public class Group {

	private boolean[] candidates = new boolean[10];
	private int groupNumber;
	private ArrayList<Cell> cells;

	public Group(int groupNumber) {
		this.groupNumber = groupNumber;
		for(int i = 0; i<candidates.length;i++){
			candidates[i] = true;
		}
		this.cells = new ArrayList<Cell>();
	}

	public void addCell(Cell cell) {
		this.cells.add(cell);
	}

	public Cell getCell(int cellNumber) {
		return this.cells.get(cellNumber);
	}

	public int getGroupNumber() {
		return this.groupNumber;
	}

	public void resetCandidates() {
		for (int i = 0; i < this.candidates.length; i++) {
			this.candidates[i] = true;
		}
	}

	public void computeCandidates() {
		this.resetCandidates();
		for(int i =0;i<candidates.length;i++){
			for(int j=0;j<this.cells.size();j++){
				if(i == this.cells.get(j).getValue()){
				candidates[i] = false;
				}
			}
		}
		for (int i = 0; i < this.cells.size(); i++) {
			this.cells.get(i).computeCandidates();
		}
	}

	public void unsetCandidate(int value) {
		this.candidates[value] = false;
	}

	public boolean isCandidate(int value) {
		return this.candidates[value];
	}

	public boolean checkError(int value) {
		int compteur = 0;
		boolean res = false;
		for(int i = 0; i < this.cells.size(); i++){
			if(this.cells.get(i).getValue() == value){
				compteur ++;
			}
			if(compteur == 2){
				return true;
			}
		}
		return res;
		
	}

	public ArrayList<Cell> getCellsCandidate(int value) {
		ArrayList<Cell> list = new ArrayList<Cell>();
		for (int i = 0; i < this.cells.size(); i++) {
			for (int j = 0; j < this.cells.get(i).getCandidates().size(); i++)
				if (this.cells.get(i).getCandidates().get(j) == value)
					list.add(this.cells.get(i));

		}
		return list;
	}

}
