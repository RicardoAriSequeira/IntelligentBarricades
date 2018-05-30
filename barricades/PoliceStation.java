package barricades;

import java.awt.Point;
import java.util.List;

public class PoliceStation {

	private List<Police> polices;
	private Point thiefPosition;
	private boolean thiefPositionUpdated;
	private boolean oneStepPassed;

	public PoliceStation() {
		this.thiefPosition = null;
		this.thiefPositionUpdated = false;
		this.oneStepPassed = false;
	}

	public void updatePolices(List<Police> polices) {
		this.polices = polices;
	}

	public void reportThiefPosition(Point p) {
		this.oneStepPassed = false;
		this.thiefPositionUpdated = true;
		this.thiefPosition = p;
	}

	public boolean isThiefPositionKnown() {
		return thiefPositionUpdated;
	}

	public Point getThiefPosition() {
		return thiefPosition;
	}

	public void update() {
		if (this.oneStepPassed)
			this.thiefPositionUpdated = false;
		else
			this.oneStepPassed = true;
	}

}