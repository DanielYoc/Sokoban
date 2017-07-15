package planners;

import java.util.List;

import plannable.Action;

public interface IPlanner {
	List<Action> computePlan() throws Exception;
}
