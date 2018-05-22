package barricades;

import java.awt.Point;

public class Cell {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	private Point coordinates;
	private boolean isRoad, hasCar;

	// North, South, East, West
	private boolean[] directions;

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

	public void setHasCarFalse() {hasCar = false;}

	public void setHasCarTrue() {hasCar = true;}

	public void setDirection(int direction) {
		isRoad = true;
		directions[direction] = true;
	}

}