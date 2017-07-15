package model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import data.Action;
import data.State;

public abstract class CommonSearcher<T> implements Searcher<T> {

	private PriorityQueue<State<T>> openQueue;
	private int evaluatedNodes;

	protected CommonSearcher() {
		this.openQueue = new PriorityQueue<>();
	}

	protected void initOpenQueue(State<T> initState) {
		this.openQueue.clear();
		this.evaluatedNodes = 0;
		this.openQueue.add(initState);
	}
	
	protected void initOpenQueue(List<State<T>> initState) {
		this.openQueue.clear();
		this.evaluatedNodes = 0;
		this.openQueue.addAll(initState);
	}

	protected void pushToOpenQueue(State<T> state) {
		this.openQueue.add(state);
	}

	protected PriorityQueue<State<T>> getOpenQueue() {
		return openQueue;
	}

	protected State<T> poolOpenQueue() {
		return this.openQueue.poll();
	}

	protected State<T> getStateFromOpenQueue(State<T> state) {
		for (State<T> s : this.openQueue)
			if (s.equals(state)) {
				return s;
			}
		return null;
	}

	protected void replaceStates(State<T> removeState, State<T> newState) {
		this.openQueue.remove(removeState);
		this.openQueue.add(newState);
	}

	protected void increaseEvaluatedNodes() {
		this.evaluatedNodes++;
	}

	protected ArrayList<Action<?>> backTrace(State<T> state) {
		Stack<Action<?>> stack = new Stack<Action<?>>();
		while (state.getParent() != null) {
			stack.push(state.getAction());
			state = state.getParent();
		}
		ArrayList<Action<?>> list = new ArrayList<>();
		while (stack.isEmpty() == false)
			list.add(stack.pop());
		return list;
	}

	@Override
	public int getNumberOfNodesEvaluated() {
		return this.evaluatedNodes;
	}

}
