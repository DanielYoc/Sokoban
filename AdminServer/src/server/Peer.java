package server;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Peer {
	private int m_Port;
	private String m_Ip;
	private StringProperty m_Mission;

	public Peer(String ip, int port) {
		m_Ip = ip;
		m_Port = port;
		m_Mission = new SimpleStringProperty();
		setMissionProperty("Waiting for thread start...");
	}

	public int getPort() {
		return m_Port;
	}

	public void setPort(int port) {
		this.m_Port = port;
	}

	public String getIp() {
		return m_Ip;
	}

	public void setIp(String m_Ip) {
		this.m_Ip = m_Ip;
	}

	public StringProperty missionProperty() {
		return m_Mission;
	}

	public void setMissionProperty(String mission) {
		this.m_Mission.setValue(mission);
	}

	@Override
	public String toString() {
		return m_Ip + ":" + m_Port;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Peer == false)
			return false;

		Peer param = (Peer) obj;
		return m_Port == param.getPort() && m_Ip.equals(param.m_Ip);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}
