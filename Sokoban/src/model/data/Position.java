package model.data;

import java.io.Serializable;

/**
 * 2D position/point
 */
public class Position implements Serializable {

	private static final long serialVersionUID = 9L;
	
	private int m_id;
	private int m_X;
    private int m_Y;

    public Position(int x, int y) {
        m_X = x;
        m_Y = y;
    }

    public int getX() {
        return m_X;
    }

    public void setX(int x) {
        this.m_X = x;
    }

    public int getY() {
        return m_Y;
    }

    public void setY(int y) {
        this.m_Y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Position) {
            Position val = (Position) obj;
            return val.getX() == m_X && val.getY() == m_Y;
        }
        return false;
    }

	public int getId() {
		return m_id;
	}

	public void setId(int id) {
		m_id = id;
	}
}
