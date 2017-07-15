package controller.commands;

import view.IView;

public class WinCommand extends Command {

	private IView m_View;

	public WinCommand(IView view) {
		m_View = view;
	}

	@Override
	public void execute() throws Exception {
		m_View.winning();
	}

}
