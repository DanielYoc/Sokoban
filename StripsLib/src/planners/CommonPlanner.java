package planners;

import java.util.List;

import plannable.Action;
import predicates.IPredicate;

public abstract class CommonPlanner implements IPlanner {

	protected Action getSatisfyingAction(IPredicate p, List<Action> actions) {
		for (Action action : actions) {
			for (IPredicate effectPredict : action.getEffectList()) {
				if (p.equals(effectPredict)) {
					return action;
				}
			}
		}
		return null;
	}
/*
	private Map<String, Object> findAssignment(IPredicate p, IPredicate genericP, Action action) {
		Map<String, Object> assignment = new HashMap<String, Object>();

		Object[] args1 = p.getArgs();
		Object[] args2 = genericP.getArgs();
		Map<String, List<Object>> illegalAssignments = action.getIllegalAssignments();

		// Find match between action's variables and the predicate's constants
		for (int i = 0; i < args2.length; i++) {
			if (illegalAssignments.get(args2[i]) != null) {
				if (illegalAssignments.get(args2[i]).contains(args1[i]))
					return null;
			}
			assignment.put((String) args2[i], args1[i]);
		}

		List<Object> unusedObjects = new ArrayList<Object>();
		unusedObjects.addAll(objects);
		unusedObjects.removeAll(assignment.values());

		// Find all unassigned variables (from the action's arguments)
		for (Object variable : action.getArgs()) {
			if (!assignment.containsKey(variable)) {
				// Remove all illegal assignments
				List<Object> legalObjects = new ArrayList<Object>();
				legalObjects.addAll(unusedObjects);
				if (illegalAssignments.get(variable) != null) {
					legalObjects.removeAll(illegalAssignments.get(variable));
				}

				// Choose a random legal object
				int idx = rand.nextInt(legalObjects.size());
				Object chosenObject = legalObjects.get(idx);
				unusedObjects.remove(chosenObject);

				assignment.put((String) variable, chosenObject);
			}
		}

		return assignment;
	}*/
}
