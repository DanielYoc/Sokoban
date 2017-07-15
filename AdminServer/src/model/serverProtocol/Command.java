package model.serverProtocol;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import model.data.Level;
import model.data.Record;
import model.data.RecordQuery;
import model.data.RecordsDataMapper;
import model.data.SolutionsGateway;

public class Command implements Serializable {

	private static final long serialVersionUID = -6095011322216519425L;

	private String m_Name;

	private Level m_Level;

	private RecordQuery m_Query;

	private Record m_Recored;

	public String getName() {
		return m_Name;
	}

	public void setName(String name) {
		m_Name = name;
	}

	public Level getLevel() {
		return m_Level;
	}

	public void setLevel(Level level) {
		m_Level = level;
	}

	public RecordQuery getQuery() {
		return m_Query;
	}

	public void setQuery(RecordQuery query) {
		m_Query = query;
	}

	public Record getRecord() {
		return m_Recored;
	}

	public void setRecored(Record recored) {
		m_Recored = recored;
	}


}
