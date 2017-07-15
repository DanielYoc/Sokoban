package predicates;

public class OnPredicate implements IPredicate {

	private Object m_Base;
	private Object m_Above;

	public OnPredicate(Object above, Object base) {
		m_Base = base;
		m_Above = above;
	}

	@Override
	public boolean isSatisfied(IPredicate predicate) {
		return equals(predicate);
	}

	public boolean equals(IPredicate p) {
		return p instanceof OnPredicate && ((OnPredicate) p).m_Base.equals(m_Base)
				&& ((OnPredicate) p).m_Above.equals(m_Above);
	}

	@Override
	public String toString() {
		return "On(" + m_Base.toString() + ", " + m_Above.toString() + ")";
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}