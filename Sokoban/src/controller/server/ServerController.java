package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import controller.Controller;
import controller.SokobanController;
import controller.commands.Command;
import model.IModel;
import view.IView;

/**
 * Provide connection to the controller by send commands to server
 */
public class ServerController extends SokobanController {
	private ServerSocket m_ClientSocket;
	private boolean m_End;

	public ServerController(Controller controller, IModel model, IView view, int port) throws IOException {
		super(controller, model, view);
		m_End = false;
		m_ClientSocket = new ServerSocket(port);
	}

	@Override
	public void start() {
		SokobanController sokoController = this;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (m_End == false) {
					try {
						Socket clientSocket = m_ClientSocket.accept();
						IClientInput clientInput = new TextClientInput();
						Command command = clientInput.read(clientSocket.getInputStream(), sokoController, m_Model,
								m_View);
						m_Controller.insertCommand(command);
						IClientOutput clientOutput = new TextClientOutput();
						clientOutput.write(clientSocket.getOutputStream(), "Command execute.");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});
		thread.start();
		super.start();
	}

	@Override
	public void stop() {
		m_End = true;
		super.stop();
	}
}
