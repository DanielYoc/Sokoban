package strips;

import java.util.HashMap;

import model.data.Box;
import model.data.Player;
import model.data.Position;
import plannable.Action;
import plannable.State;
import predicates.IPredicate;

public class MoveAction extends Action {

	private static HashMap<String, MoveAction> m_Moves = new HashMap<String, MoveAction>();

	public static MoveAction getInstance(String direction) {
		MoveAction action = m_Moves.get(direction);
		if (action == null) {
			action = new MoveAction(direction);
			m_Moves.put(direction, action);
		}
		return action;
	}

	private String m_Direction;

	private MoveAction(String direction) {
		m_Direction = direction;
	}

	@Override
	public void execute(State state) {
		AtPredicate playerPred = null;
		for (IPredicate pred : state.getPredicates()) {
			if (pred instanceof AtPredicate && ((AtPredicate) pred).getElementType().equals(Player.class)) {
				playerPred = (AtPredicate) pred;
				break;
			}
		}
		if (playerPred == null)
			return;
		state.removePredicate(playerPred);
		Position newPlayerPos;
		AtPredicate boxPred;
		switch (m_Direction) {
		case "move right":
			newPlayerPos = new Position(playerPred.getPosition().getX() + 1, playerPred.getPosition().getY());
			state.addPredicate(new AtPredicate(playerPred.getElementType(), newPlayerPos));
			boxPred = getBoxAt(state, newPlayerPos);
			if (boxPred != null) {
				state.removePredicate(boxPred);
				state.addPredicate(new AtPredicate(boxPred.getElementType(),
						new Position(newPlayerPos.getX() + 1, newPlayerPos.getY())));
			}
			break;
		case "move left":
			newPlayerPos = new Position(playerPred.getPosition().getX() - 1, playerPred.getPosition().getY());
			state.addPredicate(new AtPredicate(playerPred.getElementType(), newPlayerPos));
			boxPred = getBoxAt(state, newPlayerPos);
			if (boxPred != null) {
				state.removePredicate(boxPred);
				state.addPredicate(new AtPredicate(boxPred.getElementType(),
						new Position(newPlayerPos.getX() - 1, newPlayerPos.getY())));
			}
			break;
		case "move up":
			newPlayerPos = new Position(playerPred.getPosition().getX(), playerPred.getPosition().getY() - 1);
			state.addPredicate(new AtPredicate(playerPred.getElementType(), newPlayerPos));
			boxPred = getBoxAt(state, newPlayerPos);
			if (boxPred != null) {
				state.removePredicate(boxPred);
				state.addPredicate(new AtPredicate(boxPred.getElementType(),
						new Position(newPlayerPos.getX(), newPlayerPos.getY() - 1)));
			}
			break;
		case "move down":
			newPlayerPos = new Position(playerPred.getPosition().getX(), playerPred.getPosition().getY() + 1);
			state.addPredicate(new AtPredicate(playerPred.getElementType(), newPlayerPos));
			boxPred = getBoxAt(state, newPlayerPos);
			if (boxPred != null) {
				state.removePredicate(boxPred);
				state.addPredicate(new AtPredicate(boxPred.getElementType(),
						new Position(newPlayerPos.getX(), newPlayerPos.getY() + 1)));
			}
			break;
		default:
			System.out.println("Unknow action to execute");
			break;
		}
	}

	private AtPredicate getBoxAt(State state, Position position) {
		for (IPredicate pred : state.getPredicates()) {
			if (pred instanceof AtPredicate && ((AtPredicate) pred).getElementType().equals(Box.class)
					&& ((AtPredicate) pred).getPosition().equals(position)) {
				return ((AtPredicate) pred);
			}
		}
		return null;
	}

	@Override
	public String toString(){
		return m_Direction;
	}
}
