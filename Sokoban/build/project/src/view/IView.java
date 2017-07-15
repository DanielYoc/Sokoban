package view;

import commons.ILevelReadOnly;
import javafx.stage.Stage;

public interface IView {

	public void paint();

	public void onLoadLevelClick();

	public void onExitClick();

	public void showMessage(String text);

	public void setStage(Stage primaryStage);
	
	public void setTimeElspade(int seconds);

	public void close();
	
	public void setLevel(ILevelReadOnly level);
	
	public void winning();
}
