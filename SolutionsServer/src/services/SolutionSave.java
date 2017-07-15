package services;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import model.Action;
import model.SolutionDataMapper;
import model.State;

/**
 * provide save solution service
 */
@Path("/SolutionSave/{value}")
public class SolutionSave {

	@GET
	public boolean saveLevel(@PathParam("value") String value) {
		System.out.println("recived: " + value);
		String[] arr = value.split(",");
		State state = new State();
		state.setHashCode(Integer.parseInt(arr[0]));
		state.setSolvedable(Boolean.parseBoolean(arr[1]));
		if (state.getSolvedable()) {
			ArrayList<Action> actions = new ArrayList<Action>();
			for (int i = 2; i < arr.length; i++) {
				Action act = new Action();
				act.setIndex(i - 1);
				act.setActionType(arr[i]);
				act.setStateId(state.getHashCode());
				actions.add(act);
			}
			Action[] arrActions = new Action[actions.size()];
			actions.toArray(arrActions);
			state.setActions(arrActions);
		}
		SolutionDataMapper dm = new SolutionDataMapper();
		dm.insertState(state);
		return true;
	}

}
