package model.data;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * XML save and load level
 */
public class XmlLevelLoader implements ILevelLoader {
	@Override
	public Level load(InputStream input) {
		XMLDecoder dec = new XMLDecoder(input);
		Level level = (Level) dec.readObject();
		dec.close();
		return level;
	}

	@Override
	public void save(OutputStream output, Level level) {
		XMLEncoder encode = new XMLEncoder(output);
		encode.writeObject(level);
		encode.close();
	}
}
