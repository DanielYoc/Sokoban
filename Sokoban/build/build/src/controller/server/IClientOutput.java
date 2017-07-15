package controller.server;

import java.io.OutputStream;

public interface IClientOutput {
	public void write(OutputStream out, String text);
}
