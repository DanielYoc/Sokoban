package predicates;

public class NotPredicate implements IPredicate {

	private IPredicate m_BasePredicate;

	public NotPredicate(IPredicate basePredicate) {
		m_BasePredicate = basePredicate;
	}

	@Override
	public boolean isSatisfied(IPredicate predicate) {
		return m_BasePredicate.equals(predicate) == false;
	}

	public boolean equals(IPredicate p) {
		return p instanceof NotPredicate && ((NotPredicate) p).m_BasePredicate.equals(m_BasePredicate);
	}

	@Override
	public String toString() {
		return "Not(" + m_BasePredicate.toString() + ")";
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
