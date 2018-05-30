package barricades;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Cell {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	// North, South, East, West
	private boolean isRoad, isBarricade, isGarage;
	private boolean[] directions;

	private Car car;
	private Point coordinates;
	private List<Integer> possibleDirections;

	public Cell(int x, int y){
		coordinates = new Point(x,y);
		isRoad = false;
		car = null;
		directions = new boolean[4];
		possibleDirections = new ArrayList<Integer>();
	}

	public Point getCoordinates() {return coordinates;}

	public boolean isRoad() {return isRoad;}

	public boolean isBarricade() {return isBarricade;}

	public boolean isGarage() {return isGarage;}

	public boolean hasCar() {return !(this.car == null);}

	public boolean[] getDirections() {return directions;}

	public List<Integer> getPossibleDirections() {return possibleDirections;}

	public Car getCar() {return car;}

	public void setIsBarricade() {this.isBarricade = true;}

	public void setIsGarage() {this.isGarage = true;}

	public void setNoCar() {this.car = null;}

	public void setCar(Car car) {this.car = car;}

	public void setDirection(int direction) {

		if (directions[direction] == false) {

			isRoad = true;
			directions[direction] = true;
			possibleDirections.add(direction);

		}

	}

}