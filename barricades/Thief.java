package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Thief extends Car {

	public Thief(Point position){
		super(position);
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


	public int directionDecision(Map map) {

		int direction;

		triedDirections = new boolean[4];

		if (this.directionToMantain != NOT_DEFINED) {

			direction = checkWantedDirections(map);

			if (direction == STILL) {

				if (possibleDirection(map,this.directionToMantain))
					return this.directionToMantain;
				else
					triedDirections[this.directionToMantain] = true;

				for (int d = 0; d < triedDirections.length; d++) {

					if (triedDirections[d] == false) {

						if (possibleDirection(map,d)) {
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

		if (!possibleDirection(map,direction)) {

			if (direction == NORTH || direction == SOUTH)
				wantedDirections = new int[]{WEST, EAST};
			else
				wantedDirections = new int[]{NORTH, SOUTH};

			direction = checkWantedDirections(map);

			if (direction == STILL) {

				for (int d = 0; d < triedDirections.length; d++) {

					if (triedDirections[d] == false) {

						if (possibleDirection(map,d)) {
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