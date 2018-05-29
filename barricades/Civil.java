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

}