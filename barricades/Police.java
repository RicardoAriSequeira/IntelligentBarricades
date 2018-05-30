package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Police extends Car {

	private PoliceStation station;

	public Police(Map map, PoliceStation station, Point position){
		super(map, position);
		this.station = station;
	}

	private boolean thiefInCell(Point p) {

		Cell cell = map.getCell(p);

		if (cell != null)

			if (cell.hasCar())

				if (cell.getCar() instanceof Thief) return true;

		return false;
	}

	private Point thiefAround() {

		Point[] visionPoints = super.getVisionPoints();

		for (Point point : visionPoints) {
			if (thiefInCell(point)) {
				station.reportThiefPosition(point);
				System.out.println("Thief found in cell " + point.x + "," + point.y);
				return point;
			}
		}

		return null;
	}

	private int pursuitThief(Point thiefPosition) {

		Random generator = new Random(12345);
		int r = generator.nextInt(2);

		if (thiefPosition.x < this.position.x) {

			if (thiefPosition.y > this.position.y) {

				if (!possibleDirection(WEST) && possibleDirection(SOUTH)) return SOUTH;

				if (!possibleDirection(SOUTH) && possibleDirection(WEST)) return WEST;

				if (r == 0 && possibleDirection(SOUTH)) return SOUTH;
				else if (r == 1 && possibleDirection(WEST)) return WEST;
				return STILL;

			} else if (thiefPosition.y < this.position.y) {

				if (!possibleDirection(WEST) && possibleDirection(NORTH)) return NORTH;

				if (!possibleDirection(NORTH) && possibleDirection(WEST)) return WEST;

				if (r == 0 && possibleDirection(NORTH)) return NORTH;
				else if (r == 1 && possibleDirection(WEST)) return WEST;
				return STILL;

			}  else if (possibleDirection(WEST)) return WEST;
			else return STILL;


		} else if (thiefPosition.x > this.position.x) {

			if (thiefPosition.y > this.position.y) {

				if (!possibleDirection(EAST) && possibleDirection(SOUTH)) return SOUTH;

				if (!possibleDirection(SOUTH) && possibleDirection(EAST)) return EAST;

				if (r == 0 && possibleDirection(SOUTH)) return SOUTH;
				else if (r == 1 && possibleDirection(EAST)) return EAST;
				return STILL;

			} else if (thiefPosition.y < this.position.y) {

				if (!possibleDirection(EAST) && possibleDirection(NORTH)) return NORTH;

				if (!possibleDirection(NORTH) && possibleDirection(EAST)) return EAST;

				if (r == 0 && possibleDirection(NORTH)) return NORTH;
				else if (r == 1 && possibleDirection(EAST)) return EAST;
				return STILL;
				
			} else if (possibleDirection(EAST)) return EAST;
			else return STILL;

		} else {

			if (thiefPosition.y > this.position.y && possibleDirection(SOUTH)) return SOUTH;
			else if (thiefPosition.y < this.position.y && possibleDirection(NORTH)) return NORTH;
			return STILL;

		}
	}

	public int directionDecision() {

		Point thiefPosition = thiefAround();

		if (station.isThiefPositionKnown()) {

			System.out.println("Helping in the pursuit!");

			return pursuitThief(station.getThiefPosition());
		}

		if (thiefPosition != null) {

			return pursuitThief(thiefPosition);

		} else {
			Random generator = new Random(12345);
			int r = generator.nextInt(possibleDirections.size());
			return possibleDirections.get(r);
		}
	}

}