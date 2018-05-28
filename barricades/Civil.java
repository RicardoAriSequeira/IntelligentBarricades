package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Civil extends Car {

	public Civil(Map map, Point position){
		super(map,position);
	}

	protected int directionDecision(Map map, List<Integer> possibleDirections) {
		Random generator = new Random();
		int r = generator.nextInt(possibleDirections.size());
		return possibleDirections.get(r);
	}

}