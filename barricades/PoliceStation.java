package barricades;

import java.awt.Point;
import java.util.List;

public class PoliceStation {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	private Map map;
	private List<Police> polices;
	private Point thiefPosition;
	private boolean thiefPositionUpdated;
	private boolean oneStepPassed;
	private boolean thiefArrested;

	private int[] seenCells;

	public PoliceStation(Map map) {
		this.map = map;
		this.thiefPosition = null;
		this.thiefPositionUpdated = false;
		this.oneStepPassed = false;
		this.thiefArrested = false;
		this.seenCells = new int[map.nX * map.nY];
	}

	public void resetStation() {
		this.thiefPosition = null;
		this.thiefPositionUpdated = false;
		this.oneStepPassed = false;
		this.thiefArrested = false;
		this.seenCells = new int[map.nX * map.nY];
	}

	public boolean isThiefArrested() {return thiefArrested;}

	public boolean isThiefPositionKnown() {return thiefPositionUpdated;}

	public Point getThiefPosition() {return thiefPosition;}

	public void reportThiefSurrender() {thiefArrested = true;}

	public void updatePolices(List<Police> polices) {this.polices = polices;}

	public void reportThiefPosition(Point p) {
		this.thiefPositionUpdated = true;
		this.thiefPosition = p;
	}

	public void update() {

		for (int i = 0; i < seenCells.length; i++)
			if (seenCells[i] > 0) seenCells[i]--;

		for (Police p: polices)
			seenCells[p.position.x + p.position.y * map.nY] = 10;

		if (this.oneStepPassed)
			this.thiefPositionUpdated = false;
		else
			this.oneStepPassed = true;
	}

	public boolean directionOfThief(Point state, int direction) {

		if (thiefPositionUpdated) {

			if (thiefPosition.x < state.x) {

				if (thiefPosition.y < state.y)
					if (direction == WEST || direction == NORTH) return true;

				else if (thiefPosition.y > state.y)
					if (direction == WEST || direction == SOUTH) return true;

				else
					if (direction == WEST) return true;

			} else if (thiefPosition.x > state.x) {

				if (thiefPosition.y < state.y)
					if (direction == EAST || direction == NORTH) return true;

				else if (thiefPosition.y > state.y)
					if (direction == EAST || direction == SOUTH) return true;

				else
					if (direction == EAST) return true;

			} else {

				if (thiefPosition.y < state.y)
					if (direction == NORTH) return true;

				else
					if (direction == SOUTH) return true;
			}

		} else return false;

		return false;

	}

	public int getReward(Point state, int direction) {

		if (directionOfThief(state, direction)) {
			return 100;
		} else {
			Point nextState = map.getCell(state).getNextState(direction);
			return 25 / 1 + seenCells[nextState.x + nextState.y * map.nY];
		}

		/*
		rewards[(thiefPosition.x+1)+(thiefPosition.y)*map.nY][STILL] += 100;
		rewards[(thiefPosition.x-1)+(thiefPosition.y)*map.nY][STILL] += 100;
		rewards[(thiefPosition.x)+(thiefPosition.y+1)*map.nY][STILL] += 100;
		rewards[(thiefPosition.x)+(thiefPosition.y-1)*map.nY][STILL] += 100;

		rewards[(thiefPosition.x+3)+(thiefPosition.y)*map.nY][WEST] += 100;
		rewards[(thiefPosition.x+2)+(thiefPosition.y)*map.nY][WEST] += 100;
		rewards[(thiefPosition.x-2)+(thiefPosition.y)*map.nY][EAST] += 100;
		rewards[(thiefPosition.x-3)+(thiefPosition.y)*map.nY][EAST] += 100;
		rewards[(thiefPosition.x)+(thiefPosition.y+3)*map.nY][SOUTH] += 100;
		rewards[(thiefPosition.x)+(thiefPosition.y+2)*map.nY][SOUTH] += 100;
		rewards[(thiefPosition.x)+(thiefPosition.y-2)*map.nY][NORTH] += 100;
		rewards[(thiefPosition.x)+(thiefPosition.y-3)*map.nY][NORTH] += 100;

		rewards[(thiefPosition.x+1)+(thiefPosition.y+1)*map.nY][EAST] += 100;
		rewards[(thiefPosition.x+1)+(thiefPosition.y+1)*map.nY][SOUTH] += 100;

		rewards[(thiefPosition.x-1)+(thiefPosition.y+1)*map.nY][WEST] += 100;
		rewards[(thiefPosition.x-1)+(thiefPosition.y+1)*map.nY][SOUTH] += 100;

		rewards[(thiefPosition.x+1)+(thiefPosition.y-1)*map.nY][EAST] += 100;
		rewards[(thiefPosition.x+1)+(thiefPosition.y-1)*map.nY][NORTH] += 100;

		rewards[(thiefPosition.x-1)+(thiefPosition.y-1)*map.nY][WEST] += 100;
		rewards[(thiefPosition.x-1)+(thiefPosition.y-1)*map.nY][NORTH] += 100;

		*/

	}

}