package searchable;

/**
 * represent state search for path from one box to one target
 */
public class OnePathState {
	private Class<?>[][] m_Board;
	private Integer m_HashCode = null;

	public OnePathState(Class<?>[][] board) {
		m_Board = board;
	}

	public Class<?>[][] getBoard() {
		return m_Board;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Class<?>[] row : m_Board) {
			builder.append("\n");
			for (Class<?> ty : row)
				builder.append(ty != null ? ty.toString() : "Floor");
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		if (m_HashCode == null)
			m_HashCode = toString().hashCode();
		return m_HashCode;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof OnePathState && ((OnePathState) obj).hashCode() == hashCode();
	}
}
