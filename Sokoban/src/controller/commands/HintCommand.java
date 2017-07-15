package controller.commands;

import model.IModel;
import model.serverProtocol.SolutionRespone;
import view.IView;

/**
 * Get and show to user one step move to solution
 */
public class HintCommand extends Command {

	private IModel m_Model;
	private IView m_View;

	public HintCommand(IModel model, IView view) {
		m_Model = model;
		m_View = view;
	}

	@Override
	public void execute() throws Exception {
		SolutionRespone solution = m_Model.getSolution();
		if (solution.getSolvable())
		{
			if(solution.getActions().length>0)
			m_View.setSolution(new String[]{solution.getActions()[0]});
		}
		else
			m_View.showMessage("Can't find solution for current state.");
	}

}
