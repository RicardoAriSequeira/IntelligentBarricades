package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.List;

public class Barricade {

	private Cell[] cells;

	public Barricade(Cell cell){
		this.cells = new Cell[1];
		this.cells[0] = cell;
		cell.setIsBarricade();
	}

	public Barricade(Cell[] cells){
		this.cells = cells;
		for (Cell c: cells) c.setIsBarricade();
	}

}