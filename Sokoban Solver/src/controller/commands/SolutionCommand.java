package controller.commands;

import model.IModel;
import planners.CantSolvedException;
import view.IView;

public class SolutionCommand extends Command {

	private IModel m_Model;
	private IView m_View;

	public SolutionCommand(IModel model,IView view) {
		m_Model = model;
		m_View=view;
	}

	@Override
	public void execute() throws Exception {
		try{
		m_Model.plan();
		m_View.setSolution(m_Model.plan());
		}catch(CantSolvedException e){
			m_View.levelCantSolvedMessage();
		}
		
	}

}
