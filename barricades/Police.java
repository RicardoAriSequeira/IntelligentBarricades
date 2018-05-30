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

	private Point thiefAround() {

		Cell cell;
		Point[] visionPoints = getVisionPoints();

		for (Point point : visionPoints) {

			cell = map.getCell(point);

			if (cell != null) {

				if (cell.hasCar()) {

					if (cell.getCar() instanceof Thief) {

						station.reportThiefPosition(point);
						System.out.println("Thief found in cell " + point.x + "," + point.y);
						return point;
					}
				}
			}
		}

		return null;
	}

	public int directionDecision() {

		Point thiefPosition = thiefAround();

		if (station.isThiefPositionKnown()) {

			System.out.println("Helping in the pursuit!");

			return goToPoint(station.getThiefPosition());
		}

		if (thiefPosition != null) {

			return goToPoint(thiefPosition);

		} else {
			Random generator = new Random(12345);
			List<Integer> possibleDirections = map.getCell(position).getPossibleDirections();
			int r = generator.nextInt(possibleDirections.size());
			return possibleDirections.get(r);
		}
	}

}