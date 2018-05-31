package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Civil extends Car {

	public Civil(Map map, Point position){
		super(map, position);
	}

	public int directionDecision() {
		Random generator = new Random(12345);
		List<Integer> possibleDirections = map.getCell(position).getLegalDirections();
		int r = generator.nextInt(possibleDirections.size());
		return possibleDirections.get(r);
	}

}