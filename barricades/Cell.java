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
	private boolean[] directions;
	private List<Integer> possibleDirections;
	private Point coordinates;
	private boolean isRoad;
	private Car car;

	public Cell(int x, int y){
		coordinates = new Point(y,x);
		isRoad = false;
		car = null;
		directions = new boolean[4];
		possibleDirections = new ArrayList<Integer>();
	}

	public Point getCoordinates() {return coordinates;}

	public boolean isRoad() {return isRoad;}

	public boolean hasCar() {return !(this.car == null);}

	public boolean[] getDirections() {return directions;}

	public List<Integer> getPossibleDirections() {return possibleDirections;}

	public Car getCar() {return car;}

	public void setCar(Car car) {this.car = car;}

	public void setNoCar() {this.car = null;}

	public void setDirection(int direction) {

		if (directions[direction] == false) {

			isRoad = true;
			directions[direction] = true;
			possibleDirections.add(direction);

		}

	}

}