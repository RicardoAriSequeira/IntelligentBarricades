package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Police extends Car {

	public Police(Map map, Point position){
		super(map, position);
	}

	private boolean thiefInCell(Map map, Point p) {

		Cell cell = map.getCell(p);

		if (cell != null) {

			if (cell.hasCar()) {

				if (cell.getCar() instanceof Thief) {
					System.out.println("ladrao na celula " + cell.getCoordinates().x + ", " + cell.getCoordinates().y);
					return true;
				}

			}

		}

		return false;

	}

	private Point thiefAround(Map map) {

		Point[] visionPoints = super.getVisionPoints();

		for (Point point : visionPoints)
			if (thiefInCell(map, point))
				return point;

		return null;

	}

	protected int directionDecision(Map map, List<Integer> possibleDirections) {

		Random generator = new Random();
		int r = generator.nextInt(2);

		Point thiefPosition = thiefAround(map);

		if (thiefPosition != null) {

			if (thiefPosition.x < this.position.x) {

				if (thiefPosition.y > this.position.y) {

					if (!map.isCellRoad(this.position.x-1,this.position.y)) return SOUTH;

					if (!map.isCellRoad(this.position.x,this.position.y+1)) return WEST;

					if (r == 0) return SOUTH;
					else return WEST;

				} else if (thiefPosition.y < this.position.y) {

					if (!map.isCellRoad(this.position.x-1,this.position.y)) return NORTH;

					if (!map.isCellRoad(this.position.x,this.position.y-1)) return WEST;

					if (r == 0) return NORTH;
					else return WEST;

				} else return WEST;


			} else if (thiefPosition.x > this.position.x) {

				if (thiefPosition.y > this.position.y) {

					if (!map.isCellRoad(this.position.x+1,this.position.y)) return SOUTH;

					if (!map.isCellRoad(this.position.x,this.position.y+1)) return EAST;

					if (r == 0) return SOUTH;
					else return EAST;

				} else if (thiefPosition.y < this.position.y) {

					if (!map.isCellRoad(this.position.x+1,this.position.y)) return NORTH;

					if (!map.isCellRoad(this.position.x,this.position.y-1)) return EAST;

					if (r == 0) return NORTH;
					else return EAST;
					
				} else return EAST;

			} else {

				if (thiefPosition.y > this.position.y) return SOUTH;
				else return NORTH;

			}

		} else {

			r = generator.nextInt(possibleDirections.size());
			return possibleDirections.get(r);

		}
	}

}