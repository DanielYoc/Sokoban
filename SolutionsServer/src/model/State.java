package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * The class represent level state
 */
@Entity(name = "States")
public class State implements Serializable {

	private static final long serialVersionUID = 125037259352282081L;

	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HashCode")
	private int hashCode;

	@Column(name = "Solvedable")
	private boolean solvedable;

	@Transient
	private Action[] m_Actions;

	public int getHashCode() {
		return this.hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public boolean getSolvedable() {
		return this.solvedable;
	}

	public void setSolvedable(boolean solvedable) {
		this.solvedable = solvedable;
	}

	public Action[] getActions() {
		return m_Actions;
	}

	public void setActions(Action[] actions) {
		this.m_Actions = actions;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(this.solvedable);
		if (this.solvedable && m_Actions != null)
			for (Action a : m_Actions){
			result.append(",");
				result.append(a.getActionType());
			}
		return result.toString();
	}
}
