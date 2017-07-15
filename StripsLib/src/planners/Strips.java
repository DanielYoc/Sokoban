package planners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import plannable.Action;
import plannable.GoalState;
import plannable.IPlannable;
import plannable.State;
import predicates.IPredicate;

public class Strips extends CommonPlanner {

	private IPlannable m_Plannable;
	private Stack<IStackItem> m_Stack;
	private IHeuristic m_Heuristic;
	private State m_State;
	private final int MaxOpreationsCanBeMade = 100000;

	public Strips(IPlannable plannable, IHeuristic heuristic) {
		m_Plannable = plannable;
		m_Heuristic = heuristic;
		m_Stack = new Stack<IStackItem>();
	}

	@Override
	public List<Action> computePlan() throws Exception {
		m_Stack.clear();
		ArrayList<Action> backTrace = new ArrayList<>();
		int operationsMade = 0;
		m_State = m_Plannable.getInitialState();
		m_Stack.push(m_Plannable.getGoal());
		while (m_Stack.isEmpty() == false ) {
			if( operationsMade > MaxOpreationsCanBeMade)
				throw new CantSolvedException();
			Action action;
			if ((action = Step()) != null)
				backTrace.add(action);
			operationsMade++;
		}
		return backTrace;
	}

	private Action Step() throws Exception {
		IStackItem item = m_Stack.peek();

		// check if is the goal state
		if (item instanceof GoalState) {
			GoalState goal = ((GoalState) item);
			// check if the goal is satisfied
			if (m_State.isSatisfied(goal.getPredicates())) {
				m_Stack.pop();
				return null;
			}

			// if goal is not satisfied decompose is predicates
			List<IPredicate> decompsePredSet = m_Heuristic.decomposeGoal(goal);

			IPredicate[] decompsePredArr = new IPredicate[decompsePredSet.size()];
			decompsePredSet.toArray(decompsePredArr);
			for (int i = decompsePredArr.length - 1; i >= 0; i--) {
				m_Stack.push(decompsePredArr[i]);
			}
			return null;
		}

		if (item instanceof IPredicate) {
			IPredicate predicate = (IPredicate) item;
			if (m_State.isSatisfied(predicate)) {
				m_Stack.pop();
				return null;
			}			

			List<Action> actions = m_Heuristic.GetSatisfiedAction(predicate, m_State);
			Collections.reverse(actions);
			for (Action ac : actions) {
				m_Stack.add(ac);
				m_Stack.addAll(ac.getPreconditions());
			}
			return null;
		}

		if (item instanceof Action) {
			Action action = (Action) item;
			m_Stack.pop();
			action.execute(m_State);
			return action;
		}
		throw new Exception("Unkown stack item type");
	}
}
