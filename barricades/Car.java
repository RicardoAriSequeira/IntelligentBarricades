package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public abstract class Car {

	public static final int NOT_DEFINED = -2;
	public static final int STILL = -1;
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	public boolean[] triedDirections;
	public int[] wantedDirections;
	public int directionToMantain;

	public Point position;
	public List<Integer> possibleDirections;
	
	public Car(Point position){
		this.directionToMantain = NOT_DEFINED;
		this.position = position;
	}

	protected Point[] getVisionPoints() {
		Point[] visionPoints = new Point[12];
		visionPoints[0] = new Point(position.x+2, position.y);
		visionPoints[1] = new Point(position.x+1, position.y);
		visionPoints[2] = new Point(position.x-1, position.y);
		visionPoints[3] = new Point(position.x-2, position.y);
		visionPoints[4] = new Point(position.x, position.y+2);
		visionPoints[5] = new Point(position.x, position.y+1);
		visionPoints[6] = new Point(position.x, position.y-1);
		visionPoints[7] = new Point(position.x, position.y-2);
		visionPoints[8] = new Point(position.x+1, position.y+1);
		visionPoints[9] = new Point(position.x+1, position.y-1);
		visionPoints[10] = new Point(position.x-1, position.y+1);
		visionPoints[11] = new Point(position.x-1, position.y-1);
		return visionPoints;
	}

	protected boolean possibleDirection(Map map, int direction) {

		Point nextPosition = new Point(position);

		if (direction == NORTH) nextPosition.y--;
		else if (direction == SOUTH) nextPosition.y++;
		else if (direction == EAST) nextPosition.x++;
		else if (direction == WEST) nextPosition.x--;

		if (map.inMap(nextPosition))
			if (map.getCell(nextPosition).isRoad() && map.getCell(nextPosition).hasCar() == false)
				return true;
		return false;
	}

	public int checkWantedDirections(Map map) {

		Random generator = new Random();
		int a = generator.nextInt(2);

		boolean[] directions = map.getCell(position).getDirections();

		if (directions[wantedDirections[a]] == true) {
			if (possibleDirection(map,wantedDirections[a]))
				return wantedDirections[a];
			else
				triedDirections[wantedDirections[a]] = true;
		}

		int b = a == 0 ? 1 : 0;

		if (directions[wantedDirections[b]] == true) {
			if (possibleDirection(map,wantedDirections[b]))
				return wantedDirections[b];
			else {
				triedDirections[wantedDirections[b]] = true;
				if (possibleDirection(map,wantedDirections[a]))
					return wantedDirections[a];
				else triedDirections[wantedDirections[a]] = true;
			}
		} else {
			if (possibleDirection(map,wantedDirections[a]))
				return wantedDirections[a];
			else {
				triedDirections[wantedDirections[a]] = true;
				if (possibleDirection(map,wantedDirections[b]))
					return wantedDirections[b];
				else triedDirections[wantedDirections[b]] = true;
			}
		}

		return STILL;

	}

	public abstract int directionDecision(Map map);

	public void changePosition(Map map, int direction) {

		Point nextPosition = new Point(position);

		if (direction == NORTH) nextPosition.y--;
		else if (direction == SOUTH) nextPosition.y++;
		else if (direction == EAST) nextPosition.x++;
		else if (direction == WEST) nextPosition.x--;
		else return;

		if (map.inMap(nextPosition)){

			if (map.getCell(nextPosition).hasCar() == false) {
				map.getCell(position).setNoCar();
				this.position = nextPosition;
				map.getCell(position).setCar(this);
			}

		} else {
			map.getCell(position).setNoCar();
			this.position = nextPosition;
		}

	}
	
	public void go(Map map){

		if (map.inMap(this.position)) {

			possibleDirections = map.getCell(position).getPossibleDirections();

			if (possibleDirections.size() == 0) {
				System.out.println("Error: there is no directions in cell (" + position.x + "," + position.y + ")");
				return;
			}

			int direction = directionDecision(map);
			changePosition(map, direction);

		}
	}

}