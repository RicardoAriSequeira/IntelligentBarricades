package barricades;

import java.awt.Point;

public class Cell {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	// North, South, East, West
	private boolean[] directions;
	private Point coordinates;
	private boolean isRoad, hasCar;

	public Cell(int x, int y){
		coordinates = new Point(y,x);
		isRoad = false;
		hasCar = false;
		directions = new boolean[4];
	}

	public Point getCoordinates() {return coordinates;}

	public boolean isRoad() {return isRoad;}

	public boolean[] getDirections() {return directions;}

	public boolean getHasCar() {return hasCar;}

	public void setHasCar(boolean value) {hasCar = value;}

	public void setDirection(int direction) {
		isRoad = true;
		directions[direction] = true;
	}

}