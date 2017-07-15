package model.policy;

import model.data.Box;
import model.data.Cell;
import model.data.Level;
import model.data.Player;
import model.data.Position;

public class MySokobanPolicy implements IPolicy {

	public boolean move(Level level, String direction) {
		Position pos = level.getSelectedPlayer().getPosition();

		switch (direction.toLowerCase()) {
		case "up":
			return moveInDirection(level, pos, new Position(pos.getX(), pos.getY() - 1),
					new Position(pos.getX(), pos.getY() - 2));
		case "down":
			return moveInDirection(level, pos, new Position(pos.getX(), pos.getY() + 1),
					new Position(pos.getX(), pos.getY() + 2));
		case "left":
			return moveInDirection(level, pos, new Position(pos.getX() - 1, pos.getY()),
					new Position(pos.getX() - 2, pos.getY()));
		case "right":
			return moveInDirection(level, pos, new Position(pos.getX() + 1, pos.getY()),
					new Position(pos.getX() + 2, pos.getY()));
		default:
			return false;
		}
	}

	private boolean moveInDirection(Level level, Position currPlayerPos, Position nextPlayerPos, Position nextBoxPos) {
		Cell[][] board = level.getBoard();
		Cell currCell = board[currPlayerPos.getY()][currPlayerPos.getX()];
		Cell nextCell = board[nextPlayerPos.getY()][nextPlayerPos.getX()];

		if (nextCell.hasWall())
			return false;
		else if (!(nextCell.hasBox())) {
			Player player = currCell.getPlayer();
			currCell.getElements().remove(player);
			nextCell.getElements().add(player);
			level.getSelectedPlayer().setPosition(nextPlayerPos);
			return true;
		} else {
			Cell nextBoxCell = board[nextBoxPos.getY()][nextBoxPos.getX()];
			if (nextBoxCell.hasBox() || nextBoxCell.hasWall())
				return false;
			else {
				Player player = currCell.getPlayer();
				currCell.getElements().remove(player);
				nextCell.getElements().add(player);
				level.getSelectedPlayer().setPosition(nextPlayerPos);
				Box box = nextCell.getBox();
				nextCell.getElements().remove(box);
				nextBoxCell.getElements().add(box);
				box.setPosition(nextBoxPos);
				return true;
			}
		}
	}

	public Boolean isSolved(Level level){
		for (Cell[] row : level.getBoard())
			for (Cell cell : row) {
				if (cell.hasBox() != cell.hasTarget())
					return false;
			}
		return true;
	}
}
