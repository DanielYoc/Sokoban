package plannable;

import java.util.HashSet;
import java.util.Set;

import predicates.IPredicate;
import predicates.NotPredicate;

public class State {
	private HashSet<IPredicate> m_Predicates = new HashSet<IPredicate>();
	private HashSet<NotPredicate> m_Forbidden = new HashSet<NotPredicate>();

	public Set<IPredicate> getPredicates() {
		return m_Predicates;
	}

	public void addPredicate(IPredicate p) {
		m_Predicates.add(p);
	}

	public void removePredicate(IPredicate p) {
		m_Predicates.remove(p);
	}

	public boolean isSatisfied(Set<IPredicate> predicates) {
		for (IPredicate p : predicates) {
			if(m_Predicates.contains(p)==false)
				return false;
		}
		return true;
	}

	public void update(Action a) {
		for (IPredicate p : a.getEffectList()) {
			this.addPredicate(p);
		}
	}

	public boolean isSatisfied(IPredicate predicate) {
		for (IPredicate p : m_Predicates)
			if (p.equals(predicate))
				return true;
		return false;
	}

	public void addForbidden(NotPredicate predicate) {
		m_Forbidden.add(predicate);
	}

}
