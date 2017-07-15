package model.data;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.serverProtocol.Command;

/**
 * Provide records save and search from server
 */
public class RecordsGateway {

	private int m_ServerPort;
	private String m_ServerHost;

	public RecordsGateway(String host, int port) {
		m_ServerHost = host;
		m_ServerPort = port;
	}

	/**
	 * Send the given record to be save in the server
	 * @param recored the record to save
	 */
	public void saveRecored(Record record) {
		Command cmd = new Command();
		cmd.setName("addRecord");
		cmd.setRecored(record);
		try {
			Socket socket = new Socket(m_ServerHost, m_ServerPort);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			out.writeObject(cmd);
			out.flush();

			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send query search records for server and return the related records
	 * @param query the records query to search
	 * @return the records
	 */
	public Record[] search(RecordQuery query) {
		Command cmd = new Command();
		cmd.setName("searchRecords");
		cmd.setQuery(query);
		Record[] resp = null;
		try {
			Socket socket = new Socket(m_ServerHost, m_ServerPort);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			out.writeObject(cmd);
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			resp = (Record[]) in.readObject();

			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
