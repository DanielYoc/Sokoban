package boot;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.*;
import model.data.SolutionsGateway;
import server.MyServer;
import view.MainPanelController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

	private final static String ip = "localhost";
	private final static String solutionServerAddr = "localhost:8080";
	private final static int port = 9000;
	private static MyServer server;

	@Override
	public void start(Stage primaryStage) {
		try {
			IViewModel model = new ViewModel(server);
			server.startServer();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainPanel.fxml"));
			AnchorPane root = (AnchorPane) fxmlLoader.load();

			MainPanelController view = (MainPanelController) fxmlLoader.getController();
			view.setModel(model);

			Scene scene = new Scene(root, 400, 460);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Admin panel");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			server = new MyServer(port, new SolutionsGateway(solutionServerAddr));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
		server.stopServer();
	}
}
