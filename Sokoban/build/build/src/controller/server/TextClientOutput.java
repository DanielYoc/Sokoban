package controller.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class TextClientOutput implements IClientOutput {

	@Override
	public void write(OutputStream output, String text) {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		try {
			writer.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
