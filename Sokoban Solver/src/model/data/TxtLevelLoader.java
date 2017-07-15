package model.data;

import model.factories.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Text save and load level
 */
public class TxtLevelLoader implements ILevelLoader {

	private boolean m_CloseOutpet;

	public TxtLevelLoader() {
		CloseOutpetOnFinish(true);
	}

	@Override
	public Level load(InputStream input) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		GameElementsFactory factory = new GameElementsFactory();

		ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
		String line;
		String name = "";
		char ch;
		int row = 0, maxCell = 0;
		Player selectedPlayer = null;
		try {
			name = reader.readLine();
			while ((line = reader.readLine()) != null) {
				board.add(new ArrayList<Cell>());
				if (line.length() > maxCell)
					maxCell = line.length();

				for (int col = 0; col < line.length(); col++) {
					Cell cell = new Cell();
					ch = line.charAt(col);
					GameElement[] elems = factory.CreateElements(ch);
					for (GameElement ge : elems) {
						if (ge instanceof Player)
							selectedPlayer = (Player) ge;

						ge.setPosition(new Position(col, row));
						cell.getElements().add(ge);
					}
					board.get(row).add(cell);
				}
				row++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Cell[][] cells = new Cell[board.size()][maxCell];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = board.get(i).size() <= j ? new Cell() : board.get(i).get(j);
			}
		}
		return new Level(name, cells, selectedPlayer);
	}

	@Override
	public void save(OutputStream output, Level level) {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		GameElemCharFactory factory = new GameElemCharFactory();
		try {
			if (output instanceof PrintStream)
				((PrintStream) output).print(level.getName());
			else
				writer.write(level.getName());

			for (Cell[] row : level.getBoard()) {
				if (output instanceof PrintStream)
					((PrintStream) output).println();
				else
					writer.newLine();

				for (Cell cell : row) {

					if (output instanceof PrintStream)
						((PrintStream) output).print(factory.getChar(cell.getElements()));
					else
						writer.write(factory.getChar(cell.getElements()));
				}
			}
			if (output instanceof PrintStream == false)
				writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean CloseOutpetOnFinish() {
		return m_CloseOutpet;
	}

	public void CloseOutpetOnFinish(boolean closeOutpet) {
		this.m_CloseOutpet = closeOutpet;
	}

}
