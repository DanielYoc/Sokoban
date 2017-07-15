package view;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.IViewModel;
import server.Peer;

public class MainPanelController extends Observable implements Initializable {

	private IViewModel m_Model;

	@FXML
	TableView peersTableView;

	@FXML
	TextField maxConnectionsAllowedTxt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (m_Model != null) {
			peersTableView.setItems(m_Model.getConnectedPeerList());
			Bindings.bindBidirectional(maxConnectionsAllowedTxt.textProperty(), m_Model.getMaxAllowedConnections());
		}
	}

	public void setModel(IViewModel model) {
		m_Model = model;

		if (peersTableView != null)
			peersTableView.setItems(m_Model.getConnectedPeerList());

		if (maxConnectionsAllowedTxt != null)
			Bindings.bindBidirectional(maxConnectionsAllowedTxt.textProperty(), m_Model.getMaxAllowedConnections());
	}

	public void onDisconnectedClick() {
		if (peersTableView.getSelectionModel().getSelectedItem() != null) {
			Peer peer = (Peer) peersTableView.getSelectionModel().getSelectedItem();
			m_Model.disconnectClient(peer);
		}
	}

	public void onOpenServerClick() {
		m_Model.openServer();
	}

	public void onCloseServerClick() {
		m_Model.closeServer();
	}

	public void onUpdateMaxConnectionsAllowedClick() {
		m_Model.updateMaxAllowedConnections();
	}

}
