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

		if (this.position.x < map.nX &&
			this.position.y < map.nY &&
			this.position.x >= 0 &&
			this.position.y >= 0)
			return true;
		
		return false;
	}
	
	public void go(Map map){

		if (inCity(map)) {

			Cell prevCell = map.getCell(position.x, position.y);
			prevCell.setHasCarFalse();

			boolean[] positionDirections = map.getCell(position.x, position.y).getDirections();

			if (!map.getCell(position.x, position.y).isRoad()) {
				System.out.println("Error: no road in cell (" + position.y + "," + position.x + ")");
				return;
			}

			if (positionDirections[0] == false && positionDirections[1] == false && positionDirections[2] == false && positionDirections[3] == false) {
				System.out.println("Error: there is no directions in cell (" + position.y + "," + position.x + ")");
				return;
			}

			Random generator = new Random();
			int direction = generator.nextInt(4);

			while (positionDirections[direction] == false) {
				direction = generator.nextInt(4);
			}

			Point nextPosition = new Point(position.x, position.y);

			if (direction == NORTH)
				nextPosition.y--;

			else if (direction == SOUTH)
				nextPosition.y++;

			else if (direction == EAST)
				nextPosition.x++;

			else if (direction == WEST)
				nextPosition.x--;

			if ((nextPosition.y) >= 0 && (nextPosition.y) < map.nY && (nextPosition.x) < map.nX && (nextPosition.x) >= 0 )
				if (map.getCell(nextPosition.x, nextPosition.y).getHasCar() == false)
					this.position = nextPosition;
			else
				this.position = nextPosition;


			if (inCity(map)) {
				Cell currentCell = map.getCell(position.x, position.y);
				currentCell.setHasCarTrue();
			}
		}

	}
	
	/** A: actuators */

	/** B: perceptors */

	/** C: decision process */

}


/*			if (direction == NORTH && (position.y - 1) >= 0 && map.getCell(position.x, position.y - 1).getHasCar() == false)
				this.position.y--;

			else if (direction == SOUTH && (position.y + 1) <= map.nY && map.getCell(position.x, position.y + 1).getHasCar() == false)
				this.position.y++;

			else if (direction == EAST && (position.x + 1) <= map.nX && map.getCell(position.x + 1, position.y).getHasCar() == false)
				this.position.x++;

			else if (direction == WEST && (position.x - 1) >= 0 && map.getCell(position.x - 1, position.y).getHasCar() == false)
				this.position.x--;
*/