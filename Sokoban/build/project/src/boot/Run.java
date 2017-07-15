package boot;

import java.io.FileInputStream;
import java.net.BindException;
import java.util.List;

import controller.Controller;
import controller.SokobanController;
import controller.server.ServerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.MyModel;
import model.policy.MySokobanPolicy;
import view.MyView;

public class Run extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MainWindow.fxml"));
		GridPane root = (GridPane) fxmlLoader.load();
		MyView view = (MyView) fxmlLoader.getController();
		Scene scene = new Scene(root, 800, 800);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(new FileInputStream("./resources/icon.png")));
		primaryStage.setTitle("Sokoban - Daniel Yochpaz & Limor Dray");
		MyModel model = new MyModel(new MySokobanPolicy());
		SokobanController controller = null;
		List<String> params = getParameters().getRaw();
		if (params.size() != 0 && params.get(0).equals("-server")) {
			try {
				controller = new ServerController(new Controller(), model, view,
						java.lang.Integer.parseInt(params.get(1)));
			} catch (BindException e) {
				System.out.println("the port you entered is already in use.\n");		
				Platform.exit();
				return;
			}
		} else
			controller = new SokobanController(new Controller(), model, view);
		
		controller.setModel(model);
		controller.setView(view);
		view.setStage(primaryStage);
		model.addObserver(controller);
		view.addObserver(controller);
		controller.start();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
