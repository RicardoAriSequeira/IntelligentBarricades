package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Thief extends Car {

	public Thief(Point position){
		super(position);
	}

	public int directionDecision(Map map) {
		Random generator = new Random(12345);
		triedDirections = new boolean[possibleDirections.size()];
		int r = generator.nextInt(possibleDirections.size());
		triedDirections[r] = true;
		return possibleDirections.get(r);
	}

	public void changePosition(Map map, int direction) {

		Point nextPosition = new Point(position);

		if (direction == NORTH) nextPosition.y--;
		else if (direction == SOUTH) nextPosition.y++;
		else if (direction == EAST) nextPosition.x++;
		else if (direction == WEST) nextPosition.x--;

		while (!possiblePosition(map,nextPosition)){

			direction = getOtherPossibleDirection();

			nextPosition = new Point(position);

			if (direction == -1) break;
			else if (direction == NORTH) nextPosition.y--;
			else if (direction == SOUTH) nextPosition.y++;
			else if (direction == EAST) nextPosition.x++;
			else if (direction == WEST) nextPosition.x--;

		}


		if (direction != -1) {
			map.getCell(position).setNoCar();
			this.position = nextPosition;
			map.getCell(position).setCar(this);

		}

	}

}