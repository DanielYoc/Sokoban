package controller.commands;

import java.util.List;

/**
 * 
 * The inheritor need to implement operation method that will call when the
 * command execute
 */
public abstract class Command {
	protected List<String> m_Params;

	public void setParams(List<String> params) {
		m_Params = params;
	}

	public abstract void execute() throws Exception;
}
