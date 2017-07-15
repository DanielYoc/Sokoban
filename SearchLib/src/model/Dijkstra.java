package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import data.Action;
import data.Searchable;
import data.State;

public class Dijkstra<T> extends CommonSearcher<T> {

	@Override
	public ArrayList<Action<?>> search(Searchable<T> searchable) {
		initOpenQueue(searchable.getAllPossibleStates());
		HashSet<State<T>> close = new HashSet<State<T>>();
		State<T> currentState;
		while ((currentState = poolOpenQueue()) != null) {
			increaseEvaluatedNodes();
			close.add(currentState);
			if (searchable.isGoalSatisfied(currentState)) {
				return backTrace(currentState);
			}
			List<State<T>> successors = searchable.getAllPossibleStates(currentState);
			for (State<T> s : successors) {
				if (close.contains(s) == false) {
					State<T> duplicateState;
					if ((duplicateState = getStateFromOpenQueue(s)) == null) {
						pushToOpenQueue(s);
					} else {
						if (duplicateState.getCost() > s.getCost()) {
							replaceStates(duplicateState, s);
						}
					}
				}
			}
		}
		return null;
	}
}
