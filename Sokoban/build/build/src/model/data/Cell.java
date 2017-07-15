package model.data;

import java.io.Serializable;
import java.util.ArrayList;
import commons.ICellReadOnly;

public class Cell implements ICellReadOnly, Serializable {

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

	@Override
	public GameElement[] getElementsArray() {
		return m_Elements.toArray(new GameElement[m_Elements.size()]);
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
}
