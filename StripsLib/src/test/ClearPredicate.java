package test;

import predicates.IPredicate;

public class ClearPredicate<T> implements IPredicate {

	private T m_Top;

	public ClearPredicate(T top) {
		m_Top = top;
	}

	@Override
	public boolean isSatisfied(IPredicate predicate) {
		return equals(predicate);
	}

	public boolean equals(IPredicate p) {
		return p instanceof ClearPredicate<?> && ((ClearPredicate<?>) p).m_Top.equals(p);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return "Clear(" + m_Top.toString() + ")";
	}
}
