package barricades;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Environment
 * @author Rui Henriques
 */
public class Board {

	/** A: Environment */

	public int nX, nY, nUAVs;
	public List<Agent> UAVs;
	public Cell[][] board;
	public GraphicalInterface GUI;
	
	public Board(int nX, int nY, int nUAVs) {
		this.nX = nX;
		this.nY = nY;
		this.nUAVs = nUAVs;
		initialize();
	}

	private void initialize() {
		UAVs = new ArrayList<Agent>();
		for(int i=0; i<nUAVs && i<nY; i++) UAVs.add(new Agent(new Point(0,i)));
		board = new Cell[nX][nY];
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				board[i][j] = new Cell();
	}

	
	/** B: Elicit agent actions */
	
	RunThread runThread;

	public class RunThread extends Thread {
		
		int time;
		private volatile boolean running = true;
		
		public RunThread(int time){
			this.time = time;

		}

		public void terminate(){
			running = false;
		}
		
	    public void run() {
	    	while(running){
		    	removeAgents();
				for(Agent a : UAVs) a.go();
				displayAgents();
				try {
					sleep(time*10);
				} catch (InterruptedException e) {
					this.interrupt();
				}
	    	}
	    }
	}
	
	public void run(int time) {
		runThread = new RunThread(time);
		runThread.start();
		displayAgents();
	}

	public void reset() {
		initialize();
		displayBoard();
		displayAgents();	
	}

	public void step() {
		removeAgents();
		for(Agent a : UAVs) a.go();
		displayAgents();
	}

	public void stop() {
		runThread.interrupt();
		runThread.terminate();
		displayAgents();
	}

	public void displayBoard(){
		GUI.displayBoard(this);
	}

	public void displayAgents(){
		GUI.displayAgents(this);
	}
	
	public void removeAgents(){
		GUI.removeAgents(this);
	}
}
