package model;

import model.data.Level;
import model.data.RecordQuery;
import model.serverProtocol.SolutionRespone;

public interface IModel {

	public void move(String dirction) throws Exception;

	public Level getLevel();

	public void setLevel(Level level);

	public void searchRecords(long id, RecordQuery query);

	public void saveRecord(String userName);

	public SolutionRespone getSolution();
}
