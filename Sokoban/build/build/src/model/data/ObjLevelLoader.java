package model.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Object(binary) save and load level
 */
public class ObjLevelLoader implements ILevelLoader {
	@Override
	public Level load(InputStream input) {
		Level level = null;
		try {
			ObjectInputStream in = new ObjectInputStream(input);
			level = (Level) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return level;
	}

	@Override
	public void save(OutputStream output, Level level) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(output);
			out.writeObject(level);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
