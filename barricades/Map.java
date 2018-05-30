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

	private void initialize() {

		board = new Cell[nX][nY];
		entryCells = new ArrayList<Cell>();

		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				board[i][j] = new Cell(i,j);


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
			if ((i>15 && i<25) || (i>28 && i<32))
				setCellDirection(new Point(29,i), SOUTH);
			if (i>16 && i<32) {
				setCellDirection(new Point(5,i), SOUTH);
				setCellDirection(new Point(26,i), NORTH);
			}
			if (i>16 && i<21)
				setCellDirection(new Point(2,i), NORTH);
			if (i>21 && i<26)
				setCellDirection(new Point(13,i), NORTH);
			if (i>29 && i<32) {
				setCellDirection(new Point(2,i), NORTH);
				setCellDirection(new Point(13,i), NORTH);
				setCellDirection(new Point(26,i), NORTH);
			}
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

		new Barricade(getCell(new Point(8,16)));
		new Barricade(getCell(new Point(8,17)));
		new Barricade(getCell(new Point(9,16)));
		new Barricade(getCell(new Point(9,17)));

		new Barricade(getCell(new Point(22,16)));
		new Barricade(getCell(new Point(22,17)));
		new Barricade(getCell(new Point(23,16)));
		new Barricade(getCell(new Point(23,17)));

		new Barricade(getCell(new Point(3,3)));
		new Barricade(getCell(new Point(3,7)));
		new Barricade(getCell(new Point(3,10)));
		new Barricade(getCell(new Point(3,12)));
		
		new Barricade(getCell(new Point(8,3)));
		new Barricade(getCell(new Point(9,3)));
		new Barricade(getCell(new Point(8,21)));
		new Barricade(getCell(new Point(9,21)));
		new Barricade(getCell(new Point(8,25)));
		new Barricade(getCell(new Point(9,25)));
		new Barricade(getCell(new Point(8,29)));
		new Barricade(getCell(new Point(9,29)));

		new Barricade(getCell(new Point(13,3)));
		new Barricade(getCell(new Point(13,8)));
		new Barricade(getCell(new Point(13,10)));
		new Barricade(getCell(new Point(13,13)));
		
		new Barricade(getCell(new Point(18,3)));
		new Barricade(getCell(new Point(18,8)));
		new Barricade(getCell(new Point(18,10)));
		new Barricade(getCell(new Point(18,13)));

		new Barricade(getCell(new Point(22,3)));
		new Barricade(getCell(new Point(23,3)));

		new Barricade(getCell(new Point(28,3)));
		new Barricade(getCell(new Point(28,10)));
		new Barricade(getCell(new Point(28,12)));

		new Barricade(getCell(new Point(8,7)));
		new Barricade(getCell(new Point(9,10)));
		new Barricade(getCell(new Point(8,12)));

		new Barricade(getCell(new Point(22,10)));
		new Barricade(getCell(new Point(23,12)));
		new Barricade(getCell(new Point(22,21)));
		new Barricade(getCell(new Point(23,21)));
		new Barricade(getCell(new Point(22,25)));
		new Barricade(getCell(new Point(23,25)));
		new Barricade(getCell(new Point(22,29)));
		new Barricade(getCell(new Point(23,29)));

		new Barricade(getCell(new Point(3,16)));
		new Barricade(getCell(new Point(2,17)));

		new Barricade(getCell(new Point(5,17)));

		new Barricade(getCell(new Point(2,21)));
		new Barricade(getCell(new Point(5,21)));
		new Barricade(getCell(new Point(13,21)));
		new Barricade(getCell(new Point(18,21)));
		new Barricade(getCell(new Point(29,21)));

		new Barricade(getCell(new Point(5,25)));
		new Barricade(getCell(new Point(13,25)));
		new Barricade(getCell(new Point(18,25)));
		new Barricade(getCell(new Point(29,25)));
		
		new Barricade(getCell(new Point(2,29)));
		new Barricade(getCell(new Point(5,29)));
		new Barricade(getCell(new Point(13,29)));
		new Barricade(getCell(new Point(18,29)));
		new Barricade(getCell(new Point(29,29)));
		

		new Barricade(getCell(new Point(13,16)));
		new Barricade(getCell(new Point(18,16)));
		new Barricade(getCell(new Point(18,17)));
		new Barricade(getCell(new Point(26,17)));
		new Barricade(getCell(new Point(26,21)));
		new Barricade(getCell(new Point(26,25)));
		new Barricade(getCell(new Point(26,29)));

		new Barricade(getCell(new Point(28,16)));
		new Barricade(getCell(new Point(29,17)));




		/*
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				System.out.print(i + " " + j + " : " + board[i][j].getDirections()[0] + " " + board[i][j].getDirections()[1] + " " + board[i][j].getDirections()[2] + " " + board[i][j].getDirections()[3] + " " + "\n");

				*/
	}
}