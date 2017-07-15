package model.data;

import java.io.Serializable;

public class Record implements Serializable{

	private static final long serialVersionUID = -506604695839485260L;
	
	private int m_Id;
	private String m_LevelName;
	private int m_Seconds;
	private String m_UserName;
	private int m_Steps;

	public Record() {
	}
	
	public Record(String levelName,int seconds,String userName,int steps) {
		setLevelName(levelName);
		setSeconds(seconds);
		setUserName(userName);
		setSteps(steps);
	}
	
	public String getLevelName() {
		return m_LevelName;
	}

	public void setLevelName(String name) {
		this.m_LevelName = name;
	}

	public int getSeconds() {
		return m_Seconds;
	}

	public void setSeconds(int seconds) {
		this.m_Seconds = seconds;
	}

	public String getTime() {
		int minutes = m_Seconds / 60;
		int seconds = m_Seconds % 60;
		String timeStr = minutes >= 10 ? String.valueOf(minutes) : "0" + minutes;
		timeStr += ":";
		timeStr += seconds >= 10 ? seconds : "0" + seconds;
		return timeStr;
	}

	public String getUserName() {
		return m_UserName;
	}

	public void setUserName(String userName) {
		this.m_UserName = userName;
	}

	public int getId() {
		return m_Id;
	}

	public void setId(int id) {
		this.m_Id = id;
	}

	public int getSteps() {
		return m_Steps;
	}

	public void setSteps(int steps) {
		this.m_Steps = steps;
	}

}
