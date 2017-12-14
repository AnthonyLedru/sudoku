package sudokumodel;

public class SingleCellValue extends HintSolver {

	public boolean hasHint(Sudoku model) {
		boolean res = false;
		for (int i = 0; i < 81; i++) {
			if (model.getCell(i).getCandidates().size() == 1) {
				res = true;
				this.explanation = "La cellule ligne " + model.getCell(i).getLine() + " et colonne "
						+ model.getCell(i).getColumn() + " ne possède qu'une seul solution.";
			}
		}
		return res;
	}

	public boolean doHint(Sudoku model) {
		boolean res = this.hasHint(model);
		if (res) {
			for (int i = 0; i < 81; i++) {
				if (model.getCell(i).getCandidates().size() == 1) {
					model.getCell(i).setValue(model.getCell(i).getCandidates().get(0), true);
				}
			}
		}
		return res;
	}
}
