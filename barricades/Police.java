package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Police extends Car {

    private final double gamma = 0.9;
    private final int actionsCount = 5;

	private PoliceStation station;

	public Police(Map map, PoliceStation station, Point position){
		super(map, position);
		this.station = station;
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

	private double maxQ(MapState s) {
		Point thiefPosition = station.getThiefPosition();
        List<Integer> actionsFromState = map.getCell(position).getPossibleDirections(map);
        int indexPolice = map.getCell(position).indexRoad;
        int indexThief = map.getCell(thiefPosition).indexRoad;
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actionsFromState.size(); i++) {
            int action = actionsFromState.get(i);
            double value = station.Q[indexPolice + indexThief * map.nRoads][action];
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

		if (choice < 30) {

		 	return directionDecision();

		 } else {

		// 	if (station.isThiefPositionKnown()) {

				triedDirections = new boolean[4];
				int direction = generator.nextInt(4) + 1;

				while (!possibleDirection(direction)) {
					triedDirections[direction-1] = true;
					if (triedAllDirections()) {
						break;
					}
					direction = generator.nextInt(4) + 1;
				}

				if (!possibleDirection(direction)) {
					return STILL;
				}

				return direction;

		// 	} else {

				// triedDirections = new boolean[legalDirections.size()];
				// int r = generator.nextInt(legalDirections.size());
				// int direction = legalDirections.get(r);

				// while (!possibleDirection(direction)) {
				// 	triedDirections[r] = true;
				// 	if (triedAllDirections()) {
				// 		break;
				// 	}
				// 	r = generator.nextInt(legalDirections.size());
				// 	direction = legalDirections.get(r);
				// }
				
				// if (!possibleDirection(direction)) {
				// 	return STILL;
				// }

				// return direction;

		 	}
		// }
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

	       	Point thiefPosition = station.getThiefPosition();

	        //Point nextState = map.getCell(position).getNextState(action);
	        MapState nextState = map.getNextState(position,thiefPosition, action);

	        int indexPolice = map.getCell(position).indexRoad;
			int indexThief = map.getCell(thiefPosition).indexRoad;

	        double q = station.Q[indexPolice + indexThief * map.nRoads][action];
	        double maxQ = maxQ(nextState);
	        int r = station.getReward(position, action);

	        List<Integer> actionsFromState = map.getCell(position).getPossibleDirections(map);
	        double value = q + ( (float) 1 / (actionsFromState.size() + 1) ) * (r + gamma * maxQ - q);
	        station.Q[indexPolice + indexThief * map.nRoads][action] = value;

			changePosition(action);

		}
	}

	public int directionDecision() {

		searchThiefAround();

		Point thiefPosition = station.getThiefPosition();
        List<Integer> actionsFromState = map.getCell(position).getPossibleDirections(map);
        
        int action, bestAction = STILL;
        int indexPolice = map.getCell(position).indexRoad;
		int indexThief = map.getCell(thiefPosition).indexRoad;

		double value, maxValue = Double.MIN_VALUE;

        for (int i = 0; i < actionsFromState.size(); i++) {
            action = actionsFromState.get(i);
            value = station.Q[indexPolice + indexThief * map.nRoads][action];
 
            if (value > maxValue) {
                maxValue = value;
                bestAction = action;
            }
        }

        //for (int i = 0; i < 5; i++)
       	//	System.out.println("Q[" + (indexPolice + (station.getThiefOrientation(position)-1)) + "][" + i + "]: " + station.Q[indexPolice + (station.getThiefOrientation(position)-1)][i]);

        return bestAction;
	}
}