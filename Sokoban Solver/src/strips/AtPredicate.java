package strips;

import model.data.Position;
import predicates.IPredicate;

public class AtPredicate implements IPredicate {

	private Position m_Position;
	private Class<?> m_ElementType;

	public AtPredicate(Class<?> elementType, Position position) {
		m_Position = position;
		m_ElementType = elementType;
	}

	@Override
	public boolean isSatisfied(IPredicate predicate) {
		return equals(predicate);
	}

	@Override
	public boolean equals(Object p) {
		if (p instanceof AtPredicate) {
			AtPredicate predicate = (AtPredicate) p;
			return predicate.m_Position.equals(m_Position) && predicate.m_ElementType.equals(m_ElementType);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Position: " + m_Position.toString() + " Game element: " + m_ElementType.toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public Position getPosition() {
		return m_Position;
	}

	public Class<?> getElementType() {
		return m_ElementType;
	}

}
