package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import server.Peer;

/**
 * Represent ViewModel interface
 */
public interface IViewModel {
	
	/** 
	 * @return observable list of connected peers
	 */
	ObservableList<Peer> getConnectedPeerList();
	
	public void disconnectClient(Peer peer);
	
	public SimpleStringProperty getMaxAllowedConnections();
	
	public void updateMaxAllowedConnections();

	public void closeServer();
	
	public void openServer();
}
