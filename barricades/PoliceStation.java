package barricades;

import java.awt.Point;
import java.util.List;

public class PoliceStation {

	private List<Police> polices;
	private Point thiefPosition;
	private boolean thiefPositionUpdated;

	public PoliceStation() {
		this.thiefPosition = null;
		this.thiefPositionUpdated = false;
	}

	public void updatePolices(List<Police> polices) {
		this.polices = polices;
	}

	public void reportThiefPosition(Point p) {
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
		this.thiefPositionUpdated = false;
	}

}