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
	public int direction = STILL;
	
	public Car(Point position){
		this.position = position;
	} 
	
	public void go(Map map){

		/*

		if (direction == STILL) {

			positionDirections = board[position.x][position.y].getDirections();

			Random generator = new Random();
			generator.nextInt(0,4);

		} else if (direction == NORTH) {


		} else if (direction == SOUTH) {

		} else if (direction == EAST) {

		} else if (direction == WEST) {

		}

		*/

		if(position.x>10) position.x--;
		else position.x++;
	}
	
	/** A: actuators */

	/** B: perceptors */

	/** C: decision process */

}
