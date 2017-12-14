package sudokumodel;

public interface SudokuModel {
	public void init(String grid);
	public boolean isInit();
	public boolean isFull();
	public boolean isValide();
	public CellValue getCellValue(int cellNumber);
	public void reset();
	public boolean setValue(int value, int cellNumber, boolean checkError);
	public void computeCandidates();
	public void toggleCandidate(int chiffre, int cellNumber);
	public boolean solve();
	public String hint();
}