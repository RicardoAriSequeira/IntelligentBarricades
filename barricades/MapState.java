package barricades;

import java.awt.Point;

public class MapState {

	private Point thiefPosition, policePosition;

	public MapState(Point thiefPosition, Point policePosition){
		this.thiefPosition = thiefPosition;
		this.policePosition = policePosition;
	}

	public int getStateIndex(Map map) {
		int indexPolice = map.getCell(policePosition).indexRoad;
		int indexThief = map.getCell(thiefPosition).indexRoad;
		return indexPolice + (indexThief * map.nRoads);
	}

}