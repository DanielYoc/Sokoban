package model;

import model.data.Level;

public interface IModel {
	
	public void move(String dirction) throws Exception;
		
	public Level getLevel();
	
	public void setLevel(Level level);
}
