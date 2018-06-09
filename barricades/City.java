package barricades;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.io.*;

public class City {

	public static final int STILL = 0;
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;
	public static final int WEST = 4;

	public Map map;
	public Thief thief;
	public List<Civil> civils;
	public List<Police> polices;
	public PoliceStation station;
	public GraphicalInterface GUI;

	public int nX, nY, nPolices, nCivils;

	public Point[] randomCivilPoints = {new Point(22,6),new Point(23,9),new Point(16,8),new Point(31,10),
													new Point(29,4),new Point(1,29),new Point(9,27),new Point(11,21),
													new Point(17,25),new Point(14,29),new Point(23,23),new Point(29,25),
													new Point(2,9),new Point(8,5),new Point(9,11),new Point(5,16),
													new Point(20,16),new Point(8,17),new Point(17,17)};
	
	public City(int nX, int nY) {

		this.nX = nX;
		this.nY = nY;
		this.nPolices = 4;
		this.nCivils = 0;

		this.map = new Map(this.nX, this.nY);
		this.civils = new ArrayList<Civil>();
		this.polices = new ArrayList<Police>();

		insertThief(map.getRandomRoadCell());

		this.station = new PoliceStation(map, thief);

		for (int i = 0; i < nPolices; i++)
			insertPolice(map.getRandomRoadCell());

		for (int i = 0; i < nCivils; i++)
			insertCivil(map.getRandomRoadCell());

	}

	private void initialize(boolean train) {

		this.map = new Map(this.nX, this.nY);
		this.civils = new ArrayList<Civil>();
		this.polices = new ArrayList<Police>();

		if (train) {

			insertThief(map.getRandomRoadCell());

			for (int i = 0; i < nPolices; i++)
				insertPolice(map.getRandomRoadCell());

			for (int i = 0; i < nCivils; i++)
				insertCivil(map.getRandomRoadCell());

		} else {

			insertThief(new Point(10,16));

			for (int i = 0; i < nPolices; i++)
				insertPolice(new Point(28-i,16));

			for (int i = 0; i < nCivils; i++)
				insertCivil(randomCivilPoints[i]);

		}

		station.resetStation(map, thief);

	}
	
	RunThread runThread;

	public class RunThread extends Thread {
		
		int time;
		boolean display;
		private volatile boolean running = true;
		
		public RunThread(int time, boolean display){
			this.time = time;
			this.display = display;
		}

		public void terminate(){
			running = false;
		}
		
	    public void run() {

	    	//try {

		    	//PrintWriter writer = new PrintWriter("results.txt", "UTF-8");

		    	long startTime;
		    	//int nFailed = 0;

				//for (int i = 0; i < 1000 && running; i++) {

					if (display) removeCars();

			    	initialize(false);

					if (display) displayCars();

					startTime = System.nanoTime();

			    	while(running && !station.isThiefArrested()){

			    		updateClock();
			    		station.update(thief);
				    	if (display) removeCars();
				    	for(Civil c : civils) c.go();
				    	thief.go();
						for(Police p: polices) p.go();
						substituteCars();

						if (display) {
							displayCars();
							try {
								sleep(time*10);
							} catch (InterruptedException e) {
								this.interrupt();
							}
						}

						// if (((System.nanoTime() - startTime) / 1000) > 100000) {
						// 	i--;
						// 	nFailed++;
						// 	break;
						// }
			    	}

			  //   	if (((System.nanoTime() - startTime) / 1000) > 100000) {
			  //   		System.out.println("Failed");
					// } else {
					// 	writer.println(((System.nanoTime() - startTime) / 1000));
			    		System.out.println("Run Time: " + ((System.nanoTime() - startTime) / 1000) + " microseconds");
					//}

			    //}

			    // writer.println("Failed " + nFailed + " times");
			    // writer.close();
		    	GUI.setRunText("Run");
				terminate();

			// } catch (FileNotFoundException e) {
			// 	System.err.println("Caught FileNotFoundException: " +  e.getMessage());
			// } catch (UnsupportedEncodingException e) {
			// 	System.err.println("Caught UnsupportedEncodingException: " +  e.getMessage());
			// }
	    }
	}

	TrainThread trainThread;

	public class TrainThread extends Thread {
		
		int iterations;
		boolean display;
		private volatile boolean running = true;
		
		public TrainThread(int iterations, boolean display){
			this.iterations = iterations;
			this.display = display;
		}

		public void terminate(){
			running = false;
		}
		
	    public void run() {

	    	boolean thiefAlreadyArrested = false;

	    	while(running){

	    		long startIteration;

	    		for (int i = 0; i < iterations && running; i++) {

	    			initialize(true);

	    			startIteration = System.nanoTime();

	    			System.out.print("Iteration number " + i + ": ");
	    			GUI.setTrainText((i + 1) + " of " + iterations);

	    			while(!station.isThiefArrested()) {

	    				removeCars();

			    		station.update(thief);
			    		for(Civil c : civils) c.go();
				    	thief.go();
						for(Police p: polices) p.train();
						substituteCars();

						if (display) {
							displayCars();
							try {
								sleep(50);
							} catch (InterruptedException e) {
								this.interrupt();
							}
						}	
						
					}

					System.out.println(((System.nanoTime() - startIteration) / 1000) + "ns");

				}

				GUI.setTrainText("Train");
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
		Random generator = new Random();
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

	public void run(int time, boolean display) {
		runThread = new RunThread(time, display);
		runThread.start();
	}

	public void train(int iterations, boolean display) {
		trainThread = new TrainThread(iterations, display);
		trainThread.start();
	}

	public void reset() {
		initialize(false);
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
