package model.data;

import java.io.Serializable;

/**
 * Player GameElement
 */
public class Player extends GameElement implements Serializable, IMovable {

	private static final long serialVersionUID = 8L;
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Player) {
			return getPosition().equals(((GameElement) obj).getPosition());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Player , Position: " + getPosition().toString();
	}
}
