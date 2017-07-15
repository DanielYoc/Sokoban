package controller.commands;

import model.IModel;
import model.data.RecordQuery;

/**
 * Send users records search by given query.
 * The results will be return and set in the view by "UpdateRecordsViewCommands"
 */
public class SearchRecordCommand extends Command {

	private IModel m_Model;

	public SearchRecordCommand(IModel model) {
		m_Model = model;
	}

	@Override
	public void execute() throws Exception {
		m_Model.searchRecords((long)m_Params.get(0),(RecordQuery)m_Params.get(1));
	}

}
