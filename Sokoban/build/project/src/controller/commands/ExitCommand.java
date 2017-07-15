package controller.commands;

import controller.SokobanController;

/**
 * the class represents exist command
 **/
public class ExitCommand extends Command {

	public SokobanController m_Controller;

	public ExitCommand(SokobanController controller) {
		m_Controller = controller;
	}

	@Override
	public void execute() {
		m_Controller.stop();
	}

}
