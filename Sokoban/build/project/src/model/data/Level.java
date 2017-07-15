package model.data;

import java.io.Serializable;

import commons.ILevelReadOnly;

public class Level implements Serializable,ILevelReadOnly {

	private static final long serialVersionUID = 2L;

	private String m_Name;
	private int m_StepsCount;
	private int m_SecondsCount;
	private Player m_SelectedPlayer;
	private Cell[][] m_Board;

	public Level() {
	}

	public Level(String name, Cell[][] board, Player selectedPlayer) {
		m_Name = name;
		m_Board = board;
		m_SelectedPlayer = selectedPlayer;
	}

	public String getName() {
		return m_Name;
	}

	public void setName(String name) {
		this.m_Name = name;
	}
	
	public int getStepsCount() {
		return m_StepsCount;
	}

	public void setStepsCount(int stepsCount) {
		this.m_StepsCount = stepsCount;
	}

	public int getSecondsCount() {
		return m_SecondsCount;
	}

	public void setSecondsCount(int secondsCount) {
		this.m_SecondsCount = secondsCount;
	}

	public Cell[][] getBoard() {
		return m_Board;
	}

	public void setBoard(Cell[][] board) {
		this.m_Board = board;
	}
	public void setSelectedPlayer(Player player) {
		m_SelectedPlayer = player;
	}

	public Player getSelectedPlayer() {
		return m_SelectedPlayer;
	}
}
