package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Police extends Car {

    private final double gamma = 0.9;
    private final int actionsCount = 5;

	private PoliceStation station;
	public double[][] Q;

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

						station.reportThiefPosition(point);

						if (((Thief)car).surrender()) {
							station.reportThiefSurrender();
						}
					}
				}
			}
		}
	}

	private double maxQ(Point s) {
        List<Integer> actionsFromState = map.getCell(position).getLegalDirections();
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actionsFromState.size(); i++) {
            int action = actionsFromState.get(i);
            double value = Q[s.x+map.nY*s.y][action];
            if (value > maxValue) maxValue = value;
        }
        return maxValue;
    }

    public int trainDirectionDecision(List<Integer> legalDirections) {

		Random generator = new Random();
		int choice = generator.nextInt(100);

		if (station.nextToThief(position)) {
			return STILL;
		}

		if (choice < 50) {

			return directionDecision();

		} else {

			if (station.isThiefPositionKnown()) {

				triedDirections = new boolean[4];
				int direction = generator.nextInt(4);

				while (!possibleDirection(direction)) {
					triedDirections[direction] = true;
					if (triedAllDirections()) {
						break;
					}
					direction = generator.nextInt(4);
				}

				if (!possibleDirection(direction)) {
					return STILL;
				}

				return direction;

			} else {

				triedDirections = new boolean[legalDirections.size()];
				int r = generator.nextInt(legalDirections.size());
				int direction = legalDirections.get(r);

				while (!possibleDirection(direction)) {
					triedDirections[r] = true;
					if (triedAllDirections()) {
						break;
					}
					r = generator.nextInt(legalDirections.size());
					direction = legalDirections.get(r);
				}
				
				if (!possibleDirection(direction)) {
					return STILL;
				}

				return direction;

			}
		}
    }

    public void train() {

		if (map.inMap(this.position)) {

			searchThiefAround();

	        List<Integer> legalDirections = map.getCell(position).getLegalDirections();

			if (legalDirections.size() == 0) {
				System.out.println("Error: there is no directions in cell (" + position.x + "," + position.y + ")");
				return;
			}

	       	int action = trainDirectionDecision(legalDirections);

	        Point nextState = map.getCell(position).getNextState(action);

	        double q = Q[position.x+map.nY*position.y][action];
	        double maxQ = maxQ(nextState);
	        int r = station.getReward(position, action);

	        List<Integer> actionsFromState = map.getCell(position).getPossibleDirections(map);
	        double value = q + ( (float) 1 / (actionsFromState.size() + 1) ) * (r + gamma * maxQ - q);
	        Q[position.x+map.nY*position.y][action] = value;

			changePosition(action);

		}
	}

	public int directionDecision() {

        List<Integer> actionsFromState = map.getCell(position).getPossibleDirections(map);
        double maxValue = Double.MIN_VALUE;
        int bestAction = STILL;

        for (int i = 0; i < actionsFromState.size(); i++) {
            int action = actionsFromState.get(i);
            double value = Q[position.x + position.y*map.nY][action];
 
            if (value > maxValue) {
                maxValue = value;
                bestAction = action;
            }
        }

        return bestAction;
	}
}