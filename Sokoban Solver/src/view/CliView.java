package view;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import model.data.Level;
import model.data.TxtLevelLoader;

public class CliView extends Observable implements IView {

	private Level m_Level;
	private TxtLevelLoader m_LevelWriter;

	public CliView() {
		m_LevelWriter = new TxtLevelLoader();
	}

	public void listen() {
		System.out.println(
				"Hello and welcome to Sokoban game!\n1. Enter \"exit\" to end the game,\n2. Enter \"load <level path>\" to load level.\n3. Enter \"save <level path>\" to save level.\n4. Enter \"move right, move left, move up, move down\" to move.\n5. Enter \"solution\" to see the solution.");
		String input;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			while ((input = reader.readLine()).equals("exit") == false) {
				setChanged();
				ArrayList<Object> params = new ArrayList<>(Arrays.asList(input.split(" ")));
				notifyObservers(params);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void paint() {
		m_LevelWriter.save(System.out, m_Level);
		System.out.println("\n");
	}

	public void winning() {
		m_Level = null;
		System.out.println("You finished the game!");
	}

	@Override
	public void setLevel(Level level) {
		m_Level = level;
	}

	@Override
	public void setSolution(List<String> plan) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				for (String ac : plan) {
					ArrayList<Object> params = new ArrayList<>(Arrays.asList(ac.split(" ")));
					setChanged();
					notifyObservers(params);
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	@Override
	public void levelCantSolvedMessage() {
		System.out.println("The program failed to find soultion for the level.\nPlease check that the level can be solved.");		
	}

}
