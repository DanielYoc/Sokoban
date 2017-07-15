package searchable;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;

import data.Action;
import data.Searchable;
import data.State;
import model.data.*;
import model.policy.IPolicy;

/**
 * one path search helper class
 */
public class OnePathSearchable implements Searchable<OnePathState> {

	private OnePathState m_InitState;
	private OnePathState m_GoalState;
	private IPolicy m_Policy;

	public OnePathSearchable(IPolicy policy) {
		m_Policy = policy;
	}

	@Override
	public State<OnePathState> getInitialState() {
		return new State<OnePathState>(m_InitState);
	}

	public void setInitialState(OnePathState state) {
		m_InitState = state;
	}

	public void setGoalState(OnePathState state) {
		m_GoalState = state;
	}

	@Override
	public State<OnePathState> getGoalState() {
		return new State<OnePathState>(m_GoalState);
	}

	@Override
	public List<State<OnePathState>> getAllPossibleStates(State<OnePathState> state) {
		ArrayList<State<OnePathState>> possibleState = new ArrayList<State<OnePathState>>();
		Class<?>[][] board = state.getState().getBoard();
		Class<?>[][] rightBoard = cloneMatrix(board), leftBoard = cloneMatrix(board), upBoard = cloneMatrix(board),
				downBoard = cloneMatrix(board);
		if (m_Policy.move(rightBoard, "right")) {
			possibleState.add(new State<OnePathState>(new OnePathState(rightBoard), state, state.getCost() + 1,
					new Action<String>("move right")));
		}
		if (m_Policy.move(leftBoard, "left")) {
			possibleState.add(new State<OnePathState>(new OnePathState(leftBoard), state, state.getCost() + 1,
					new Action<String>("move left")));
		}
		if (m_Policy.move(upBoard, "up")) {
			possibleState.add(new State<OnePathState>(new OnePathState(upBoard), state, state.getCost() + 1,
					new Action<String>("move up")));
		}
		if (m_Policy.move(downBoard, "down")) {
			possibleState.add(new State<OnePathState>(new OnePathState(downBoard), state, state.getCost() + 1,
					new Action<String>("move down")));
		}
		return possibleState;
	}

	private Class<?>[][] cloneMatrix(Class<?>[][] matrix) {
		Class<?>[][] clone = new Class<?>[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			clone[i] = matrix[i].clone();
		return clone;
	}

	@Override
	public boolean isGoalSatisfied(State<OnePathState> state) {
		Class<?>[][] goalBoard = m_GoalState.getBoard();
		Class<?>[][] stateBoard = state.getState().getBoard();
		for (int y = 0; y < goalBoard.length; y++)
			for (int x = 0; x < goalBoard[y].length; x++) {
				if (Box.class.equals(goalBoard[y][x]) && Box.class.equals(stateBoard[y][x]) == false)
					return false;
			}
		return true;
	}

	@Override
	public List<State<OnePathState>> getAllPossibleStates() {
		try {
			throw new NoSuchObjectException("BFS not imploment this option");
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}
		return null;
	}

}
