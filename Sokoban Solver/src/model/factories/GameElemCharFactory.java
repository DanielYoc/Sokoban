package model.factories;

import java.util.*;
import model.data.*;

/**
 * the class encoder game element to char
 */
public class GameElemCharFactory {
	private HashMap<String, Character> m_Map;

	public GameElemCharFactory() {
		m_Map = new HashMap<String, Character>();
		m_Map.put("Wall", '#');
		m_Map.put("PlayerWall", '&');
		m_Map.put("BoxWall", '?');
		m_Map.put("Floor", ' ');
		m_Map.put("BoxFloor", '@');
		m_Map.put("FloorPlayer", 'A');
		m_Map.put("Target", 'o');
		m_Map.put("PlayerTarget", 'a');
		m_Map.put("BoxTarget", '*');
		m_Map.put("", '\t');
	}

	public char getChar(List<GameElement> items) {
		ArrayList<String> names = new ArrayList<>();
		for (GameElement ge : items)
			names.add(getClassName(ge));
		
		Collections.sort(names);
		String concatName = "";
		
		for (String n : names)
			concatName += n;
		return m_Map.get(concatName);
	}

	private String getClassName(Object obj) {
		String className = obj.getClass().getName();
		return className.substring(className.lastIndexOf('.') + 1);
	}
}
