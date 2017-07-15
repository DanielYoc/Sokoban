package model.serverProtocol;

import java.io.Serializable;

public class SolutionRespone implements Serializable {

	private static final long serialVersionUID = -1214205854630105070L;

	private boolean m_Solvable;

	private String[] m_Actions;

	public boolean getSolvable() {
		return m_Solvable;
	}

	public void setSolvable(boolean solvable) {
		this.m_Solvable = solvable;
	}

	public String[] getActions() {
		return m_Actions;
	}

	public void setActions(String[] actions) {
		m_Actions = actions;
	}
}
