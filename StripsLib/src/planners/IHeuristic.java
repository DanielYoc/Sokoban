package planners;

import java.util.List;

import plannable.Action;
import plannable.GoalState;
import plannable.State;
import predicates.IPredicate;

public interface IHeuristic {
	
	List<IPredicate> decomposeGoal(GoalState g);
	
	List<Action> GetSatisfiedAction(IPredicate predicate,State state)throws CantSolvedException;

}
