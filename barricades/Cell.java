package barricades;

public class Cell {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	private boolean isRoad;
	private boolean hasCar;

	// North, South, East, West
	private boolean[] directions;

	public Cell(){
		isRoad = false;
		hasCar = false;
		directions = new boolean[4];
	} 

	public void setDirection(int direction) {
		this.setRoad();
		directions[direction] = true;
	}

	public void setRoad() {
		isRoad = true;
	}

	public boolean isRoad() {
		return isRoad;
	}

	public boolean[] getDirections() {
		return directions;
	}

	public boolean getHasCar(){
		return hasCar;
	}

	public void setHasCarFalse(){
		hasCar = false;
	}

	public void setHasCarTrue(){
		hasCar = true;
	}

}