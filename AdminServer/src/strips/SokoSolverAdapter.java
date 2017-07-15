package strips;

import java.util.ArrayList;
import java.util.List;

import model.data.*;
import plannable.*;
import predicates.IPredicate;

public class SokoSolverAdapter implements IPlannable {

	private Level m_Level;

	public SokoSolverAdapter(Level level) {
		m_Level = level;
	}

	@Override
	public GoalState getGoal() {
		List<Target> targets = m_Level.getAllTargets();
		ArrayList<IPredicate> goalList = new ArrayList<IPredicate>();
		for (Target t : targets) {
			goalList.add(new AtPredicate(Box.class, t.getPosition()));
		}
		return new GoalState(goalList);
	}

	@Override
	public State getInitialState() {
		Player player = m_Level.getSelectedPlayer();
		List<Box> boxes = m_Level.getAllBoxes();
		List<Wall> walls = m_Level.getAllWalls();
		List<Target> targets = m_Level.getAllTargets();
		State state = new State();
		state.addPredicate(new AtPredicate(Player.class, new Position(player.getPosition())));
		for (Box b : boxes) {
			state.addPredicate(new AtPredicate(Box.class, new Position(b.getPosition())));
		}
		for (Target t : targets) {
			state.addPredicate(new AtPredicate(Target.class, new Position(t.getPosition())));
		}
		for (Wall w : walls) {
			state.addPredicate(new AtPredicate(Wall.class, new Position(w.getPosition())));
		}

		return state;
	}

	public GameElement getPlayer() {
		return m_Level.getSelectedPlayer();
	}

}
