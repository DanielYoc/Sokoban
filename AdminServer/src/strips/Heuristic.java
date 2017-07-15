package strips;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.data.*;
import model.policy.IPolicy;
import plannable.*;
import planners.CantSolvedException;
import planners.IHeuristic;
import predicates.IPredicate;
import searchable.OnePathState;
import searchable.BFSCallable;
import searchable.OnePathSearchable;

public class Heuristic implements IHeuristic {

	private Level m_Level;
	private IPolicy m_Policy;

	public Heuristic(Level level, IPolicy policy) {
		m_Level = level;
		m_Policy = policy;
	}

	@Override
	public List<IPredicate> decomposeGoal(GoalState g) {
		ArrayList<IPredicate> decom = new ArrayList<>();
		decom.addAll(g.getPredicates());
		return decom;
	}

	/**
	 * for each box(that not on target) check satisfied actions to path and
	 * return the actions for the short path
	 * 
	 * @throws CantSolvedException
	 *             when not find any path to satisfied the predicate
	 */
	@Override
	public List<Action> GetSatisfiedAction(IPredicate predicate, State state) throws CantSolvedException {
		if (predicate instanceof AtPredicate) {
			AtPredicate pred = (AtPredicate) predicate;
			if (pred.getElementType().equals(Box.class)) {
				
				// run the path search for each box on different thread
				List<BFSCallable> BFSCallableList = createTargetSearchables(pred.getPosition(), state);
				ExecutorService threadPool = Executors.newFixedThreadPool(5);
				List<Future<List<data.Action<?>>>> futureResults;
				try {
					futureResults = threadPool.invokeAll(BFSCallableList);
					List<data.Action<?>> minActions = null;// looking for minimum actions
					for (Future<List<data.Action<?>>> future : futureResults) {
						List<data.Action<?>> actions = future.get();
						if (actions != null && (minActions == null || actions.size() < minActions.size()))
							minActions = actions;
					}
					if (minActions != null) {
						ArrayList<Action> actions = new ArrayList<>();
						for (data.Action<?> ac : minActions)
							actions.add(MoveAction.getInstance(ac.getAction().toString()));
						return actions;
					}
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					throw new CantSolvedException();
				}

			}
		}
		throw new CantSolvedException();
	}

	private List<BFSCallable> createTargetSearchables(Position targetPostion, State state) {
		ArrayList<BFSCallable> searchableList = new ArrayList<>();
		for (IPredicate p : state.getPredicates()) {
			if (p instanceof AtPredicate && ((AtPredicate) p).getElementType().equals(Box.class)) {
				AtPredicate boxP = (AtPredicate) p;

				// check if the box is not on target and add the box to list
				if (state.getPredicates().contains(new AtPredicate(Target.class, boxP.getPosition())) == false) {
					Class<?>[][] stateBoard = new Class[m_Level.getBoard().length][];
					for (int i = 0; i < stateBoard.length; i++)
						stateBoard[i] = new Class<?>[m_Level.getBoard()[i].length];
					for (IPredicate pred : state.getPredicates()) {
						if (pred instanceof AtPredicate) {
							AtPredicate atPredicate = (AtPredicate) pred;
							Class<?> ty = atPredicate.getElementType();

							// if the element is box, set it as wall that the
							// algorithm will not check this option
							// in the end of the loop we will set the box we
							// want check path from in the board
							if (ty.equals(Box.class) || ty.equals(Wall.class)) {
								stateBoard[atPredicate.getPosition().getY()][atPredicate.getPosition()
										.getX()] = Wall.class;
							} else {
								if (Target.class.equals(ty) == false)
									stateBoard[atPredicate.getPosition().getY()][atPredicate.getPosition().getX()] = ty
											.equals(Player.class) ? Player.class : null;
							}
						}
					}
					OnePathSearchable searchable = new OnePathSearchable(m_Policy);

					// set the box for the algorithm to search path for
					stateBoard[boxP.getPosition().getY()][boxP.getPosition().getX()] = Box.class;
					searchable.setInitialState(new OnePathState(stateBoard));

					// create the goal state (where the box need to be)
					Class<?>[][] goalBoard = new Class[stateBoard.length][];
					for (int i = 0; i < goalBoard.length; i++)
						goalBoard[i] = stateBoard[i].clone();
					goalBoard[boxP.getPosition().getY()][boxP.getPosition().getX()] = null;
					goalBoard[targetPostion.getY()][targetPostion.getX()] = Box.class;
					searchable.setGoalState(new OnePathState(goalBoard));
					searchableList.add(new BFSCallable(searchable));
				}
			}
		}
		return searchableList;
	}

}
