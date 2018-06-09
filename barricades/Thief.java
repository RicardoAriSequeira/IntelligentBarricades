package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Thief extends Car {

	private boolean surrender;

	public Thief(Map map, Point position){
		super(map,position);
		surrender = false;
	}

	public boolean surrender() {return surrender;}

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

		Random generator = new Random();
		List<Integer> legalDirections = map.getCell(position).getLegalDirections();

		triedDirections = new boolean[legalDirections.size()];
		int r = generator.nextInt(legalDirections.size());
		int direction = legalDirections.get(r);

		while (!possibleDirection(direction)) {
			triedDirections[r] = true;
			if (triedAllDirections()) break;
			r = generator.nextInt(legalDirections.size());
			direction = legalDirections.get(r);
		}

		if (!possibleDirection(direction)) {

			triedDirections = new boolean[4];
			direction = generator.nextInt(4) + 1;

			while (!possibleDirection(direction)) {
				triedDirections[direction-1] = true;
				if (triedAllDirections()) break;
				direction = generator.nextInt(4) + 1;
			}

			if (!possibleDirection(direction)) {
				surrender = true;
				return STILL;
			}
		}

		return direction;

	}

}