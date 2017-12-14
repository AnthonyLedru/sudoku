package sudokumodel;

public class SingleGroupValue extends HintSolver {

	public SingleGroupValue() {
		super();
	}
	
	public boolean hasHint(Sudoku model) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = 0; k < 9; k++) {
					if(model.getBloc(i).getCell(j).getValue() == k)
						return true;
				}
			}
		}
	return false;
	}

	private int checkGroup(Group g) {
		return 4;
	}

	public boolean doHint(Sudoku model) {
		return false;
	}
}
