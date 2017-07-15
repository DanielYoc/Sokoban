package controller;

import java.util.*;
import controller.commands.*;
import model.IModel;
import model.MyModel;
import view.IView;

public class SokobanController implements Observer {

	protected CommandsFactory m_CommandsFactory;
	protected Controller m_Controller;
	protected IModel m_Model;
	protected IView m_View;

	public SokobanController(Controller controller, IModel model, IView view) {
		m_Model = model;
		m_View = view;
		m_Controller = controller;
		m_CommandsFactory = new CommandsFactory(this, m_Model, m_View);
	}

	public void setModel(MyModel model) {
		m_Model = model;
	}

	public void setView(IView view) {
		m_View = view;
	}

	@Override
	public void update(Observable o, Object arg) {
		@SuppressWarnings("unchecked")
		List<Object> params = (List<Object>) arg;
		String commandName = (String) params.remove(0);
		Command cmd = m_CommandsFactory.GetCommand(commandName);
		if (cmd == null) {
			System.out.println("Unkown command - " + commandName);
			return;
		}
		cmd.setParams(params);
		m_Controller.insertCommand(cmd);
	}

	public void stop() {
		m_Controller.stop();
	}

	public void start() {
		m_Controller.start();
	}

}
