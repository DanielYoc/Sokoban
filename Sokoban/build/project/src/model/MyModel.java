package model;

import java.util.*;

import model.data.*;
import model.policy.IPolicy;

public class MyModel extends Observable implements IModel {
	private Level m_Level;
	private IPolicy m_Policy;
	private Timer m_Timer;
	private boolean m_IsSolved;

	public MyModel(IPolicy policy) {
		m_Policy = policy;
		m_Timer = null;
	}

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
			List<String> solvedParams = new LinkedList<String>();
			solvedParams.add("solved");
			this.setChanged();
			this.notifyObservers(solvedParams);
		}
	}

	public void setLevel(Level level) {
		m_Level = level;
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

	@Override
	public Level getLevel() {
		return m_Level;
	}
}
