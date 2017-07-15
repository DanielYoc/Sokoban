package searchable;

import java.util.List;
import java.util.concurrent.Callable;

import data.Action;
import model.BFS;

/**
 * the class represent one path search  that can run on different thread
 */
public class BFSCallable implements Callable<List<data.Action<?>>> {
	private OnePathSearchable m_SubStateSearchable;

	public BFSCallable(OnePathSearchable subStateSearchable) {
		m_SubStateSearchable = subStateSearchable;
	}

	@Override
	public List<Action<?>> call() {
		BFS<OnePathState> bfs = new BFS<>();
		return bfs.search(m_SubStateSearchable);
	}
}
