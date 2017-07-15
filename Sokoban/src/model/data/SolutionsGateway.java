package model.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import model.serverProtocol.*;

/**
 * Provide solution to levels
 */
public class SolutionsGateway {

	private int m_ServerPort;
	private String m_ServerHost;

	public SolutionsGateway(String host, int port) {
		m_ServerHost = host;
		m_ServerPort = port;
	}

	/**
	 * Return solution from server to given level
	 * @param level the level to return solution for
	 * @return the level solution
	 */
	public SolutionRespone GetSolution(Level level) {
		Command cmd = new Command();
		SolutionRespone resp = null;
		cmd.setLevel(level);
		cmd.setName("solution");
		try {
			Socket socket = new Socket(m_ServerHost, m_ServerPort);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(cmd);
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			resp = (SolutionRespone) in.readObject();
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resp;
	}

}
