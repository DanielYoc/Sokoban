package controller.commands;

import model.IModel;
import view.IView;

public class DisplayCommand extends Command {

	private IView m_View;
	private IModel m_Model;

	public DisplayCommand(IView view, IModel model) {
		m_View = view;
		m_Model = model;
	}

	@Override
	public void execute() throws Exception {
		m_View.setLevel(m_Model.getLevel());
		m_View.paint();
	}

}
