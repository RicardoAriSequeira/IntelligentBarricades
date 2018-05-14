package barricades;

import java.awt.Point;

public class Car {

	public Point position;
	
	public Car(Point position){
		this.position = position;
	} 
	
	public void go(){
		if(position.x>10) position.x--;
		else position.x++;
	}
	
	/** A: actuators */

	/** B: perceptors */

	/** C: decision process */

}
