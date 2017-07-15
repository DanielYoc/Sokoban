package controller.commands;

import view.IView;

/**
 * Update the seconds value in the view
 */
public class SetSecondsCommand extends Command {

	private IView m_View;

	public SetSecondsCommand(IView view) {
		m_View = view;
	}

	@Override
	public void execute() throws Exception {
		m_View.setTimeElspade(Integer.parseInt((String)m_Params.get(0)));
	}

}
