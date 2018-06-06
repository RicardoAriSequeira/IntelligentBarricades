package barricades;

import java.awt.Point;
import java.util.List;

public class PoliceStation {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	private Thief thief;
	private Map map;
	private List<Police> polices;
	//private Point thiefPosition;
	private boolean oneStepPassed, thiefPositionUpdated, thiefArrested;

	private int[] seenCells;

	public PoliceStation(Map map, Thief thief) {
		this.map = map;
		//this.thiefPosition = null;
		//this.oneStepPassed = false;
		this.thiefArrested = false;
		this.thiefPositionUpdated = true;
		this.seenCells = new int[map.nX * map.nY];
		this.thief = thief;
	}

	public void resetStation() {
		//this.thiefPosition = null;
		//this.oneStepPassed = false;
		this.thiefArrested = false;
		this.thiefPositionUpdated = true;
		this.seenCells = new int[map.nX * map.nY];
	}

	public boolean isThiefArrested() {return thiefArrested;}

	public boolean isThiefPositionKnown() {return thiefPositionUpdated;}

	//public Point getThiefPosition() {return thiefPosition;}
	public Point getThiefPosition() {return thief.position;}

	public void reportThiefSurrender() {thiefArrested = true;}

	public void updatePolices(List<Police> polices) {this.polices = polices;}

	public void reportThiefPosition(Point p) {

		/*

		if (!this.thiefPositionUpdated || this.oneStepPassed || this.thiefPosition == null) {
			this.thiefPosition = p;
			this.oneStepPassed = false;
			this.thiefPositionUpdated = true;
			return;
		}

		if (!this.oneStepPassed && !p.equals(this.thiefPosition)) {
			this.thiefPosition = p;
			this.thiefPositionUpdated = true;
			return;
		}

		*/
	}

	public void update() {

		for (int i = 0; i < seenCells.length; i++) {
			if (seenCells[i] > 0) seenCells[i]--;
		}

		for (Police p: polices) {
			seenCells[p.position.x + p.position.y * map.nY] = 10;
		}

		/*

		if (this.oneStepPassed) {
			this.thiefPositionUpdated = false;
		}
		else {
			this.oneStepPassed = true;
		}

		*/
	}

	public boolean directionOfThief(Point state, int direction) {

		if (thiefPositionUpdated) {

			if (thief.position.x < state.x) {

				if (thief.position.y < state.y) {
					if (direction == WEST || direction == NORTH) {
						return true;
					}
				}

				else if (thief.position.y > state.y){
					if (direction == WEST || direction == SOUTH) return true;
				}

				else{
					if (direction == WEST) {
						return true;
					}
				}

			} else if (thief.position.x > state.x) {

				

				if (thief.position.y < state.y) {
					if (direction == EAST || direction == NORTH) {
						return true;
					}
				}

				else if (thief.position.y > state.y) {
					if (direction == EAST || direction == SOUTH) {
						return true;
					}
				}

				else {
					if (direction == EAST) {
						return true;
					}
				}

			} else {

				if (thief.position.y < state.y) {
					if (direction == NORTH) {
						return true;
					}
				}

				else {
					if (direction == SOUTH) {
						return true;
					}
				}
			}
		}

		return false;

	}

	public boolean nextToThief(Point p) {

		if (thiefPositionUpdated) {

			Cell cell = map.getCell(new Point(p.x+1,p.y));

			if (cell != null) {
				if (map.getCell(new Point(p.x+1,p.y)).hasThief()) {
					return true;
				}
			}

			cell = map.getCell(new Point(p.x-1,p.y));

			if (cell != null) {
				if (map.getCell(new Point(p.x-1,p.y)).hasThief()) {
					return true;
				}
			}

			cell = map.getCell(new Point(p.x,p.y+1));

			if (cell != null) {
				if (map.getCell(new Point(p.x,p.y+1)).hasThief()) {
					return true;
				}
			}

			cell = map.getCell(new Point(p.x,p.y-1));

			if (cell != null) {
				if (map.getCell(new Point(p.x,p.y-1)).hasThief()) {
					return true;
				}
			}

		}

		return false;

	}

	public int getReward(Point state, int direction) {

		int reward = 0;

		if (nextToThief(state) && thiefArrested) {
			reward += 1000;
		}

		if (directionOfThief(state, direction)) {
			reward += 150;

		} else {
			//Point nextState = map.getCell(state).getNextPoint(direction);
			//reward += 50 / (1 + seenCells[nextState.x + nextState.y * map.nY]);
		}

		return reward;

	}

}