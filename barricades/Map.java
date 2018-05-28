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

	public Cell getCell(int y, int x) {
		if (inMap(x,y)) return board[x][y];
		return null;
	}

	public Cell getCell(Point p) {
		if (inMap(p)) return board[p.x][p.y];
		return null;
	}

	public void setCellDirection(int x, int y, int direction) {

		board[x][y].setDirection(direction);

		if ((direction == NORTH && x == nX-1) ||
			(direction == SOUTH && x == 0) ||
			(direction == EAST && y == 0) ||
			(direction == WEST && y == nY-1))

			entryCells.add(board[x][y]);

	}

	public List<Cell> getEntryCells() {return entryCells;}

	public boolean inMap(int x, int y) {
		if (x < nX && y < nY && x >= 0 && y >= 0) return true;
		return false;
	}

	public boolean inMap(Point p) {return inMap(p.x, p.y);}

	public boolean isCellRoad(int x, int y) {

		Cell cell = getCell(x,y);

		if (cell != null) {
			return cell.isRoad();
		}

		return false;

	}

	private void initialize() {

		board = new Cell[nX][nY];
		entryCells = new ArrayList<Cell>();

		for(int i=0; i<nX; i++) {

			for(int j=0; j<nY; j++) {

				board[i][j] = new Cell(i,j);

				if (i == (nX-1)) {

					setCellDirection(3, j, EAST);
					setCellDirection(16, j, WEST);
					setCellDirection(17, j, EAST);
					setCellDirection(21, j, EAST);
					setCellDirection(25, j, WEST);
					setCellDirection(29, j, EAST);

					if ((j<4) || (j>8 && j<14) || (j>18 && j<24) || (j>28 && j<32))
						setCellDirection(10, j, WEST);
					if (j>3 && j<9) {
						setCellDirection(7, j, WEST);
						setCellDirection(12, j, EAST);
					}
					if (j>13 && j<18) {
						setCellDirection(8, j, WEST);
						setCellDirection(13, j, EAST);
					}
					if (j>22 && j<29)
						setCellDirection(12, j, WEST);
				}
			}

			setCellDirection(i, 8, SOUTH);
			setCellDirection(i, 9, NORTH);
			setCellDirection(i, 22, SOUTH);
			setCellDirection(i, 23, NORTH);

			if (i<17) {
				setCellDirection(i, 3, NORTH);
				setCellDirection(i, 13, SOUTH);
				setCellDirection(i, 28, SOUTH);
			}
			if ((i<21) || (i>25 && i<30))
				setCellDirection(i, 18, NORTH);
			if ((i>15 && i<25) || (i>28 && i<32))
				setCellDirection(i, 29, SOUTH);
			if (i>16 && i<32) {
				setCellDirection(i, 5, SOUTH);
				setCellDirection(i, 26, NORTH);
			}
			if (i>16 && i<21)
				setCellDirection(i, 2, NORTH);
			if (i>21 && i<26)
				setCellDirection(i, 13, NORTH);
			if (i>29 && i<32) {
				setCellDirection(i, 2, NORTH);
				setCellDirection(i, 13, NORTH);
				setCellDirection(i, 26, NORTH);
			}

		}

		/*

		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				System.out.print(i + " " + j + " : " + board[i][j].getDirections()[0] + " " + board[i][j].getDirections()[1] + " " + board[i][j].getDirections()[2] + " " + board[i][j].getDirections()[3] + " " + "\n");

				*/
	}
}