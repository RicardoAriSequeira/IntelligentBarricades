package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Civil extends Car {

	public Civil(Point position){
		super(position);
	}

	public int directionDecision(Map map) {
		Random generator = new Random(12345);
		int r = generator.nextInt(possibleDirections.size());
		return possibleDirections.get(r);
	}

	public void changePosition(Map map, int direction) {

		Point nextPosition = new Point(position);

		if (direction == NORTH) nextPosition.y--;
		else if (direction == SOUTH) nextPosition.y++;
		else if (direction == EAST) nextPosition.x++;
		else if (direction == WEST) nextPosition.x--;

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

}