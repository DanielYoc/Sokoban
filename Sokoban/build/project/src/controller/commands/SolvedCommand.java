package controller.commands;

import view.IView;

public class SolvedCommand extends Command {

	private IView m_View;

	public SolvedCommand(IView view) {
		m_View = view;
	}

	@Override
	public void execute() throws Exception {
		m_View.winning();
	}

}
