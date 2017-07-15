package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import commons.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.data.GameElement;
import model.data.IMovable;

public class LevelControl extends Canvas {

	private ILevelReadOnly m_Level;
	private HashMap<String, Image> m_ElementsImage;
	private StringProperty m_BoxFileName;
	private StringProperty m_PlayerStandFileName;
	private StringProperty m_PlayerRightFileName;
	private StringProperty m_PlayerLeftFileName;
	private StringProperty m_WallFileName;
	private StringProperty m_FloorFileName;
	private StringProperty m_TargetFileName;

	public LevelControl() {
		m_BoxFileName = new SimpleStringProperty();
		m_PlayerStandFileName = new SimpleStringProperty();
		m_PlayerRightFileName = new SimpleStringProperty();
		m_PlayerLeftFileName = new SimpleStringProperty();
		m_WallFileName = new SimpleStringProperty();
		m_FloorFileName = new SimpleStringProperty();
		m_TargetFileName = new SimpleStringProperty();
		setFocusTraversable(true);
	}

	public void init() {
		m_ElementsImage = new HashMap<String, Image>();
		m_ElementsImage.put("Box", new Image(new File(m_BoxFileName.get()).toURI().toString(), true));
		m_ElementsImage.put("Player", new Image(new File(m_PlayerStandFileName.get()).toURI().toString(), true));
		m_ElementsImage.put("Wall", new Image(new File(m_WallFileName.get()).toURI().toString(), true));
		m_ElementsImage.put("Floor", new Image(new File(m_FloorFileName.get()).toURI().toString(), true));
		m_ElementsImage.put("Target", new Image(new File(m_TargetFileName.get()).toURI().toString(), true));
	}

	public void setLevel(ILevelReadOnly level) {
		m_Level = level;
	}

	public void redraw() {
		if (m_Level == null)
			return;
		double winWidth = getWidth();
		double winHeight = getHeight();
		double elemWidth = winWidth / Math.max(m_Level.getBoard()[0].length, m_Level.getBoard().length);

		GraphicsContext g = getGraphicsContext2D();
		g.clearRect(0, 0, winWidth, winHeight);

		ArrayList<GameElement> moves = new ArrayList<>();
		for (ICellReadOnly[] row : m_Level.getBoard()) {
			for (ICellReadOnly cell : row) {
				for (GameElement ge : cell.getElementsArray())
					if (ge instanceof IMovable == false) {
						String key = ge.getClass().getName();
						key = key.substring(key.lastIndexOf('.') + 1);
						g.setFill(new ImagePattern(m_ElementsImage.get(key)));
						g.fillRect(ge.getPosition().getX() * elemWidth, ge.getPosition().getY() * elemWidth, elemWidth,
								elemWidth);

					} else {
						moves.add(ge);
					}

			}
		}
		
		for (GameElement ge : moves) {
			String key = ge.getClass().getName();
			key = key.substring(key.lastIndexOf('.') + 1);
			g.setFill(new ImagePattern(m_ElementsImage.get(key)));
			g.fillRect(ge.getPosition().getX() * elemWidth, ge.getPosition().getY() * elemWidth, elemWidth, elemWidth);
		}

	}

	public String getPlayerStandFileName() {
		return m_PlayerStandFileName.get();
	}

	public void setPlayerStandFileName(String path) {
		m_PlayerStandFileName.set(path);
	}

	public String getPlayerRightFileName() {
		return m_PlayerRightFileName.get();
	}

	public void setPlayerRightFileName(String path) {
		m_PlayerRightFileName.set(path);
	}

	public String getPlayerLeftFileName() {
		return m_PlayerLeftFileName.get();
	}

	public void setPlayerLeftFileName(String path) {
		m_PlayerLeftFileName.set(path);
	}

	public String getBoxFileName() {
		return m_BoxFileName.get();
	}

	public void setBoxFileName(String path) {
		m_BoxFileName.set(path);
	}

	public String getWallFileName() {
		return m_WallFileName.get();
	}

	public void setWallFileName(String path) {
		m_WallFileName.set(path);
	}

	public String getFloorFileName() {
		return m_FloorFileName.get();
	}

	public void setFloorFileName(String path) {
		m_FloorFileName.set(path);
	}

	public String getTargetFileName() {
		return m_TargetFileName.get();
	}

	public void setTargetFileName(String path) {
		m_TargetFileName.set(path);
	}

}
