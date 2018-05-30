package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Thief extends Car {

	/*
	public int[] wantedDirections;
	public int directionToMantain;
	public boolean wrongWay;
	*/

	public Thief(Map map, Point position){
		super(map,position);
		//this.wrongWay = false;
	}

	/*

	public int checkWantedDirections() {

		Random generator = new Random();
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

	*/

	private Point garageAround() {

		Cell cell;
		Point[] visionPoints = getVisionPoints();

		for (Point point : visionPoints) {

			cell = map.getCell(point);

			if (cell != null) {

				if (cell.isGarage()) {

					System.out.println("Garage found in cell " + point.x + "," + point.y);
					return point;
				}
			}

		}

		return null;
	}

	private boolean policeAround() {

		Cell cell;
		Point[] visionPoints = getVisionPoints();

		for (Point point : visionPoints) {

			cell = map.getCell(point);

			if (cell != null)

				if (cell.hasCar())

					if (cell.getCar() instanceof Police)

						return true;
		}

		return false;
	}

	private boolean thiefPossibleDirection(int direction) {

		Cell cell;

		if (!possibleDirection(direction)) return false;

		if (direction == NORTH) {

			for (int i = 1; i <= VISION_LIMIT; i++) {

				cell = map.getCell(new Point(position.x, position.y-i));

				if (cell == null)
					return false;
				else
					if (cell.hasCar())
						if (cell.getCar() instanceof Police)
							return false;

			}

		} else if (direction == SOUTH) {

			for (int i = 1; i <= VISION_LIMIT; i++) {

				cell = map.getCell(new Point(position.x, position.y+i));

				if (cell == null)
					return false;
				else
					if (cell.hasCar())
						if (cell.getCar() instanceof Police)
							return false;

			}

		} else if (direction == WEST) {

			for (int i = 1; i <= VISION_LIMIT; i++) {

				cell = map.getCell(new Point(position.x-i, position.y));

				if (cell == null)
					return false;
				else
					if (cell.hasCar())
						if (cell.getCar() instanceof Police)
							return false;

			}

		} else if (direction == EAST) {

			for (int i = 1; i <= VISION_LIMIT; i++) {

				cell = map.getCell(new Point(position.x+i, position.y));

				if (cell == null)
					return false;
				else
					if (cell.hasCar())
						if (cell.getCar() instanceof Police)
							return false;

			}

		} else return false;

		return true;


	}

	public int directionDecision() {

		int direction = STILL;
		boolean[] triedDirections = new boolean[4];

		if (!policeAround()) {

			Point garage = garageAround();

			if (garage != null) return goToPoint(garage);

		}

		Random generator = new Random();
		List<Integer> possibleDirections = map.getCell(position).getPossibleDirections();
		int r = generator.nextInt(possibleDirections.size());

		triedDirections[possibleDirections.get(r)] = true;
		direction = possibleDirections.get(r);

		if (thiefPossibleDirection(direction)) return direction;

		for (int i = 0; i < possibleDirections.size(); i++) {

			if (!triedDirections[possibleDirections.get(i)] &&
				thiefPossibleDirection(possibleDirections.get(i)))

				return possibleDirections.get(i);

			else

				triedDirections[possibleDirections.get(i)] = true;

		}

		for (int i = 0; i < triedDirections.length; i++)

			if (!triedDirections[i] && (thiefPossibleDirection(i)))

				return i;


		return STILL;


		/*

		int direction;

		triedDirections = new boolean[4];

		if (wrongWay) {

			direction = checkWantedDirections();

			if (direction != STILL) {

				wrongWay = false;
				return direction;

			} else {

				if (possibleDirection(this.directionToMantain))
					return this.directionToMantain;
				else
					triedDirections[this.directionToMantain] = true;

				for (int d = 0; d < triedDirections.length; d++) {

					if (triedDirections[d] == false) {

						if (possibleDirection(d)) {
							this.directionToMantain = d;
							return d;

						}
					}
				}
			}

		}

		Random generator = new Random();
		int r = generator.nextInt(possibleDirections.size());
		triedDirections[possibleDirections.get(r)] = true;
		direction = possibleDirections.get(r);

		if (!possibleDirection(direction)) {

			if (direction == NORTH || direction == SOUTH)
				wantedDirections = new int[]{WEST, EAST};
			else
				wantedDirections = new int[]{NORTH, SOUTH};

			direction = checkWantedDirections();

			if (direction == STILL) {

				for (int d = 0; d < triedDirections.length; d++) {

					if (triedDirections[d] == false) {

						if (possibleDirection(d)) {
							this.directionToMantain = d;
							return d;

						}
					}
				}

			} else return direction;

			return STILL;

		}

		return direction;

		*/

	}

}