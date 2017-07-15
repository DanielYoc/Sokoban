package commons;

public interface ILevelReadOnly {

	public ICellReadOnly[][] getBoard();

	public int getStepsCount();

	public int getSecondsCount();
	
	public String getName();
}
