package view;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.data.Record;
import model.data.RecordQuery;

public class RecordsView extends Observable implements Initializable {
	private final ObservableList<Record> m_recordsList = FXCollections.observableArrayList();

	private RecordQuery m_CurrentQuery;

	@FXML
	TableView recordsTableView;
	@FXML
	TextField pageNumber;
	@FXML
	TextField userNameText;
	@FXML
	TextField levelNameText;
	@FXML
	ChoiceBox orderByChoiceBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		m_CurrentQuery = new RecordQuery();
		recordsTableView.setItems(m_recordsList);
		orderByChoiceBox.setItems(FXCollections.observableArrayList("ID", "Level name", "User name", "Time", "Steps"));
		orderByChoiceBox.setValue("ID");
	}

	public void search() {
		this.setChanged();
		notifyObservers(this);
	}

	public void onSearchClick() {
		getCurrentQuery().setUserName(
				userNameText.getText() == null || userNameText.getText().equals("") ? null : userNameText.getText());
		getCurrentQuery().setLevelName(
				levelNameText.getText() == null || levelNameText.getText().equals("") ? null : levelNameText.getText());

		switch ((String) orderByChoiceBox.getValue()) {
		case "ID":
			m_CurrentQuery.setOrderBy("id");
			break;
		case "Level name":
			m_CurrentQuery.setOrderBy("levelName");
			break;
		case "User name":
			m_CurrentQuery.setOrderBy("userName");
			break;
		case "Time":
			m_CurrentQuery.setOrderBy("seconds");
			break;
		case "Steps":
			m_CurrentQuery.setOrderBy("steps");
			break;
		}
		try {
			getCurrentQuery().setPage(Integer.parseInt(pageNumber.getText()) - 1);
			if (getCurrentQuery().getPage() < 0)
				m_CurrentQuery.setPage(0);
		} catch (NumberFormatException ex) {
			getCurrentQuery().setPage(0);
		}
		search();
	}

	@FXML
	public void onMouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY
				&& recordsTableView.getSelectionModel().getSelectedItem() != null) {
			Record rec = (Record) recordsTableView.getSelectionModel().getSelectedItem();
			RecordQuery query = new RecordQuery();
			query.setUserName(rec.getUserName());
			setQuery(query);
			search();
		}
	}

	public ObservableList<Record> getRecords() {
		return m_recordsList;
	}

	public RecordQuery getCurrentQuery() {
		return m_CurrentQuery;
	}

	public void setQuery(RecordQuery query) {
		m_CurrentQuery = query;
		userNameText.setText(query.getUserName());
		levelNameText.setText(query.getLevelName());
		switch (query.getOrderBy()) {
		case "id":
			orderByChoiceBox.setValue("ID");
			break;
		case "levelName":
			orderByChoiceBox.setValue("Level name");
			break;
		case "userName":
			orderByChoiceBox.setValue("User name");
			break;
		case "seconds":
			orderByChoiceBox.setValue("Time");
			break;
		case "steps":
			orderByChoiceBox.setValue("Steps");
			break;
		}
		pageNumber.setText(((Integer) query.getPage()).toString());
	}

}
