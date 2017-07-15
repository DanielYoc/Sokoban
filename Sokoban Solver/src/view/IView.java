package view;

import java.util.List;

import model.data.Level;
public interface IView {

	public void paint();

	public void setLevel(Level level);
	
	public void winning();

	public void setSolution(List<String> plan);

	public void levelCantSolvedMessage();
	
}
