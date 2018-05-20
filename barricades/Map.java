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

	private void initialize() {

		board = new Cell[nX][nY];

		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				board[i][j] = new Cell();

		for(int i=0; i<17; i++){
			board[i][3].setRoad();
			board[i][3].setDirection(NORTH);

			board[i][13].setRoad();
			board[i][13].setDirection(SOUTH);

			board[i][28].setRoad();
			board[i][28].setDirection(SOUTH);
		}
		for(int i=0; i<32; i++){
			board[i][8].setRoad();
			board[i][8].setDirection(SOUTH);

			board[i][9].setRoad();
			board[i][9].setDirection(NORTH);

			board[i][22].setRoad();
			board[i][22].setDirection(SOUTH);

			board[i][23].setRoad();
			board[i][23].setDirection(NORTH);

			board[3][i].setRoad();
			board[3][i].setDirection(EAST);

			board[16][i].setRoad();
			board[16][i].setDirection(WEST);

			board[17][i].setRoad();
			board[17][i].setDirection(EAST);

			board[21][i].setRoad();
			board[21][i].setDirection(EAST);

			board[25][i].setRoad();
			board[25][i].setDirection(WEST);

			board[29][i].setRoad();
			board[29][i].setDirection(EAST);			
		}
		for(int i=0; i<21; i++){
			board[i][18].setRoad();
			board[i][18].setDirection(NORTH);
		}
		for(int i=0; i<4; i++){
			board[10][i].setRoad();
			board[10][i].setDirection(WEST);
		}
		for(int i=4; i<9; i++){
			board[7][i].setRoad();
			board[7][i].setDirection(WEST);

			board[12][i].setRoad();
			board[12][i].setDirection(EAST);
		}
		for(int i=9; i<14; i++){
			board[10][i].setRoad();
			board[10][i].setDirection(WEST);
		}
		for(int i=14; i<18; i++){
			board[8][i].setRoad();
			board[8][i].setDirection(WEST);

			board[13][i].setRoad();
			board[13][i].setDirection(EAST);
		}
		for(int i=19; i<24; i++){
			board[10][i].setRoad();
			board[10][i].setDirection(WEST);
		}
		for(int i=23; i<29; i++){
			board[12][i].setRoad();
			board[12][i].setDirection(WEST);
		}
		for(int i=28; i<32; i++){
			board[10][i].setRoad();
			board[10][i].setDirection(WEST);
		}
		for(int i=17; i<32; i++){
			board[i][5].setRoad();
			board[i][5].setDirection(SOUTH);
		}
		for(int i=17; i<22; i++){
			board[i][2].setRoad();
			board[i][2].setDirection(NORTH);

			board[i][26].setRoad();
			board[i][26].setDirection(NORTH);
		}
		for(int i=30; i<32; i++){
			board[i][2].setRoad();
			board[i][2].setDirection(NORTH);

			board[i][13].setRoad();
			board[i][13].setDirection(NORTH);

			board[i][26].setRoad();
			board[i][26].setDirection(NORTH);
		}
		for(int i=29; i<32; i++){
			board[i][29].setRoad();
			board[i][29].setDirection(SOUTH);
		}
		for(int i=26; i<30; i++){
			board[i][18].setRoad();
			board[i][18].setDirection(NORTH);
		}
		for(int i=22; i<26; i++){
			board[i][13].setRoad();
			board[i][13].setDirection(NORTH);
		}
		for(int i=16; i<25; i++){
			board[i][29].setRoad();
			board[i][29].setDirection(SOUTH);
		}

		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				System.out.print(i + " " + j + " : " + board[i][j].getDirections()[0] + " " + board[i][j].getDirections()[1] + " " + board[i][j].getDirections()[2] + " " + board[i][j].getDirections()[3] + " " + "\n");
	}
}