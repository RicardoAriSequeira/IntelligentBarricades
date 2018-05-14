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
	public List<Car> UAVs;
	public Cell[][] board;
	public GraphicalInterface GUI;
	
	public Board(int nX, int nY, int nUAVs) {
		this.nX = nX;
		this.nY = nY;
		this.nUAVs = nUAVs;
		initialize();
	}

	private void initialize() {
		UAVs = new ArrayList<Car>();
		for(int i=0; i<nUAVs && i<nY; i++) UAVs.add(new Car(new Point(0,i)));
		board = new Cell[nX][nY];
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				board[i][j] = new Cell();

		for(int i=0; i<17; i++){
			board[i][3].setRoad();
			board[i][3].setNorthDirection();
		}
		for(int i=0; i<32; i++){
			board[i][8].setRoad();
			board[i][8].setSouthDirection();
		}
		for(int i=0; i<32; i++){
			board[i][9].setRoad();
			board[i][9].setNorthDirection();
		}
		for(int i=0; i<32; i++){
			board[i][22].setRoad();
			board[i][22].setSouthDirection();
		}
		for(int i=0; i<32; i++){
			board[i][23].setRoad();
			board[i][23].setNorthDirection();
		}
		for(int i=0; i<17; i++){
			board[i][13].setRoad();
			board[i][13].setSouthDirection();
		}
		for(int i=0; i<21; i++){
			board[i][18].setRoad();
			board[i][18].setNorthDirection();
		}
		for(int i=0; i<17; i++){
			board[i][28].setRoad();
			board[i][28].setSouthDirection();
		}
		for(int i=0; i<32; i++){
			board[3][i].setRoad();
			board[3][i].setEastDirection();
		}
		for(int i=0; i<32; i++){
			board[16][i].setRoad();
			board[16][i].setWestDirection();
		}
		for(int i=0; i<32; i++){
			board[17][i].setRoad();
			board[17][i].setEastDirection();
		}
		for(int i=0; i<32; i++){
			board[21][i].setRoad();
			board[21][i].setEastDirection();
		}		
		for(int i=0; i<32; i++){
			board[25][i].setRoad();
			board[25][i].setWestDirection();
		}
		for(int i=0; i<32; i++){
			board[29][i].setRoad();
			board[29][i].setEastDirection();
		}
		for(int i=0; i<4; i++){
			board[10][i].setRoad();
			board[10][i].setWestDirection();
		}
		for(int i=4; i<10; i++){
			board[7][i].setRoad();
			board[7][i].setWestDirection();
		}
		for(int i=4; i<10; i++){
			board[12][i].setRoad();
			board[12][i].setEastDirection();
		}
		for(int i=10; i<14; i++){
			board[10][i].setRoad();
			board[10][i].setWestDirection();
		}
		for(int i=14; i<18; i++){
			board[8][i].setRoad();
			board[8][i].setWestDirection();
		}
		for(int i=14; i<18; i++){
			board[13][i].setRoad();
			board[13][i].setEastDirection();
		}
		for(int i=19; i<22; i++){
			board[10][i].setRoad();
			board[10][i].setWestDirection();
		}
		for(int i=23; i<29; i++){
			board[12][i].setRoad();
			board[12][i].setWestDirection();
		}
		for(int i=29; i<32; i++){
			board[10][i].setRoad();
			board[10][i].setWestDirection();
		}
		for(int i=18; i<32; i++){
			board[i][5].setRoad();
			board[i][5].setSouthDirection();
		}
		for(int i=18; i<22; i++){
			board[i][2].setRoad();
			board[i][2].setNorthDirection();
		}
		for(int i=29; i<32; i++){
			board[i][2].setRoad();
			board[i][2].setNorthDirection();
		}
		for(int i=26; i<29; i++){
			board[i][18].setRoad();
			board[i][18].setNorthDirection();
		}
		for(int i=29; i<32; i++){
			board[i][13].setRoad();
			board[i][13].setNorthDirection();
		}
		for(int i=22; i<26; i++){
			board[i][13].setRoad();
			board[i][13].setNorthDirection();
		}
		for(int i=18; i<26; i++){
			board[i][29].setRoad();
			board[i][29].setSouthDirection();
		}
		for(int i=29; i<32; i++){
			board[i][29].setRoad();
			board[i][29].setSouthDirection();
		}
		for(int i=29; i<32; i++){
			board[i][26].setRoad();
			board[i][26].setNorthDirection();
		}
		for(int i=18; i<21; i++){
			board[i][26].setRoad();
			board[i][26].setNorthDirection();
		}
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
		    	removeCars();
				for(Car a : UAVs) a.go();
				displayCars();
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
		displayCars();
	}

	public void reset() {
		initialize();
		displayBoard();
		displayCars();	
	}

	public void step() {
		removeCars();
		for(Car a : UAVs) a.go();
		displayCars();
	}

	public void stop() {
		runThread.interrupt();
		runThread.terminate();
		displayCars();
	}

	public void displayBoard(){
		GUI.displayBoard(this);
	}

	public void displayCars(){
		GUI.displayCars(this);
	}
	
	public void removeCars(){
		GUI.removeCars(this);
	}
}
