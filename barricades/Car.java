package barricades;

import java.awt.Point;
import java.util.Random;

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

			boolean[] positionDirections = map.getCell(position).getDirections();

			if (!map.getCell(position).isRoad()) {
				System.out.println("Error: no road in cell (" + position.y + "," + position.x + ")");
				return;
			}

			if (positionDirections[0] == false &&
				positionDirections[1] == false &&
				positionDirections[2] == false &&
				positionDirections[3] == false) {
				System.out.println("Error: there is no directions in cell (" + position.y + "," + position.x + ")");
				return;
			}

			Random generator = new Random();
			int direction = generator.nextInt(4);

			while (positionDirections[direction] == false) {
				direction = generator.nextInt(4);
			}

			Point nextPosition = new Point(position);

			if (direction == NORTH) nextPosition.y--;
			else if (direction == SOUTH) nextPosition.y++;
			else if (direction == EAST) nextPosition.x++;
			else if (direction == WEST) nextPosition.x--;

			if (map.inMap(nextPosition)){

				if (map.getCell(nextPosition).getHasCar() == false) {
					map.getCell(position).setHasCar(false);
					this.position = nextPosition;
					map.getCell(position).setHasCar(true);
				}

			} else {
				map.getCell(position).setHasCar(false);
				this.position = nextPosition;
			}
		}
	}
	
	/** A: actuators */

	/** B: perceptors */

	/** C: decision process */

}