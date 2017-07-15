package view;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import commons.ILevelReadOnly;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Record;
import model.data.RecordQuery;

public class MyView extends Observable implements IView, Initializable, Observer {

	private Stage m_Stage;
	private MediaPlayer m_BackgroundPlayer;
	private MediaPlayer m_MoveSoundPlayer;
	private HashMap<KeyCode, String> m_KeysMap;
	private HashMap<Long, RecordsView> m_recordsRequests;

	@FXML
	LevelControl levelControl;
	@FXML
	GridPane mainGrid;
	@FXML
	Label timeLabel;
	@FXML
	Label stepsCountLabel;
	@FXML
	MenuButton menuButton;
	@FXML
	Label levelNameLabel;
	@FXML
	Label resultForLabel;
	@FXML
	MenuItem showLevelRecordsMenuItem;
	@FXML
	MenuItem showSoultionMenuItem;
	@FXML
	MenuItem showHintMenuItem;	

	@SuppressWarnings("unchecked")
	public MyView() {
		m_BackgroundPlayer = new MediaPlayer(new Media(new File("resources/background_music.mp3").toURI().toString()));
		m_BackgroundPlayer.setVolume(0.5);
		m_BackgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		m_BackgroundPlayer.setAutoPlay(false);
		m_recordsRequests = new HashMap<>();
		m_MoveSoundPlayer = new MediaPlayer(new Media(new File("resources/move_sound.mp3").toURI().toString()));

		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("./resources/keys.xml"));
			m_KeysMap = (HashMap<KeyCode, String>) decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("failed to read keys");
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuButton.setFocusTraversable(false);
		levelControl.init();
		javafx.event.EventHandler<KeyEvent> keyHandler = new javafx.event.EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String dir = m_KeysMap.get(event.getCode());
				if (dir != null) {
					m_MoveSoundPlayer.stop();
					if (m_BackgroundPlayer.getStatus() == Status.PLAYING)
						m_MoveSoundPlayer.play();
					setChanged();
					ArrayList<String> list = new ArrayList<>();
					list.add("move");
					list.add(dir);
					notifyObservers(list);
				}
			}
		};
		mainGrid.setOnKeyPressed(keyHandler);
	}

	public void showSoultionClick() {
		setChanged();
		List<String> params = new ArrayList<String>();
		params.add("solution");
		notifyObservers(params);
	}
	
	public void showHintClick(){
		setChanged();
		List<String> params = new ArrayList<String>();
		params.add("hint");
		notifyObservers(params);
	}

	@Override
	public void paint() {
		levelControl.redraw();
		levelControl.focusedProperty();
	}

	public void onLoadLevelClick() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Level File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("Xml Files", "*.xml"), new ExtensionFilter("Binary Files", "*.obj"),
				new ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File("./levels"));
		File file = fileChooser.showOpenDialog(m_Stage);
		if (file != null) {
			setChanged();
			List<String> params = new ArrayList<String>();
			params.add("load");
			params.add(file.getPath());
			notifyObservers(params);
		}
	}

	public void onSaveLevelClick() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Level File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("Xml Files", "*.xml"), new ExtensionFilter("Binary Files", "*.obj"),
				new ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File("./levels"));
		File file = fileChooser.showSaveDialog(m_Stage);
		if (file != null) {
			setChanged();
			List<String> params = new ArrayList<String>();
			params.add("save");
			params.add(file.getPath());
			notifyObservers(params);
		}
	}

	public void showLevelRecordsClick() {
		RecordQuery query = new RecordQuery();
		query.setLevelName(levelNameLabel.getText());
		showRecordTable(query);
	}

	@Override
	public void showMessage(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Message");
				alert.setContentText(text);
				alert.showAndWait();
			}
		});
	}

	@Override
	public void onExitClick() {
		setChanged();
		LinkedList<String> params = new LinkedList<String>();
		params.add("exit");
		notifyObservers(params);
	}

	public void winning() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				AnchorPane root = null;
				try {
					root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/WinReport.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Label) root.lookup("#timeLabel")).setText(timeLabel.getText());
				((Label) root.lookup("#stepsLabel")).setText(stepsCountLabel.getText());

				Dialog<ButtonType> dialog = new Dialog<ButtonType>();
				dialog.setTitle("Level complete");
				dialog.getDialogPane().setContent(root);
				dialog.initOwner(m_Stage);
				dialog.setResizable(false);
				TextField userNameTxtBox = (TextField) root.lookup("#userNameTxtBox");
				Label invalidUserNameLabel = (Label) root.lookup("#invalidUserNameLabel");
				((Button) root.lookup("#saveRecordBtn")).addEventHandler(MouseEvent.MOUSE_CLICKED,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								String userName = userNameTxtBox.getText();
								if (userName == null || userName.equals("") || userName.length() > 50) {
									invalidUserNameLabel.setVisible(true);
									return;
								}
								setChanged();
								LinkedList<String> params = new LinkedList<String>();
								params.add("saveRecord");
								params.add(userName);
								notifyObservers(params);
								dialog.close();
								RecordQuery query = new RecordQuery();
								query.setLevelName(levelNameLabel.getText());
								showRecordTable(query);
							}
						});
				dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
				Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
				closeButton.managedProperty().bind(closeButton.visibleProperty());
				closeButton.setVisible(false);
				dialog.showAndWait();
			}
		});
	}

	public void setStage(Stage stage) {
		m_Stage = stage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				onExitClick();
			}
		});
	}

	public void close() {
		Platform.exit();
	}

	@Override
	public void setLevel(ILevelReadOnly level) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				levelNameLabel.setText(level.getName());
				stepsCountLabel.setText(String.valueOf(level.getStepsCount()));
			}
		});
		levelControl.setLevel(level);
		setTimeElspade(level.getSecondsCount());
		showLevelRecordsMenuItem.setDisable(false);
		showSoultionMenuItem.setDisable(false);
		showHintMenuItem.setDisable(false);
	}

	public void musicTrigger() {
		if (m_BackgroundPlayer.getStatus() == Status.PLAYING)
			m_BackgroundPlayer.pause();
		else
			m_BackgroundPlayer.play();
	}

	@Override
	public void setTimeElspade(int totalSeconds) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				int minutes = totalSeconds / 60;
				int seconds = totalSeconds % 60;
				String timeStr = minutes >= 10 ? String.valueOf(minutes) : "0" + minutes;
				timeStr += ":";
				timeStr += seconds >= 10 ? seconds : "0" + seconds;
				timeLabel.setText(timeStr);
			}
		});

	}

	@Override
	public void setRecords(long id, Record[] records) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ObservableList<Record> list = m_recordsRequests.remove(id).getRecords();
				list.clear();
				list.addAll(records);
			}
		});
	}

	public void showRecordsClick() {
		showRecordTable(null);
	}

	private void showRecordTable(RecordQuery query) {
		MyView parent = this;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				AnchorPane root = null;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RecordsTable.fxml"));

				try {
					root = (AnchorPane) fxmlLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				RecordsView view = (RecordsView) fxmlLoader.getController();
				view.addObserver(parent);
				if (query != null) {
					view.setQuery(query);
				}
				view.search();
				Dialog<ButtonType> dialog = new Dialog<ButtonType>();
				dialog.setTitle("Records table");
				dialog.initOwner(m_Stage);
				dialog.getDialogPane().setContent(root);
				dialog.setWidth(500);
				dialog.setHeight(400);
				dialog.setResizable(false);
				dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
				Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
				closeButton.managedProperty().bind(closeButton.visibleProperty());
				closeButton.setVisible(false);
				dialog.showAndWait();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		RecordsView view = (RecordsView) arg;
		long id = System.currentTimeMillis();
		while (m_recordsRequests.containsKey(id))
			id = System.currentTimeMillis();
		m_recordsRequests.put(id, view);
		setChanged();
		LinkedList<Object> params = new LinkedList<Object>();
		params.add("searchRecords");
		params.add(id);
		params.add(view.getCurrentQuery());
		notifyObservers(params);
	}

	@Override
	public void setSolution(String[] actions) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (String ac : actions) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setChanged();
					ArrayList<String> list = new ArrayList<>();
					list.add("move");
					list.add(ac.split(" ")[1]);
					notifyObservers(list);
				}
			}
		}).start();
	}

}
