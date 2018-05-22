package barricades;

import java.awt.Point;
import java.util.Random;

public class Map {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	
	public Cell[][] board;
	public int nX, nY;

	public Map(int nX, int nY){
		this.nX = nX;
		this.nY = nY;
		initialize();
	}

	public Cell getCell(int y, int x) {
		return board[x][y];
	}

	/*public boolean hasCar(int x, int y) {
		return board[x][y].getHasCar();		
	}*/
	private void initialize() {

		board = new Cell[nX][nY];

		for(int i=0; i<nX; i++) {

			for(int j=0; j<nY; j++) {

				board[i][j] = new Cell();

				if (i == (nX-1)) {

					board[3][j].setDirection(EAST);
					board[16][j].setDirection(WEST);
					board[17][j].setDirection(EAST);
					board[21][j].setDirection(EAST);
					board[25][j].setDirection(WEST);
					board[29][j].setDirection(EAST);

					if (j<4)
						board[10][j].setDirection(WEST);
					if (j>3 && j<9) {
						board[7][j].setDirection(WEST);
						board[12][j].setDirection(EAST);
					}
					if (j>8 && j<14)
						board[10][j].setDirection(WEST);
					if (j>13 && j<18) {
						board[8][j].setDirection(WEST);
						board[13][j].setDirection(EAST);
					}
					if (j>18 && j<24)
						board[10][j].setDirection(WEST);
					if (j>22 && j<29)
						board[12][j].setDirection(WEST);
					if (j>27 && j<32)
						board[10][j].setDirection(WEST);

				}

			}

			board[i][8].setDirection(SOUTH);
			board[i][9].setDirection(NORTH);
			board[i][22].setDirection(SOUTH);
			board[i][23].setDirection(NORTH);

			if (i<17) {
				board[i][3].setDirection(NORTH);
				board[i][13].setDirection(SOUTH);
				board[i][28].setDirection(SOUTH);
			}
			if (i<21)
				board[i][18].setDirection(NORTH);
			if (i>15 && i<25)
				board[i][29].setDirection(SOUTH);
			if (i>16 && i<32) {
				board[i][5].setDirection(SOUTH);
				board[i][2].setDirection(NORTH);
				board[i][26].setDirection(NORTH);
			}
			if (i>21 && i<26)
				board[i][13].setDirection(NORTH);
			if (i>25 && i<30)
				board[i][18].setDirection(NORTH);
			if (i>28 && i<32)
				board[i][29].setDirection(SOUTH);
			if (i>29 && i<32) {
				board[i][2].setDirection(NORTH);
				board[i][13].setDirection(NORTH);
				board[i][26].setDirection(NORTH);
			}

		}

		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				System.out.print(i + " " + j + " : " + board[i][j].getDirections()[0] + " " + board[i][j].getDirections()[1] + " " + board[i][j].getDirections()[2] + " " + board[i][j].getDirections()[3] + " " + "\n");
	}
}