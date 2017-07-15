package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import model.serverProtocol.Command;

public class ObjectClientInput  {

	public Command read(InputStream input) {
		Command cmd = null;
		try {
			ObjectInputStream in = new ObjectInputStream(input);
			cmd = (Command) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cmd;
	}

}
