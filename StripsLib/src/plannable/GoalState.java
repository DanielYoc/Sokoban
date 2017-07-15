package plannable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import planners.IStackItem;
import predicates.IPredicate;

public class GoalState implements IStackItem {
	private Set<IPredicate> m_Predicates;

	public GoalState(List<IPredicate> predicates) {
		m_Predicates = new HashSet<IPredicate>(predicates);
	}

	public Set<IPredicate> getPredicates() {
		return m_Predicates;
	}
}
