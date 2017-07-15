package model.data;

import java.io.Serializable;

/**
 * Box GameElement
 */
public class Box extends GameElement implements Serializable, IMovable {
	private static final long serialVersionUID = 3L;

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Box) {
			return getPosition().equals(((GameElement) obj).getPosition());
		}
		return false;
	}

	@Override
	public String toString() {
		return "Box , Position: " + getPosition().toString();
	}
}
