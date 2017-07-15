package model.factories;

import java.util.HashMap;
import model.data.*;

/**
 * the class map and decoder char to GameElements
 */
public class GameElementsFactory {
	private HashMap<Character, ICreator[]> hm;

	/**
	 * Represent GameElement creator
	 */
	private interface ICreator {
		public GameElement create();
	}

	/**
	 * Box creator
	 */
	private class BoxCreator implements ICreator {
		@Override
		public GameElement create() {
			return new Box();
		}
	}

	/**
	 * Player creator
	 */
	private class PlayerCreator implements ICreator {
		@Override
		public GameElement create() {
			return new Player();
		}
	}

	/**
	 * DestinationPoint creator
	 */
	private class DestinationCreator implements ICreator {
		@Override
		public GameElement create() {
			return new Target();
		}

	}

	/**
	 * Wall creator
	 */
	private class WallCreator implements ICreator {
		@Override
		public GameElement create() {
			return new Wall();
		}
	}

	/**
	 * Floor creator
	 */
	private class FloorCreator implements ICreator {
		@Override
		public GameElement create() {
			return new Floor();
		}
	}

	/**
	 * Create instance of GameElementFactory
	 */
	public GameElementsFactory() {
		hm = new HashMap<Character, ICreator[]>();
		hm.put('#', new ICreator[] { new WallCreator() });
		hm.put('&', new ICreator[] { new WallCreator(), new PlayerCreator() });
		hm.put('?', new ICreator[] { new WallCreator(), new BoxCreator() });
		hm.put('@', new ICreator[] { new FloorCreator(), new BoxCreator() });
		hm.put('A', new ICreator[] { new FloorCreator(), new PlayerCreator() });
		hm.put(' ', new ICreator[] { new FloorCreator() });
		hm.put('o', new ICreator[] { new DestinationCreator() });
		hm.put('a', new ICreator[] { new DestinationCreator(), new PlayerCreator() });
		hm.put('*', new ICreator[] { new DestinationCreator(), new BoxCreator() });
		hm.put('\t', new ICreator[0]);
	}

	/**
	 * Return array of the elements that the received char represent
	 * 
	 * @param c
	 *            the char to decoder
	 * @return array of GameElement
	 */
	public GameElement[] CreateElements(char c) {
		ICreator[] cr = hm.get(c);
		GameElement[] arr = new GameElement[cr.length];
		for (int i = 0; i < arr.length; i++)
			arr[i] = cr[i].create();
		return arr;
	}

}
