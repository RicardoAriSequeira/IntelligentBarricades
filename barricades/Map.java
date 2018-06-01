package barricades;

import java.awt.Point;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Map {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	
	public Cell[][] board;
	public int nX, nY;

	private List<Cell> entryCells;

	public Map(int nX, int nY){
		this.nX = nX;
		this.nY = nY;
		initialize();
	}

	public Cell getCell(Point p) {
		if (inMap(p)) return board[p.x][p.y];
		return null;
	}

	public void setCellDirection(Point p, int direction) {

		Cell c = getCell(p);
		c.setDirection(direction);

		if ((direction == NORTH && p.y == nY-1) ||
			(direction == SOUTH && p.y == 0) ||
			(direction == EAST && p.x == 0) ||
			(direction == WEST && p.x == nX-1))

			entryCells.add(c);

	}

	public List<Cell> getEntryCells() {return entryCells;}

	public boolean inMap(Point p) {
		if (p.x < nX && p.y < nY && p.x >= 0 && p.y >= 0) return true;
		return false;
	}

	public boolean isCellRoad(Point p) {

		Cell cell = getCell(p);

		if (cell != null) {
			return cell.isRoad();
		}

		return false;

	}

	public void initialize() {

		board = new Cell[nX][nY];
		entryCells = new ArrayList<Cell>();

		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				board[i][j] = new Cell(i,j);

		Cell c = getCell(new Point(16, 2));
		c.setIsGarage();
		for(int i=0; i<nX; i++) {

			setCellDirection(new Point(8,i), SOUTH);
			setCellDirection(new Point(9,i), NORTH);
			setCellDirection(new Point(22,i), SOUTH);
			setCellDirection(new Point(23,i), NORTH);

			if (i<17) {
				setCellDirection(new Point(3,i), NORTH);
				setCellDirection(new Point(13,i), SOUTH);
				setCellDirection(new Point(28,i), SOUTH);
			}
			if ((i<21) || (i>25 && i<30))
				setCellDirection(new Point(18,i), NORTH);
			if (i>15 && i<25)
				setCellDirection(new Point(29,i), SOUTH);
			if (i>16 && i<32)
				setCellDirection(new Point(5,i), SOUTH);
			if ((i>16 && i<25) || (i>29 && i<32))
				setCellDirection(new Point(26,i), NORTH);
			if (i>16 && i<32)
				setCellDirection(new Point(2,i), NORTH);
			if (i>21 && i<26)
				setCellDirection(new Point(13,i), NORTH);
			if (i>29 && i<32) {
				setCellDirection(new Point(13,i), NORTH);
				setCellDirection(new Point(26,i), NORTH);
			}
			if (i>25 && i<30)
				setCellDirection(new Point(28,i), NORTH);
		}

		for(int j=0; j<nY; j++) {

			setCellDirection(new Point(j,3), EAST);
			setCellDirection(new Point(j,16), WEST);
			setCellDirection(new Point(j,17), EAST);
			setCellDirection(new Point(j,21), EAST);
			setCellDirection(new Point(j,25), WEST);
			setCellDirection(new Point(j,29), EAST);

			if ((j<4) || (j>8 && j<14) || (j>18 && j<24) || (j>28 && j<32))
				setCellDirection(new Point(j,10), WEST);
			if (j>3 && j<9) {
				setCellDirection(new Point(j,7), WEST);
				setCellDirection(new Point(j,12), EAST);
			}
			if (j>13 && j<18) {
				setCellDirection(new Point(j,8), WEST);
				setCellDirection(new Point(j,13), EAST);
			}
			if (j>22 && j<29)
				setCellDirection(new Point(j,12), WEST);

		}

	}
}