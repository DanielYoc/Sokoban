package model;

import java.util.List;

import model.data.Level;
import planners.CantSolvedException;

public interface IModel {

	public void move(String dirction) throws Exception;

	public Level getLevel();

	public void setLevel(Level level);
	
	public List<String> plan() throws CantSolvedException;
}
