package model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.MyServer;
import server.Peer;

public class ViewModel implements IViewModel, Observer {

	private final ObservableList<Peer> m_ConnectedPeers = FXCollections.observableArrayList();
	private MyServer m_MyServer;
	private SimpleStringProperty m_MaxAllowedConnections;

	public ViewModel(MyServer server) {
		m_MyServer = server;
		m_MyServer.addObserver(this);
		m_MaxAllowedConnections = new SimpleStringProperty("10");
	}

	@Override
	public void disconnectClient(Peer peer) {
		m_MyServer.forceDisconnectClient(peer);
	}

	@Override
	public ObservableList<Peer> getConnectedPeerList() {
		return m_ConnectedPeers;
	}

	@Override
	public SimpleStringProperty getMaxAllowedConnections() {
		return m_MaxAllowedConnections;
	}

	@Override
	public void updateMaxAllowedConnections() {
		try {
			int num = Integer.parseInt(m_MaxAllowedConnections.getValue());
			if (num < 0)
				throw new NumberFormatException();
			m_MyServer.setMaxAllowedConnections(num);
		} catch (NumberFormatException e) {
			m_MaxAllowedConnections.setValue(String.valueOf(m_MyServer.getMaxAllowedConnections()));
		}
	}

	@Override
	public void closeServer() {
		m_MyServer.stopServer();
	}

	@Override
	public void openServer() {
		m_MyServer.startServer();
	}

	@Override
	public void update(Observable o, Object arg) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				List<Object> params = (List<Object>) arg;
				if (((String) params.get(0)).equals("disconnected"))
					m_ConnectedPeers.remove(params.get(1));
				else
					m_ConnectedPeers.add((Peer) params.get(1));
			}
		});

	}

}
