package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Thief extends Car {

	public Thief(Map map, Point position){
		super(map,position);
	}

	public int directionDecision() {

		int direction;

		triedDirections = new boolean[4];

		if (this.directionToMantain != NOT_DEFINED) {

			direction = checkWantedDirections();

			if (direction == STILL) {

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

			} else {

				this.directionToMantain = NOT_DEFINED;
				return direction;
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

	}

}