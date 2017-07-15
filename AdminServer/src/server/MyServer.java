package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.data.SolutionsGateway;

/**
 * The server manager
 */
public class MyServer extends Observable implements Observer {
	private ServerSocket m_Socket;
	private ExecutorService m_ThreadPool;
	private ConcurrentHashMap<Peer, Future<Boolean>> m_ConnectionsManager;
	private Thread m_MainThread;
	private SolutionsGateway m_SolutionGateway;
	private boolean m_StopServer = false;
	private int m_MaxAllowedConnections;
	private int m_ListenPort;

	public MyServer(int port, SolutionsGateway solutionGateway) throws IOException {
		m_SolutionGateway = solutionGateway;
		m_ThreadPool = Executors.newFixedThreadPool(10);
		m_MaxAllowedConnections = 10;
		m_ConnectionsManager = new ConcurrentHashMap<>();
		m_MainThread = null;
		m_ListenPort=port;
	}

	/**
	 * Start listen for incoming requests
	 */
	public void startServer() {
		if (m_MainThread != null)
			return;
		m_StopServer = false;
		MyServer server = this;
		try {
			m_Socket = new ServerSocket(m_ListenPort);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		m_MainThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Server start...");
				while (m_StopServer == false) {
					try {
						Socket clientSocket;
						try {
							clientSocket = m_Socket.accept();
						} catch (SocketException e) {
							continue;
						}
						if (m_MaxAllowedConnections <= m_ConnectionsManager.size()) {
							clientSocket.close();
							continue;
						}
						Peer peer = new Peer(clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
						PeerCallable callable = new PeerCallable(clientSocket, peer, m_SolutionGateway);
						callable.addObserver(server);
						Future<Boolean> future = m_ThreadPool.submit(callable);
						m_ConnectionsManager.put(peer, future);
						server.setChanged();
						List<Object> params = new ArrayList<Object>();
						params.add("connected");
						params.add(peer);
						server.notifyObservers(params);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		m_MainThread.start();
	}

	/**
	 * Stop listen for incoming requests
	 */
	public void stopServer() {
		if (m_MainThread == null)
			return;
		m_StopServer = true;
		m_MainThread.interrupt();
		m_MainThread = null;
		for (Future<?> f : m_ConnectionsManager.values())
			f.cancel(true);
		m_ConnectionsManager.clear();
		try {
			m_Socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disconnected the given peer from server
	 * @param peer the peer to disconnected
	 */
	public void forceDisconnectClient(Peer peer) {
		Future<Boolean> future = m_ConnectionsManager.get(peer);
		if (future != null) {
			future.cancel(true);
			m_ConnectionsManager.remove(peer);
			setChanged();
			List<Object> params = new ArrayList<Object>();
			params.add("disconnected");
			params.add(peer);
			notifyObservers(params);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (m_ConnectionsManager.containsKey(arg1)) {
			m_ConnectionsManager.remove(arg1);
			setChanged();
			List<Object> params = new ArrayList<Object>();
			params.add("disconnected");
			params.add(arg1);
			notifyObservers(params);
		}
	}

	public int getMaxAllowedConnections() {
		return m_MaxAllowedConnections;
	}

	public void setMaxAllowedConnections(int maxAllowedConnections) {
		m_MaxAllowedConnections = maxAllowedConnections;
	}
}
