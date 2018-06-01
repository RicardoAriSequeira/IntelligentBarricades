package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public abstract class Car {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	public static final int STILL = 4;

	public static final int VISION_LIMIT = 4;

	public Point position;
	public Map map;
	public int[] wantedDirections;
	public int directionToMantain;
	public boolean wrongWay;
	boolean[] triedDirections;
	
	public Car(Map map, Point position){
		this.map = map;
		this.position = position;
		this.map.getCell(position).setCar(this);
		this.wrongWay = false;
		boolean[] triedDirections = new boolean[4];
	}

	protected Point[] getVisionPoints() {

		Point[] visionPoints = new Point[4*VISION_LIMIT+4];

		int i = 0;

		for (int vision = 1; vision <= VISION_LIMIT; vision++) {
			visionPoints[i++] = new Point(position.x+vision, position.y);
			visionPoints[i++] = new Point(position.x-vision, position.y);
			visionPoints[i++] = new Point(position.x, position.y+vision);
			visionPoints[i++] = new Point(position.x, position.y-vision);
		}

		visionPoints[i++] = new Point(position.x+1, position.y+1);
		visionPoints[i++] = new Point(position.x+1, position.y-1);
		visionPoints[i++] = new Point(position.x-1, position.y+1);
		visionPoints[i++] = new Point(position.x-1, position.y-1);

		return visionPoints;
	}

	protected boolean possibleDirection(int direction) {

		//System.out.println(direction);

		Point nextPosition = new Point(position);

		if (direction == NORTH) nextPosition.y--;
		else if (direction == SOUTH) nextPosition.y++;
		else if (direction == EAST) nextPosition.x++;
		else if (direction == WEST) nextPosition.x--;
		else return true;

		if (direction == NORTH) {
			if (map.getCell(new Point(position.x,position.y-1)) == null ||
				map.getCell(new Point(position.x,position.y-2)) == null ||
				map.getCell(new Point(position.x,position.y-3)) == null) {
				return false;
			}
		}

		if (direction == SOUTH) {
			if (map.getCell(new Point(position.x,position.y+1)) == null ||
				map.getCell(new Point(position.x,position.y+2)) == null ||
				map.getCell(new Point(position.x,position.y+3)) == null) {
				return false;
			}
		}

		if (direction == EAST) {
			if (map.getCell(new Point(position.x+1,position.y)) == null ||
				map.getCell(new Point(position.x+2,position.y)) == null ||
				map.getCell(new Point(position.x+3,position.y)) == null) {
				return false;
			}
		}

		if (direction == WEST) {
			if (map.getCell(new Point(position.x-1,position.y)) == null ||
				map.getCell(new Point(position.x-2,position.y)) == null ||
				map.getCell(new Point(position.x-3,position.y)) == null) {
				return false;
			}
		}

		if (map.inMap(nextPosition))
			if (map.getCell(nextPosition).isRoad() && map.getCell(nextPosition).hasCar() == false)
				return true;

		return false;
	}

	protected boolean triedAllDirections() {

		for (int i = 0; i < triedDirections.length; i++) {
			if (triedDirections[i] == false) return false;
		}

		return true;

	}

	public int goToPoint(Point point) {

		Random generator = new Random(482398427);
		int r = generator.nextInt(2);

		if (point.x < this.position.x) {

			if (point.y > this.position.y) {

				if (!possibleDirection(WEST) && possibleDirection(SOUTH)) {
					return SOUTH;
				}

				if (!possibleDirection(SOUTH) && possibleDirection(WEST)) {
					return WEST;
				}

				if (r == 0 && possibleDirection(SOUTH)) {
					return SOUTH;
				}

				else if (possibleDirection(WEST)) {
					return WEST;
				}

				return STILL;

			} else if (point.y < this.position.y) {

				if (!possibleDirection(WEST) && possibleDirection(NORTH)) {
					return NORTH;
				}

				if (!possibleDirection(NORTH) && possibleDirection(WEST)) {
					return WEST;
				}

				if (r == 0 && possibleDirection(NORTH)) {
					return NORTH;
				}

				else if (possibleDirection(WEST)) {
					return WEST;
				}

				return STILL;

			}  else if (possibleDirection(WEST)) {
				return WEST;
			}

			else return STILL;


		} else if (point.x > this.position.x) {

			if (point.y > this.position.y) {

				if (!possibleDirection(EAST) && possibleDirection(SOUTH)) {
					return SOUTH;
				}

				if (!possibleDirection(SOUTH) && possibleDirection(EAST)) {
					return EAST;
				}

				if (r == 0 && possibleDirection(SOUTH)) {
					return SOUTH;
				}

				else if (possibleDirection(EAST)) {
					return EAST;
				}

				return STILL;

			} else if (point.y < this.position.y) {

				if (!possibleDirection(EAST) && possibleDirection(NORTH)) {
					return NORTH;
				}

				if (!possibleDirection(NORTH) && possibleDirection(EAST)) {
					return EAST;
				}

				if (r == 0 && possibleDirection(NORTH)) {
					return NORTH;
				}

				else if (possibleDirection(EAST)) {
					return EAST;
				}

				return STILL;
				
			} else if (possibleDirection(EAST)) {
				return EAST;
			}

			else return STILL;

		} else {

			if (point.y > this.position.y && possibleDirection(SOUTH)) {
				return SOUTH;
			}

			else if (point.y < this.position.y && possibleDirection(NORTH)) {
				return NORTH;
			}

			return STILL;

		}

	}

	public int checkWantedDirections() {

		Random generator = new Random(482398427);
		int a = generator.nextInt(2);

		boolean[] directions = map.getCell(position).getDirections();

		if (directions[wantedDirections[a]] == true) {
			if (possibleDirection(wantedDirections[a]))
				return wantedDirections[a];
			else
				triedDirections[wantedDirections[a]] = true;
		}

		int b = a == 0 ? 1 : 0;

		if (directions[wantedDirections[b]] == true) {
			if (possibleDirection(wantedDirections[b]))
				return wantedDirections[b];
			else {
				triedDirections[wantedDirections[b]] = true;
				if (possibleDirection(wantedDirections[a]))
					return wantedDirections[a];
				else triedDirections[wantedDirections[a]] = true;
			}
		} else {
			if (possibleDirection(wantedDirections[a]))
				return wantedDirections[a];
			else {
				triedDirections[wantedDirections[a]] = true;
				if (possibleDirection(wantedDirections[b]))
					return wantedDirections[b];
				else triedDirections[wantedDirections[b]] = true;
			}
		}

		return STILL;
	}

	public abstract int directionDecision();

	public void changePosition(int direction) {

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
	
	public void go(){

		if (map.inMap(this.position)) {

			List<Integer> legalDirections = map.getCell(position).getLegalDirections();

			if (legalDirections.size() == 0) {
				System.out.println("Error: there is no legal directions in cell (" + position.x + "," + position.y + ")");
				return;
			}

			int direction = directionDecision();
			changePosition(direction);

		}
	}

}