package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public abstract class Car {

	public static final int STILL = -1;
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	public boolean[] triedDirections;
	public Point position;
	public List<Integer> possibleDirections;
	
	public Car(Point position){
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

	protected boolean possiblePosition(Map map, Point position) {
		if (map.inMap(position))
			if (map.getCell(position).hasCar() == false)
				return true;
		return false;
	}

	protected int getOtherPossibleDirection() {

		for (int i = 0; i < triedDirections.length; i++) {

			if (triedDirections[i]) {
				continue;

			} else {
				triedDirections[i] = true;
				return possibleDirections.get(i);
			}
		}

		return -1;
	}

	public abstract int directionDecision(Map map);

	public abstract void changePosition(Map map, int direction);
	
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
	
	/** A: actuators */

	/** B: perceptors */

	/** C: decision process */

}