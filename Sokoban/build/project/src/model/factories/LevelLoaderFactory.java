package model.factories;

import java.util.HashMap;

import model.data.*;

/**
 * The class map the levelLoaders types to save/load levels
 */
public class LevelLoaderFactory {
	private HashMap<String, ICreator> m_LevelMap;

	/**
	 * Create new instance of LevelLoaderFactory
	 */
	public LevelLoaderFactory() {
		m_LevelMap = new HashMap<String, ICreator>();
		m_LevelMap.put("txt", new TxtLevelCreator());
		m_LevelMap.put("xml", new XmlLevelCreator());
		m_LevelMap.put("obj", new ObjLevelCreator());
	}

	/**
	 * Represent levelLoader creator
	 */
	private interface ICreator {

		ILevelLoader createLevelLoader();
	}

	/**
	 * text LevelLoader creator
	 */
	private class TxtLevelCreator implements ICreator {
		@Override
		public ILevelLoader createLevelLoader() {
			return new TxtLevelLoader();
		}
	}

	/**
	 * Object(binary) LevelLoader creator
	 */
	private class ObjLevelCreator implements ICreator {
		@Override
		public ILevelLoader createLevelLoader() {
			return new ObjLevelLoader();
		}
	}

	/**
	 * XML LevelLoader creator
	 */
	private class XmlLevelCreator implements ICreator {
		@Override
		public ILevelLoader createLevelLoader() {
			return new XmlLevelLoader();
		}
	}

	/**
	 * Return new LevelLoader that compatible with the path extension
	 * @param path the level path to create level loader to
	 * @return the compatible LevelLoader
	 */
	public ILevelLoader createLoader(String path) {
		ICreator creator = m_LevelMap.get(path.substring(path.lastIndexOf('.') + 1));
		if (creator == null)
			return null;
		return creator.createLevelLoader();
	}
}
