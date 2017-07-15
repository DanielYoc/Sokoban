package data;

public class State<T> implements Comparable<State<T>> {
	private T state;
	private int cost;
	private State<T> parent;
	private Action<?> action;

	public State(T state) {
		this.cost = 0;
		this.parent = null;
		this.setState(state);
	}

	public State(T state, State<T> parent, int cost, Action<?> action) {
		this.cost = cost;
		this.parent = parent;
		this.action = action;
		this.setState(state);
	}

	public State<T> getParent() {
		return parent;
	}

	public void setParent(State<T> parent) {
		this.parent = parent;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public boolean equals(State<T> s) {
		return s.state.equals(this.state);
	}

	public Action<?> getAction() {
		return action;
	}

	public void setAction(Action<?> action) {
		this.action = action;
	}
	
	@Override
	public int hashCode() {
		return state.hashCode();
	}
	
	@Override
	public int compareTo(State<T> o) {
		return this.cost - o.cost;
	}

	@Override
	public String toString() {
		return this.state.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		State<T> s=(State<T>)obj;
		return state.equals(s.state);
	}
	
}
