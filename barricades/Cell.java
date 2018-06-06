package barricades;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Cell {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	public static final int STILL = 4;

	public boolean isRoad, isBarricade, isGarage;
	public boolean[] directions;

	public int indexRoad;

	public Car car;
	public Point coordinates;
	public List<Integer> legalDirections;

	public Cell(int x, int y){
		coordinates = new Point(x,y);
		isRoad = false;
		car = null;
		directions = new boolean[4];
		legalDirections = new ArrayList<Integer>();
		indexRoad = 0;
	}

	public Point getCoordinates() {return coordinates;}

	public boolean isBarricade() {return isBarricade;}

	public boolean isGarage() {return isGarage;}

	public boolean hasCar() {return !(this.car == null);}

	public boolean[] getDirections() {return directions;}

	public List<Integer> getLegalDirections() {return legalDirections;}

	public Car getCar() {return car;}

	public void setIsBarricade() {this.isBarricade = true;}

	public void setIsGarage() {this.isGarage = true;}

	public void setNoCar() {this.car = null;}

	public void setCar(Car car) {this.car = car;}

	public void setDirection(int direction) {
		if (directions[direction] == false) {
			isRoad = true;
			directions[direction] = true;
			legalDirections.add(direction);
		}
	}

	public boolean hasThief() {
		if (hasCar()) {
			if (getCar() instanceof Thief) {
				return true;
			} else {
				return false;
			}
		} else return false;
	}

	public List<Integer> getPossibleDirections(Map map) {

		List<Integer> result = new ArrayList<Integer>();

		result.add(STILL);

		Point nextPosition = new Point(coordinates);
		nextPosition.y--;
		if (map.inMap(nextPosition))
			if (map.getCell(nextPosition).isRoad && !map.getCell(nextPosition).hasCar())
				result.add(NORTH);

		nextPosition = new Point(coordinates);
		nextPosition.y++;
		if (map.inMap(nextPosition))
			if (map.getCell(nextPosition).isRoad && !map.getCell(nextPosition).hasCar())
				result.add(SOUTH);

		nextPosition = new Point(coordinates);
		nextPosition.x--;
		if (map.inMap(nextPosition))
			if (map.getCell(nextPosition).isRoad && !map.getCell(nextPosition).hasCar())
				result.add(WEST);

		nextPosition = new Point(coordinates);
		nextPosition.x++;
		if (map.inMap(nextPosition))
			if (map.getCell(nextPosition).isRoad && !map.getCell(nextPosition).hasCar())
				result.add(EAST);

		return result;

	}

	public Point getNextPoint(int direction) {

		Point nextPosition = new Point(coordinates);
		if (direction == NORTH) nextPosition.y--;
		else if (direction == SOUTH) nextPosition.y++;
		else if (direction == EAST) nextPosition.x++;
		else if (direction == WEST) nextPosition.x--;
		return nextPosition;

	}

}