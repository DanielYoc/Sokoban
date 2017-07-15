package boot;


import java.io.*;
import java.util.List;

import controller.Controller;
import controller.SokobanController;
import model.MyModel;
import model.data.*;
import model.factories.LevelLoaderFactory;
import model.policy.MySokobanPolicy;
import planners.CantSolvedException;
import view.CliView;

public class Run {
	public static void main(String[] args) {
		MyModel model = new MyModel(new MySokobanPolicy());

		if (args != null && args.length >= 2) {
			String path = args[0];
			LevelLoaderFactory factory = new LevelLoaderFactory();
			ILevelLoader loader = factory.createLoader(path);
			Level level;
			try {
				System.out.println("loading level \""+args[0]+"\"");
				level = loader.load(new FileInputStream(path));
				model.setLevel(level);
				System.out.println("Plan...");
				List<String> plan = model.plan();
				System.out.println("save solution to file \"" + args[1] + "\"");
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])));
				for (String p : plan) {
					out.write(p);
					out.newLine();
				}
				out.flush();
				out.close();
				System.out.println("exit");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (CantSolvedException e) {
				System.out.println("Level can't be solved.");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			CliView view = new CliView();
			SokobanController controller = new SokobanController(new Controller(), model, view);
			model.addObserver(controller);
			view.addObserver(controller);
			controller.start();
			view.listen();
		}

	}
}
