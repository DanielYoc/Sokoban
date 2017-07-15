package data;

import java.util.List;

public interface Searchable<T> {
	public State<T> getInitialState();
	
	public State<T> getGoalState();
	
	public boolean isGoalSatisfied(State<T> state);
	
	//successors
	public List<State<T>> getAllPossibleStates(State<T> state);
	
	public List<State<T>> getAllPossibleStates();
}
