package model.policy;

import model.data.GameElement;
import model.data.Level;
import model.data.Position;

public interface IPolicy {

	public boolean move(Level level, String direction);

	public Boolean isSolved(Level level);

	public boolean isLegalMove(GameElement[][] board, Position currPlayerPos, Position nextPlayerPos);
	
	public boolean move(Class<?>[][] board, String direction);
}
