package model.data;

import java.io.InputStream;
import java.io.OutputStream;
/**
 * Represent level load and save from/to file
 */
public interface ILevelLoader {
	
	/**
	 * Load level from the input stream
	 * @param input the stream to read from
	 * @return the level
	 */
    Level load(InputStream input);

    /**
     * Save level to the output stream
     * @param output the output to save the level to
     * @param level the level to save
     */
    void save(OutputStream output,Level level);
}
