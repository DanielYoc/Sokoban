package model.data;

import java.io.Serializable;

/**
 * DestinationPoint GameElement
 */
public class Target extends GameElement implements Serializable {

	private static final long serialVersionUID = 4L;
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Target) {
			return getPosition().equals(((GameElement) obj).getPosition());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Target , Position: " + getPosition().toString();
	}
}
