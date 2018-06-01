package barricades;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class City {

	public static final int Q_ITERATIONS = 10000;
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	public int nX, nY;
	public GraphicalInterface GUI;
	public List<Civil> civils;
	public List<Police> polices;
	public Thief thief;
	public Map map;
	public PoliceStation station;
	
	public City(int nX, int nY) {
		this.nX = nX;
		this.nY = nY;
		initialize();
	}

	private void initialize() {

		this.map = new Map(this.nX, this.nY);
		this.civils = new ArrayList<Civil>();
		this.polices = new ArrayList<Police>();
		this.station = new PoliceStation(map);

		insertThief(new Point(20,16));
		insertPolice(new Point(8,16));
		insertPolice(new Point(21,16));

		/*
		insertCivil(new Point(22,6)));
		insertCivil(new Point(23,9)));
		insertCivil(new Point(16,8)));
		insertCivil(new Point(31,10)));
		insertCivil(new Point(28,4)));
		insertCivil(new Point(1,29)));
		insertCivil(new Point(9,27)));
		insertCivil(new Point(11,21)));
		insertCivil(new Point(17,25)));
		insertCivil(new Point(14,29)));
		insertCivil(new Point(23,23)));
		insertCivil(new Point(29,25)));
		insertCivil(new Point(3,9)));
		insertCivil(new Point(8,5)));
		insertCivil(new Point(9,11)));
		insertCivil(new Point(5,16)));
		insertCivil(new Point(20,16)));
		insertCivil(new Point(8,17)));
		insertCivil(new Point(17,17)));
		*/
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

	    	System.out.println("RUN");

	    	map.initialize();
	    	polices.get(0).restartPolice(new Point(8,16));
			polices.get(1).restartPolice(new Point(21,16));
			insertThief(new Point(20,16));
			station.resetStation();

			displayCars();

	    	while(running && !station.isThiefArrested()){

	    		updateClock();
	    		station.update();
		    	removeCars();
		    	thief.go();
				//for(Civil c : civils) c.go();
				for(Police p: polices) p.go();
				substituteCars();
				displayCars();
				try {
					sleep(time*10);
				} catch (InterruptedException e) {
					this.interrupt();
				}

	    	}
	    }
	}

	TrainThread trainThread;

	public class TrainThread extends Thread {
		
		private volatile boolean running = true;
		
		public TrainThread(){
		}

		public void terminate(){
			running = false;
		}
		
	    public void run() {

	    	boolean thiefAlreadyArrested = false;

	    	while(running){

	    		long startIteration;

	    		for (int i = 0; i < Q_ITERATIONS && running; i++) {

	    			startIteration = System.nanoTime();

	    			System.out.print("Iteration number " + i + ": ");

	    			map.initialize();
	    			polices.get(0).restartPolice(new Point(8,16));
					polices.get(1).restartPolice(new Point(21,16));
	    			insertThief(new Point(20,16));
	    			station.resetStation();

	    			while(!station.isThiefArrested()) {

	    				removeCars();

			    		station.update();
				    	thief.go();
						//for(Civil c : civils) c.go();
						for(Police p: polices)  {
							p.train();
						}
						substituteCars();

						if (false) {
							displayCars();
							
							try {
								sleep(10);
							} catch (InterruptedException e) {
								this.interrupt();
							}
						}	
						
						
					}

					System.out.println(((System.nanoTime() - startIteration) / 1000) + "ns");

				}

				terminate();

	    	}
	    }
	}

	public void substituteCars() {

		int deletedCivils = 0;
		int deletedPolices = 0;
		boolean deletedThief = false;

		Iterator<Civil> itr = civils.iterator();
		Iterator<Police> itrP = polices.iterator();

		while (itr.hasNext()) {
			Civil civil = itr.next();
			if (!map.inMap(civil.position)) {
				itr.remove();
				deletedCivils++;
			}
		}

		while (itrP.hasNext()) {
			Police police = itrP.next();
			if (!map.inMap(police.position)) {
				itrP.remove();
				deletedPolices++;
			}
		}

		List<Cell> entryCells = map.getEntryCells();
		Random generator = new Random(482398427);
		Cell randomEntryCell;

		for (int p = 0; p < deletedPolices; p++) {
			randomEntryCell = entryCells.get(generator.nextInt(entryCells.size()));
			insertPolice(new Point(randomEntryCell.getCoordinates()));
		}

		for (int c = 0; c < deletedCivils; c++) {
			randomEntryCell = entryCells.get(generator.nextInt(entryCells.size()));
			insertCivil(new Point(randomEntryCell.getCoordinates()));
		}

		if (!map.inMap(this.thief.position)){
			randomEntryCell = entryCells.get(generator.nextInt(entryCells.size()));
			insertThief(new Point(randomEntryCell.getCoordinates()));
		}
	}

	public void insertCivil(Point p) {
		Civil civil = new Civil(map,p);
		this.civils.add(civil);
	}

	public void insertThief(Point p) {
		this.thief = new Thief(map,p);
	}

	public void insertPolice(Point p) {
		Police police = new Police(map,station,p);
		this.polices.add(police);
		this.station.updatePolices(polices);
	}

	public void run(int time) {
		runThread = new RunThread(time);
		runThread.start();
		//displayCars();
	}

	public void train() {
		trainThread = new TrainThread();
		trainThread.start();
		//displayCars();
	}

	public void reset() {
		initialize();
		displayCity();
		displayCars();	
	}

	public void step() {
		removeCars();
		this.thief.go();
		for(Civil c : civils) c.go();
		for(Police p : polices) p.go();
		displayCars();
	}

	public void stopRun() {
		runThread.interrupt();
		runThread.terminate();
		displayCars();
	}

	public void stopTrain() {
		trainThread.terminate();
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

	public void updateClock(){
		GUI.updateClock();
	}
}
