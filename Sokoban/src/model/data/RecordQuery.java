package model.data;

import java.io.Serializable;

public class RecordQuery implements Serializable{
	private String m_LevelName;
	private String m_UserName;
	private String m_OrderBy;
	private int m_Page;
	private int m_ResultsPerPage;
	private boolean m_IsDesc;

	public RecordQuery() {
		m_LevelName = null;
		m_UserName = null;
		m_Page = 0;
		m_ResultsPerPage = 50;
		m_OrderBy = "id";
		m_IsDesc = false;
	}

	public String getLevelName() {
		return m_LevelName;
	}

	public void setLevelName(String m_LevelName) {
		this.m_LevelName = m_LevelName;
	}

	public String getUserName() {
		return m_UserName;
	}

	public void setUserName(String userName) {
		this.m_UserName = userName;
	}

	public int getPage() {
		return m_Page;
	}

	public void setPage(int page) {
		this.m_Page = page;
	}

	public int getResultsPerPage() {
		return m_ResultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		this.m_ResultsPerPage = resultsPerPage;
	}

	public String getOrderBy() {
		return m_OrderBy;
	}

	public void setOrderBy(String m_OrderBy) {
		this.m_OrderBy = m_OrderBy;
	}

	public boolean getIsDesc() {
		return m_IsDesc;
	}

	public void setIsDesc(boolean isDesc) {
		this.m_IsDesc = isDesc;
	}
}
