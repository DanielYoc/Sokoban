package data;

public class Action<T> {
	private T action;

	public Action(T action) {
		this.action = action;
	}

	public T getAction() {
		return action;
	}

	public void setAction(T action) {
		this.action = action;
	}

	public boolean equals(Action<T> obj) {
		return this.action.equals(obj.action);
	}

	@Override
	public int hashCode() {
		return action.hashCode();
	}
	
	@Override
	public String toString(){
		return action.toString();
	}
}
