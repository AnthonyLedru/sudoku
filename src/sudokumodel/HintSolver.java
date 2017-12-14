package sudokumodel;

public abstract class HintSolver {
	protected String explanation;
	
	
	public abstract boolean hasHint(Sudoku model);
	
	/* La méthode hasHint est appelé avec un nouveau model en paramètre: ??? */
	public String getExplanation(){
		if(this.hasHint(new Sudoku())){
		return this.explanation;
		}
		else{
			return null;
		}
	}
	
	public abstract boolean doHint(Sudoku model);
}