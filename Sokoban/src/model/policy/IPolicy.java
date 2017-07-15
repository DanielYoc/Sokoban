package model.policy;

import model.data.Level;

public interface IPolicy {
	
	public boolean move(Level level, String direction);
	
	public Boolean isSolved(Level level);
}
