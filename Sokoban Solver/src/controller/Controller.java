package controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import controller.commands.Command;

/**
 * manage commands execute
 */
public class Controller {

	protected BlockingQueue<Command> m_CommandsQueue;
	protected boolean m_End;

	public Controller() {
		m_CommandsQueue = new ArrayBlockingQueue<Command>(10);
		m_End = false;
	}

	public void insertCommand(Command command) {
		try {
			m_CommandsQueue.put(command);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		m_End = true;
	}

	public void start() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (m_End == false) {
					try {
						Command command = m_CommandsQueue.take();
						command.execute();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
}
