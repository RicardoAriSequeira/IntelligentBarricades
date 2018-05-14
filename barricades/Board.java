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
	public List<Car> Cars;
	public Cell[][] board;
	public GraphicalInterface GUI;
	
	public Board(int nX, int nY, int nUAVs) {
		this.nX = nX;
		this.nY = nY;
		this.nUAVs = nUAVs;
		initialize();
	}

	private void initialize() {

		Cars = new ArrayList<Car>();

		Cars.add(new Car(new Point(16,16)));

		board = new Cell[nX][nY];

		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				board[i][j] = new Cell();

		for(int i=0; i<18; i++){
			board[i][3].setRoad();
			board[i][3].setNorthDirection();

			board[i][13].setRoad();
			board[i][13].setSouthDirection();

			board[i][28].setRoad();
			board[i][28].setSouthDirection();
		}
		for(int i=0; i<32; i++){
			board[i][8].setRoad();
			board[i][8].setSouthDirection();

			board[i][9].setRoad();
			board[i][9].setNorthDirection();

			board[i][22].setRoad();
			board[i][22].setSouthDirection();

			board[i][23].setRoad();
			board[i][23].setNorthDirection();

			board[3][i].setRoad();
			board[3][i].setEastDirection();

			board[16][i].setRoad();
			board[16][i].setWestDirection();

			board[17][i].setRoad();
			board[17][i].setEastDirection();

			board[21][i].setRoad();
			board[21][i].setEastDirection();

			board[25][i].setRoad();
			board[25][i].setWestDirection();

			board[29][i].setRoad();
			board[29][i].setEastDirection();			
		}
		for(int i=0; i<22; i++){
			board[i][18].setRoad();
			board[i][18].setNorthDirection();
		}
		for(int i=0; i<4; i++){
			board[10][i].setRoad();
			board[10][i].setWestDirection();
		}
		for(int i=4; i<10; i++){
			board[7][i].setRoad();
			board[7][i].setWestDirection();

			board[12][i].setRoad();
			board[12][i].setEastDirection();
		}
		for(int i=8; i<14; i++){
			board[10][i].setRoad();
			board[10][i].setWestDirection();
		}
		for(int i=13; i<19; i++){
			board[8][i].setRoad();
			board[8][i].setWestDirection();

			board[13][i].setRoad();
			board[13][i].setEastDirection();
		}
		for(int i=18; i<24; i++){
			board[10][i].setRoad();
			board[10][i].setWestDirection();
		}
		for(int i=22; i<29; i++){
			board[12][i].setRoad();
			board[12][i].setWestDirection();
		}
		for(int i=28; i<32; i++){
			board[10][i].setRoad();
			board[10][i].setWestDirection();
		}
		for(int i=16; i<32; i++){
			board[i][5].setRoad();
			board[i][5].setSouthDirection();
		}
		for(int i=16; i<22; i++){
			board[i][2].setRoad();
			board[i][2].setNorthDirection();

			board[i][26].setRoad();
			board[i][26].setNorthDirection();
		}
		for(int i=29; i<32; i++){
			board[i][2].setRoad();
			board[i][2].setNorthDirection();

			board[i][13].setRoad();
			board[i][13].setNorthDirection();

			board[i][29].setRoad();
			board[i][29].setSouthDirection();

			board[i][26].setRoad();
			board[i][26].setNorthDirection();
		}
		for(int i=25; i<30; i++){
			board[i][18].setRoad();
			board[i][18].setNorthDirection();
		}
		for(int i=21; i<26; i++){
			board[i][13].setRoad();
			board[i][13].setNorthDirection();
		}
		for(int i=16; i<26; i++){
			board[i][29].setRoad();
			board[i][29].setSouthDirection();
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
				for(Car a : Cars) a.go(board);
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
		for(Car a : Cars) a.go(board);
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
