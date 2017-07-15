package model.data;

import java.io.Serializable;

/**
 * Represent move GameItem
 */
public interface IMovable extends Serializable  {
    Position getPosition();
}
