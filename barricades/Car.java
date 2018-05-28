package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Car {

	public static final int STILL = -1;
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	public Point position;
	
	public Car(Point position){
		this.position = position;
	}

	public boolean inCity(Map map) {
		return map.inMap(this.position);
	}
	
	public void go(Map map){

		if (inCity(map)) {

			List<Integer> possibleDirections = map.getCell(position.x,position.y).getPossibleDirections();

			if (!map.getCell(position.x, position.y).isRoad()) {
				System.out.println("Error: no road in cell (" + position.y + "," + position.x + ")");
				return;
			}

			if (possibleDirections.size() == 0) {
				System.out.println("Error: there is no directions in cell (" + position.y + "," + position.x + ")");
				return;
			}

			Random generator = new Random();
			int r = generator.nextInt(possibleDirections.size());
			int direction = possibleDirections.get(r);

			Point nextPosition = new Point(position);

			if (direction == NORTH) nextPosition.y--;
			else if (direction == SOUTH) nextPosition.y++;
			else if (direction == EAST) nextPosition.x++;
			else if (direction == WEST) nextPosition.x--;

			if (map.inMap(nextPosition)){

				if (map.getCell(nextPosition.x, nextPosition.y).getHasCar() == false) {
					map.getCell(position.x, position.y).setHasCar(false);
					this.position = nextPosition;
					map.getCell(position.x, position.y).setHasCar(true);
				}

			} else {
				map.getCell(position.x, position.y).setHasCar(false);
				this.position = nextPosition;
			}
		}
	}
	
	/** A: actuators */

	/** B: perceptors */

	/** C: decision process */

}