package model;

import java.util.*;

import model.data.*;
import model.policy.IPolicy;
import plannable.Action;
import planners.CantSolvedException;
import planners.Strips;
import strips.Heuristic;
import strips.SokoSolverAdapter;

public class MyModel extends Observable implements IModel {
	private Level m_Level;
	private IPolicy m_Policy;
	private boolean m_IsWin;

	public MyModel(IPolicy policy) {
		m_Policy = policy;
	}

	public void move(String direction) throws Exception {
		if (m_Level == null || m_IsWin)
			return;

		if (m_Policy.move(m_Level, direction))
			m_Level.setStepsCount(m_Level.getStepsCount() + 1);

		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("display");
		this.notifyObservers(params);
		if (m_Policy.isSolved(m_Level)) {
			m_IsWin = true;
			List<String> solvedParams = new LinkedList<String>();
			solvedParams.add("win");
			this.setChanged();
			this.notifyObservers(solvedParams);
		}
	}

	public void setLevel(Level level) {
		m_Level = level;
		m_IsWin = m_Policy.isSolved(level);
		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("display");
		this.notifyObservers(params);
	}

	@Override
	public Level getLevel() {
		return m_Level;
	}

	@Override
	public List<String> plan() throws CantSolvedException {
		ArrayList<String> actionsCommand = null;
		SokoSolverAdapter solver = new SokoSolverAdapter(m_Level);
		Heuristic heuristic = new Heuristic(m_Level, m_Policy);
		Strips strips = new Strips(solver, heuristic);
		try {
			List<Action> actions = strips.computePlan();
			actionsCommand = new ArrayList<>();
			for (Action ac : actions)
				actionsCommand.add(ac.toString());
		} catch (CantSolvedException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionsCommand;
	}

}
