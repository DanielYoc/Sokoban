package model.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Cell implements Serializable {

	private static final long serialVersionUID = 7857851366628810488L;

	private ArrayList<GameElement> m_Elements;

	public Cell() {
		m_Elements = new ArrayList<>();
	}

	public ArrayList<GameElement> getElements() {
		return m_Elements;
	}

	public void setElements(ArrayList<GameElement> elements) {
		m_Elements = elements;
	}

	// policy
	public boolean hasWall() {
		for (GameElement el : m_Elements) {
			if (el instanceof Wall) {
				return true;
			}
		}
		return false;
	}

	public boolean hasBox() {
		for (GameElement el : m_Elements) {
			if (el instanceof Box) {
				return true;
			}
		}
		return false;
	}

	public boolean hasTarget() {
		for (GameElement el : m_Elements) {
			if (el instanceof Target) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPlayer() {
		for (GameElement el : m_Elements) {
			if (el instanceof Player) {
				return true;
			}
		}
		return false;
	}

	public Player getPlayer() {
		for (GameElement el : m_Elements) {
			if (el instanceof Player) {
				return (Player) el;
			}
		}
		return null;
	}

	public Box getBox() {
		for (GameElement el : m_Elements) {
			if (el instanceof Box) {
				return (Box) el;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cell [ ");
		for (GameElement ge : m_Elements)
			sb.append("{" + ge.toString() + "}");
		sb.append(" ]");
		return sb.toString();
	}
}
