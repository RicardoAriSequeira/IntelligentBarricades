package barricades;

public class Cell {

	private boolean isRoad;

	// North, South, East, West
	private boolean[] directions;

	public Cell(){
		isRoad = false;
		directions = new boolean[4];
	} 

	public void setNorthDirection() {
		directions[0] = true;
	}

	public void setSouthDirection() {
		directions[1] = true;
	}

	public void setEastDirection() {
		directions[2] = true;
	}

	public void setWestDirection() {
		directions[3] = true;
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

}