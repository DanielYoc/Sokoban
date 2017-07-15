package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

import javafx.geometry.Side;
import model.data.Record;
import model.data.RecordsDataMapper;
import model.data.SolutionsGateway;
import model.policy.MySokobanPolicy;
import model.serverProtocol.Command;
import model.serverProtocol.SolutionRespone;
import plannable.Action;
import planners.CantSolvedException;
import planners.Strips;
import strips.Heuristic;
import strips.SokoSolverAdapter;

public class PeerCallable extends Observable implements Callable<Boolean> {

	private InputStream m_InputStream;
	private OutputStream m_OutputStream;
	private Peer m_Peer;
	private SolutionsGateway m_SolutionGateway;

	public PeerCallable(Socket socket, Peer peer, SolutionsGateway solutionGateway) {
		m_Peer = peer;
		m_SolutionGateway = solutionGateway;
		try {
			m_InputStream = socket.getInputStream();
			m_OutputStream = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Boolean call() throws Exception {
		ObjectClientInput clientInput = new ObjectClientInput();
		Command command = clientInput.read(m_InputStream);

		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(m_OutputStream);
		} catch (IOException e2) {
			e2.printStackTrace();
			return false;
		}

		switch (command.getName()) {
		case "searchRecords":
			m_Peer.setMissionProperty("Search records");
			RecordsDataMapper searchDataMapper = RecordsDataMapper.getInstance();
			Record[] resp = searchDataMapper.search(command.getQuery());
			try {
				objOut.writeObject(resp);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case "addRecord":
			m_Peer.setMissionProperty("Add new recored");
			RecordsDataMapper recordDataMapper = RecordsDataMapper.getInstance();
			recordDataMapper.saveRecored(command.getRecord());
			break;
		case "solution":
			m_Peer.setMissionProperty("Get solution");
			int levelHashCode = command.getLevel().hashCode();
			SolutionRespone respone = null;
			if (m_SolutionGateway.isExist(levelHashCode)) {
				respone = m_SolutionGateway.getSolution(levelHashCode);
			} else {
				respone = new SolutionRespone();
				String[] actionsCommand = null;
				SokoSolverAdapter solver = new SokoSolverAdapter(command.getLevel());
				Heuristic heuristic = new Heuristic(command.getLevel(), new MySokobanPolicy());
				boolean canSolved = true;
				Strips strips = new Strips(solver, heuristic);
				try {
					List<Action> actions = strips.computePlan();
					actionsCommand = new String[actions.size()];
					for (int i = 0; i < actionsCommand.length; i++)
						actionsCommand[i] = (actions.get(i).toString());
				} catch (CantSolvedException e) {
					canSolved = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
				respone.setSolvable(canSolved);
				respone.setActions(actionsCommand);
				m_SolutionGateway.saveSolution(levelHashCode, canSolved, actionsCommand);
			}
			try {
				objOut.writeObject(respone);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				objOut.close();
			}
			break;
		}

		setChanged();
		notifyObservers(m_Peer);
		return true;
	}

}
