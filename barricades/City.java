package barricades;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Environment
 * @author Rui Henriques
 */
public class City {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	/** A: Environment */

	public List<Car> Cars;
	public Map map;
	public GraphicalInterface GUI;
	public int nX, nY;
	
	public City( int nX, int nY) {
		this.nX = nX;
		this.nY = nY;
		initialize();
	}

	private void initialize() {
		map = new Map( nX, nY);
		Cars = new ArrayList<Car>();
		Cars.add(new Car(new Point(10,16)));
		Cars.add(new Car(new Point(20,16)));
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
				for(Car a : Cars) a.go(map);
				deleteCars();
				displayCars();
				try {
					sleep(time*10);
				} catch (InterruptedException e) {
					this.interrupt();
				}
	    	}
	    }
	}

	public void deleteCars() {
		Iterator<Car> itr = Cars.iterator();
		while (itr.hasNext()) {
			Car car = itr.next();
			if (!car.inCity(map))
				itr.remove();
		}
	}

	public void insertCar() {

		List<Cell> entryCells = map.getEntryCells();

		Random generator = new Random();
		int randomEntryCell = generator.nextInt(entryCells.size());

		Cars.add(new Car(new Point(10,16)));
	}
	
	public void run(int time) {
		runThread = new RunThread(time);
		runThread.start();
		displayCars();
	}

	public void reset() {
		initialize();
		displayCity();
		displayCars();	
	}

	public void step() {
		removeCars();
		for(Car a : Cars) a.go(map);
		displayCars();
	}

	public void stop() {
		runThread.interrupt();
		runThread.terminate();
		displayCars();
	}

	public void displayCity(){
		GUI.displayCity(this);
	}

	public void displayCars(){
		GUI.displayCars(this);
	}
	
	public void removeCars(){
		GUI.removeCars(this);
	}
}
