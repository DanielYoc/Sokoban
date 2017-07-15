package predicates;

import java.util.Arrays;
import java.util.HashSet;

public class AndPredicate implements IPredicate {
	private HashSet<IPredicate> m_Prediates;

	public AndPredicate(IPredicate... predicates) {
		m_Prediates = new HashSet<>(Arrays.asList(predicates));
	}

	@Override
	public boolean isSatisfied(IPredicate predicate) {
		if (predicate instanceof AndPredicate) {
			AndPredicate andP = (AndPredicate) predicate;
			for (IPredicate item : andP.m_Prediates) {
				if (m_Prediates.contains(item) == false)
					return false;
			}
			return true;
		} else {
			return m_Prediates.size() == 0 || m_Prediates.contains(predicate);
		}
	}

	public boolean equals(IPredicate p) {
		if (p instanceof AndPredicate) {
			AndPredicate andP = (AndPredicate) p;
			if (andP.m_Prediates.size() != m_Prediates.size())
				return false;
			for (IPredicate item : andP.m_Prediates) {
				if (m_Prediates.contains(item) == false)
					return false;
			}
			return true;
		} else {
			return m_Prediates.size() == 1 && m_Prediates.contains(p);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("And( ");
		for (IPredicate p : m_Prediates) {
			builder.append(p.toString() + ", ");
		}
		builder.append(")");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
