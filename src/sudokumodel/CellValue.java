package sudokumodel;

import java.util.ArrayList;

public interface CellValue {
	
	public boolean isInitialValue();
	public int getValue();
	public ArrayList<Integer> getCandidates();
	public boolean isError();
	
}
