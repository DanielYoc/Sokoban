package controller.commands;

import model.IModel;
import model.serverProtocol.SolutionRespone;
import view.IView;

/**
 * return for view solution for current level
 */
public class SolutionCommand extends Command {

	private IModel m_Model;
	private IView m_View;

	public SolutionCommand(IModel model, IView view) {
		m_Model = model;
		m_View = view;
	}

	@Override
	public void execute() throws Exception {
		SolutionRespone solution = m_Model.getSolution();
		if (solution.getSolvable())
			m_View.setSolution(solution.getActions());
		else
			m_View.showMessage("Can't find solution for current state.");
	}

}
