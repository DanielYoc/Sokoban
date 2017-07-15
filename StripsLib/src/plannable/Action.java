package plannable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import planners.IStackItem;
import predicates.IPredicate;
import predicates.NotPredicate;

public abstract class Action implements IStackItem{

	private List<IPredicate> m_Preconditions;
	private ArrayList<IPredicate> m_Effects;
	private HashSet<NotPredicate> m_IllegalAssignments;

	public Action() {
		m_Preconditions = new ArrayList<IPredicate>();
		m_Effects = new ArrayList<IPredicate>();
		m_IllegalAssignments = new HashSet<NotPredicate>();
	}
	
	public Set<NotPredicate> getIllegalAssignments() {
		return m_IllegalAssignments;
	}

	public List<IPredicate> getPreconditions() {
		return m_Preconditions;
	}

	public List<IPredicate> getEffectList() {
		return m_Effects;
	}
	
	public abstract void execute(State state);

}
