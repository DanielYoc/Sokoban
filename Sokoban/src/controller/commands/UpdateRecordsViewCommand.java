package controller.commands;

import model.data.Record;
import view.IView;

/**
 * Update view records table
 */
public class UpdateRecordsViewCommand extends Command {

	private IView m_view;

	public UpdateRecordsViewCommand(IView view) {
		m_view = view;
	}

	@Override
	public void execute() throws Exception {
		m_view.setRecords((long) m_Params.get(0), (Record[]) m_Params.get(1));
	}

}
