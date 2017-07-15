package controller.commands;

import model.IModel;

/**
 * Save user level recored
 */
public class SaveRecordCommand extends Command {

	private IModel m_Model;

	public SaveRecordCommand(IModel model) {
		m_Model = model;
	}

	@Override
	public void execute() throws Exception {
		m_Model.saveRecord((String) m_Params.get(0));
	}

}
