package model;

import java.util.ArrayList;

import data.Action;
import data.Searchable;

public interface Searcher<T> {
	public ArrayList<Action<?>> search(Searchable<T> searchable);
    public int getNumberOfNodesEvaluated();
}
