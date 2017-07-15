package controller.commands;

import java.io.FileOutputStream;
import model.IModel;
import model.data.ILevelLoader;
import model.factories.LevelLoaderFactory;

/**
 * Represents save level command
 */
public class SaveCommand extends Command {

	private IModel m_Model;

	public SaveCommand(IModel model) {
		m_Model = model;
	}

	@Override
	public void execute() throws Exception {
		String path = m_Params.get(0);
		LevelLoaderFactory factory = new LevelLoaderFactory();
		ILevelLoader levelLoader = factory.createLoader(path);
		if (levelLoader == null) {
			throw new Exception("invalid Level file extension.");
		}
		levelLoader.save(new FileOutputStream(path), m_Model.getLevel());
	}

}
