package controller.commands;

import java.util.HashMap;

import controller.SokobanController;
import model.IModel;
import view.IView;

/**
 * the class map the user commands that available
 */
public class CommandsFactory {
	private HashMap<String, ICreator> m_CommandsMap;
	private IModel m_Model;
	private IView m_View;
	private SokobanController m_Controller;

	/**
	 * command creator interface
	 */
	private interface ICreator {
		Command createCommand();
	}

	/**
	 * player move command creator
	 */
	private class MoveCommandCreator implements ICreator {
		@Override
		public Command createCommand() {
			return new MoveCommand(m_Model);
		}
	}

	/**
	 * game exit command creator
	 */
	private class ExitCommandCreator implements ICreator {
		@Override
		public Command createCommand() {
			return new ExitCommand(m_Controller);
		}
	}

	private class SetSecondsCommandCreator implements ICreator {
		@Override
		public Command createCommand() {
			return new SetSecondsCommand(m_View);
		}
	}

	/**
	 * display command creator
	 */
	private class DisplayCommandCreator implements ICreator {
		@Override
		public Command createCommand() {
			return new DisplayCommand(m_View, m_Model);
		}
	}

	/**
	 * save level to file command creator
	 */
	private class SaveCommandCreator implements ICreator {
		@Override
		public Command createCommand() {
			return new SaveCommand(m_Model);
		}
	}

	/**
	 * load level from file command creator
	 */
	private class LoadCommandCreator implements ICreator {
		@Override
		public Command createCommand() {
			return new LoadCommand(m_Model);
		}
	}

	private class WinCommandCreator implements ICreator {

		@Override
		public Command createCommand() {
			return new WinCommand(m_View);
		}

	}
	
	private class SearchRecordCommandCreator implements ICreator {

		@Override
		public Command createCommand() {
			return new SearchRecordCommand(m_Model);
		}

	}
	
	private class UpdateRecordsViewCreator implements ICreator {

		@Override
		public Command createCommand() {
			return new UpdateRecordsViewCommand(m_View);
		}
	}
	
	private class SaveRecordCreator implements ICreator{

		@Override
		public Command createCommand() {
			return new SaveRecordCommand(m_Model);
		}		
	}

	private class SolutionCreator implements ICreator{

		@Override
		public Command createCommand() {
			return new SolutionCommand(m_Model,m_View);
		}		
	}
	
	private class HintCreator implements ICreator{

		@Override
		public Command createCommand() {
			return new HintCommand(m_Model,m_View);
		}		
	}
	
	/**
	 * create instance of the class
	 */
	public CommandsFactory(SokobanController controller, IModel model, IView view) {

		m_Controller = controller;
		m_Model = model;
		m_View = view;
		m_CommandsMap = new HashMap<>();
		m_CommandsMap.put("move", new MoveCommandCreator());
		m_CommandsMap.put("load", new LoadCommandCreator());
		m_CommandsMap.put("save", new SaveCommandCreator());
		m_CommandsMap.put("exit", new ExitCommandCreator());
		m_CommandsMap.put("SecondsCountChange", new SetSecondsCommandCreator());
		m_CommandsMap.put("win", new WinCommandCreator());
		m_CommandsMap.put("display", new DisplayCommandCreator());
		m_CommandsMap.put("searchRecords", new SearchRecordCommandCreator());
		m_CommandsMap.put("solution", new SolutionCreator());
		m_CommandsMap.put("hint", new HintCreator());
		m_CommandsMap.put("setRecords", new UpdateRecordsViewCreator());
		m_CommandsMap.put("saveRecord",new SaveRecordCreator());
	}

	/**
	 * Return the parsed command with his values that match to the key
	 */
	public Command GetCommand(String key) {
		ICreator commandCreator = m_CommandsMap.get(key);
		if (commandCreator == null)
			return null;

		return commandCreator.createCommand();
	}
}
