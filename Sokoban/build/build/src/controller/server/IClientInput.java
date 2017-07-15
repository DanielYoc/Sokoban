package controller.server;

import java.io.InputStream;

import controller.SokobanController;
import controller.commands.Command;
import model.IModel;
import view.IView;

public interface IClientInput {

	public Command read(InputStream input, SokobanController controller, IModel model, IView view);
}
