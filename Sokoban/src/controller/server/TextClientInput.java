package controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import controller.SokobanController;
import controller.commands.Command;
import controller.commands.CommandsFactory;
import model.IModel;
import view.IView;

public class TextClientInput implements IClientInput {

	@Override
	public Command read(InputStream input, SokobanController controller, IModel model, IView view) {

		String s = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		try {
			s = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommandsFactory cf = new CommandsFactory(controller, model, view);
		Command cmd = cf.GetCommand(s.split(" ")[0]);
		ArrayList<Object> list = new ArrayList<>();
		list.add(s.split(" ")[1]);
		cmd.setParams(list);
		return cmd;
	}

}
