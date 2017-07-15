package controller.commands;

import java.io.FileInputStream;
import model.factories.*;
import model.data.*;
import model.IModel;
import model.data.Level;
/**
 * The command represents level load
 *
 */
public class LoadCommand extends Command {
	private IModel m_Model;

	public LoadCommand(IModel model) {
		m_Model = model;
	}

	@Override
	public void execute() throws Exception {
		String path =m_Params.get(0);
		LevelLoaderFactory factory = new LevelLoaderFactory();
		ILevelLoader loader = factory.createLoader(path);
		Level level = loader.load(new FileInputStream(path));
		m_Model.setLevel(level);
	}
}
