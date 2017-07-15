package model;

import java.util.*;

import model.data.*;
import model.policy.IPolicy;
import model.serverProtocol.SolutionRespone;

public class MyModel extends Observable implements IModel {
	private Level m_Level;
	private IPolicy m_Policy;
	private Timer m_Timer;
	private boolean m_IsSolved;
	private RecordsGateway m_RecordsGateway;
	private SolutionsGateway m_SolutionGateway;
	private boolean m_IsSolutionProvide;

	public MyModel(IPolicy policy, RecordsGateway recordsGateway, SolutionsGateway solutionGateway) {
		m_Policy = policy;
		m_RecordsGateway = recordsGateway;
		m_Timer = null;
		m_SolutionGateway = solutionGateway;
	}

	/**
	 * move the player to the given direction
	 */
	public void move(String direction) throws Exception {
		if (m_Level == null || m_IsSolved)
			return;

		if (m_Policy.move(m_Level, direction))
			m_Level.setStepsCount(m_Level.getStepsCount() + 1);

		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("display");
		this.notifyObservers(params);
		if (m_Policy.isSolved(m_Level)) {
			m_Timer.cancel();
			m_IsSolved = true;
			if (m_IsSolutionProvide == false) {
				List<String> solvedParams = new LinkedList<String>();
				solvedParams.add("win");
				this.setChanged();
				this.notifyObservers(solvedParams);
			}
		}
	}

	/**
	 * Set the given level as the current level of the model
	 */
	public void setLevel(Level level) {
		m_Level = level;
		m_IsSolutionProvide = false;
		if (m_Timer != null)
			m_Timer.cancel();
		m_IsSolved = m_Policy.isSolved(level);
		if (m_IsSolved == false) {
			m_Timer = new Timer();
			m_Timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if (m_Level != null) {
						m_Level.setSecondsCount(m_Level.getSecondsCount() + 1);
						setChanged();
						List<String> params = new LinkedList<String>();
						params.add("SecondsCountChange");
						params.add(String.valueOf(m_Level.getSecondsCount()));
						notifyObservers(params);
					}
				}
			}, 0, 1000);
		}
		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("display");
		this.notifyObservers(params);
	}

	/**
	 * Search and return by observers records
	 */
	public void searchRecords(long id, RecordQuery query) {
		List<Object> params = new LinkedList<Object>();
		params.add("setRecords");
		params.add(id);
		params.add(m_RecordsGateway.search(query));
		this.setChanged();
		this.notifyObservers(params);
	}

	@Override
	public Level getLevel() {
		return m_Level;
	}

	/**
	 * Save the user record of the current level
	 */
	@Override
	public void saveRecord(String userName) {
		m_RecordsGateway.saveRecored(
				new Record(m_Level.getName(), m_Level.getSecondsCount(), userName, m_Level.getStepsCount()));
	}

	public SolutionRespone getSolution() {
		if (m_Level == null)
			return null;
		m_IsSolutionProvide = true;
		return m_SolutionGateway.GetSolution(m_Level);
	}
}
