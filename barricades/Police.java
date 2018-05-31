package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Police extends Car {

    private final double gamma = 0.9;
    private final int actionsCount = 5;

	private PoliceStation station;
	private double[][] Q;

	public Police(Map map, PoliceStation station, Point position){
		super(map, position);
		this.station = station;
    	this.Q = new double[map.nX * map.nY][actionsCount];
	}

	public void restartPolice(Point position) {
		this.position = position;
		this.map.getCell(position).setCar(this);
	}

	private void searchThiefAround() {

		Cell cell;
		Point[] visionPoints = getVisionPoints();

		for (Point point : visionPoints) {

			cell = map.getCell(point);

			if (cell != null) {

				if (cell.hasCar()) {

					Car car = cell.getCar();

					if (car instanceof Thief) {
						//System.out.println("Police in " + position.x + ", " + position.y + " found thief in cell " + point.x + "," + point.y);
						station.reportThiefPosition(point);
						if (((Thief)car).surrender()) station.reportThiefSurrender();
						//return point;
					}
				}
			}
		}

		//return null;
	}

	private double maxQ(Point s) {
        List<Integer> actionsFromState = map.getCell(position).getPossibleDirections(map);
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actionsFromState.size(); i++) {
            int action = actionsFromState.get(i);
            double value = Q[s.x+map.nY*s.y][action];
            if (value > maxValue) maxValue = value;
        }
        return maxValue;
    }

    public void train() {

		if (map.inMap(this.position)) {

			searchThiefAround();

			Random generator = new Random();

	        List<Integer> actionsFromState = map.getCell(position).getPossibleDirections(map);

			if (actionsFromState.size() == 0) {
				System.out.println("Error: there is no directions in cell (" + position.x + "," + position.y + ")");
				return;
			}
	         
	        int index = generator.nextInt(actionsFromState.size());
	        int action = actionsFromState.get(index);

	        Point nextState = map.getCell(position).getNextState(action);

	        double q = Q[position.x+map.nY*position.y][action];
	        double maxQ = maxQ(nextState);
	        int r = station.getReward(position, action);

	        double value = q + ( 1 / (actionsFromState.size() + 1) ) * (r + gamma * maxQ - q);
	        Q[position.x+map.nY*position.y][action] = value;

			changePosition(action);

		}

	}

	public int directionDecision() {

        List<Integer> actionsFromState = map.getCell(position).getPossibleDirections(map);
        double maxValue = Double.MIN_VALUE;
        int bestAction = STILL;

        Point state = position; // default goto self if not found

        for (int i = 0; i < actionsFromState.size(); i++) {
            int action = actionsFromState.get(i);
            double value = Q[state.x + state.y*map.nY][action];
 
            if (value > maxValue) {
                maxValue = value;
                state = map.getCell(state).getNextState(action);
                bestAction = action;
            }
        }

        return bestAction;

		/*

		Point thiefPosition = thiefAround();

		if (station.isThiefPositionKnown()) {

			System.out.println("Helping in the pursuit!");

			return goToPoint(station.getThiefPosition());
		}

		if (thiefPosition != null) {

			return goToPoint(thiefPosition);

		} else {
			Random generator = new Random(12345);
			List<Integer> possibleDirections = map.getCell(position).getPossibleDirections();
			int r = generator.nextInt(possibleDirections.size());
			return possibleDirections.get(r);
		}

		*/

	}

}