package model.data;

import java.io.Serializable;

/**
 * Wall GameElement
 */
public class Wall extends GameElement implements Serializable {

	private static final long serialVersionUID = 10L;
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Wall) {
			return getPosition().equals(((GameElement) obj).getPosition());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Wall , Position: " + getPosition().toString();
	}
}
