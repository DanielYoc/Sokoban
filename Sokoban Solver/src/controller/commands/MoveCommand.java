package controller.commands;

import model.IModel;

/**
 * Represents move command of player in level
 */
public class MoveCommand extends Command {
	private IModel m_Model;

	public MoveCommand(IModel model) {
		m_Model = model;
	}

	@Override
	public void execute() throws Exception {
		m_Model.move((String) m_Params.get(0));
	}
}
