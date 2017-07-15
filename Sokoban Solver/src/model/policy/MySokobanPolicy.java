package model.policy;


import model.data.Box;
import model.data.Cell;
import model.data.GameElement;
import model.data.Level;
import model.data.Player;
import model.data.Position;
import model.data.Wall;

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

	public boolean move(Class<?>[][] board, String direction) {
		Position pos = getPlayerPosition(board);
		switch (direction.toLowerCase()) {
		case "up":
			return moveInDirection(board, pos, new Position(pos.getX(), pos.getY() - 1),
					new Position(pos.getX(), pos.getY() - 2));
		case "down":
			return moveInDirection(board, pos, new Position(pos.getX(), pos.getY() + 1),
					new Position(pos.getX(), pos.getY() + 2));
		case "left":
			return moveInDirection(board, pos, new Position(pos.getX() - 1, pos.getY()),
					new Position(pos.getX() - 2, pos.getY()));
		case "right":
			return moveInDirection(board, pos, new Position(pos.getX() + 1, pos.getY()),
					new Position(pos.getX() + 2, pos.getY()));
		default:
			return false;
		}
	}

	private Position getPlayerPosition(Class<?>[][] board) {
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				if (Player.class.equals(board[i][j]))
					return new Position(j, i);
		return null;
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

	private boolean moveInDirection(Class<?>[][] board, Position currPlayerPos, Position nextPlayerPos,
			Position nextBoxPos) {
		Class<?> nextCell = board[nextPlayerPos.getY()][nextPlayerPos.getX()];
		if (Wall.class.equals(nextCell))
			return false;

		if (Box.class.equals(nextCell) == false) {
			board[currPlayerPos.getY()][currPlayerPos.getX()] = null;
			board[nextPlayerPos.getY()][nextPlayerPos.getX()] = Player.class;
			return true;
		}
		Class<?> nextBoxCell = board[nextBoxPos.getY()][nextBoxPos.getX()];

		if (Box.class.equals(nextBoxCell) || Wall.class.equals(nextBoxCell))
			return false;

		board[currPlayerPos.getY()][currPlayerPos.getX()] = null;
		board[nextBoxPos.getY()][nextBoxPos.getX()] = Box.class;
		board[nextPlayerPos.getY()][nextPlayerPos.getX()] = Player.class;

		return true;

	}	

	public boolean isLegalMove(GameElement[][] board, Position currPlayerPos, Position nextPlayerPos) {

		GameElement nextCell = board[nextPlayerPos.getY()][nextPlayerPos.getX()];
		if (nextCell instanceof Wall)
			return false;
		if (nextCell instanceof Box) {
			int x = nextPlayerPos.getX() + (nextPlayerPos.getX() - currPlayerPos.getX());
			int y = nextPlayerPos.getY() + (nextPlayerPos.getY() - currPlayerPos.getY());
			GameElement boxNextCell = board[x][y];
			return boxNextCell instanceof Box == false && boxNextCell instanceof Wall == false;

		} else
			return true;
	}

	public Boolean isSolved(Level level) {
		for (Cell[] row : level.getBoard())
			for (Cell cell : row) {
				if (cell.hasBox() != cell.hasTarget())
					return false;
			}
		return true;
	}
}
