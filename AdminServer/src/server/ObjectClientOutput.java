package server;

import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectClientOutput {

	public void write(OutputStream output, Object obj) {
		try {
			ObjectOutputStream writer = new ObjectOutputStream(output);
			writer.writeObject(obj);
			writer.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
