package barricades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;


/**
 * Graphical interface
 * @author Rui Henriques
 */
public class GraphicalInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	
	static JTextField speed;
	static JPanel boardPanel;
	static JButton run, reset, step;
	
	public GraphicalInterface(Board board) {
		setTitle("FirePrevention");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(640, 600);
		add(createButtonPanel(board));
		boardPanel = new JPanel();
		boardPanel.setSize(new Dimension(600,500));
		boardPanel.setLocation(new Point(20,60));
		boardPanel.setLayout(new GridLayout(board.nX,board.nY));
		for(int i=0; i<board.nX; i++)
			for(int j=0; j<board.nY; j++)
				boardPanel.add(new JPanel());
		displayBoard(board);
		displayCars(board);
		board.GUI = this;
		add(boardPanel);
	}

	public void displayBoard(Board board) {
		for(int i=0; i<board.nX; i++){
			for(int j=0; j<board.nY; j++){ 
				JPanel p = ((JPanel)boardPanel.getComponent(i*board.nY+j));
				p.setBackground(new Color(0,0,0));
				p.setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}
		for(int i=0; i<17; i++){
			board.board[i][3].setRoad();
			board.board[i][3].setNorthDirection();
		}
		for(int i=0; i<32; i++){
			board.board[i][8].setRoad();
			board.board[i][8].setSouthDirection();
		}
		for(int i=0; i<32; i++){
			board.board[i][9].setRoad();
			board.board[i][9].setNorthDirection();
		}
		for(int i=0; i<32; i++){
			board.board[i][22].setRoad();
			board.board[i][22].setSouthDirection();
		}
		for(int i=0; i<32; i++){
			board.board[i][23].setRoad();
			board.board[i][23].setNorthDirection();
		}
		for(int i=0; i<17; i++){
			board.board[i][13].setRoad();
			board.board[i][13].setSouthDirection();
		}
		for(int i=0; i<21; i++){
			board.board[i][18].setRoad();
			board.board[i][18].setNorthDirection();
		}
		for(int i=0; i<17; i++){
			board.board[i][28].setRoad();
			board.board[i][28].setSouthDirection();
		}
		for(int i=0; i<32; i++){
			board.board[3][i].setRoad();
			board.board[3][i].setEastDirection();
		}
		for(int i=0; i<32; i++){
			board.board[16][i].setRoad();
			board.board[16][i].setWestDirection();
		}
		for(int i=0; i<32; i++){
			board.board[17][i].setRoad();
			board.board[17][i].setEastDirection();
		}
		for(int i=0; i<32; i++){
			board.board[21][i].setRoad();
			board.board[21][i].setEastDirection();
		}		
		for(int i=0; i<32; i++){
			board.board[25][i].setRoad();
			board.board[25][i].setWestDirection();
		}
		for(int i=0; i<32; i++){
			board.board[29][i].setRoad();
			board.board[29][i].setEastDirection();
		}
		for(int i=0; i<4; i++){
			board.board[10][i].setRoad();
			board.board[10][i].setWestDirection();
		}
		for(int i=4; i<10; i++){
			board.board[7][i].setRoad();
			board.board[7][i].setWestDirection();
		}
		for(int i=4; i<10; i++){
			board.board[12][i].setRoad();
			board.board[12][i].setEastDirection();
		}
		for(int i=10; i<14; i++){
			board.board[10][i].setRoad();
			board.board[10][i].setWestDirection();
		}
		for(int i=14; i<18; i++){
			board.board[8][i].setRoad();
			board.board[8][i].setWestDirection();
		}
		for(int i=14; i<18; i++){
			board.board[13][i].setRoad();
			board.board[13][i].setEastDirection();
		}
		for(int i=19; i<22; i++){
			board.board[10][i].setRoad();
			board.board[10][i].setWestDirection();
		}
		for(int i=23; i<29; i++){
			board.board[12][i].setRoad();
			board.board[12][i].setWestDirection();
		}
		for(int i=29; i<32; i++){
			board.board[10][i].setRoad();
			board.board[10][i].setWestDirection();
		}
		for(int i=18; i<32; i++){
			board.board[i][5].setRoad();
			board.board[i][5].setSouthDirection();
		}
		for(int i=18; i<22; i++){
			board.board[i][2].setRoad();
			board.board[i][2].setNorthDirection();
		}
		for(int i=29; i<32; i++){
			board.board[i][2].setRoad();
			board.board[i][2].setNorthDirection();
		}
		for(int i=26; i<29; i++){
			board.board[i][18].setRoad();
			board.board[i][18].setNorthDirection();
		}
		for(int i=29; i<32; i++){
			board.board[i][13].setRoad();
			board.board[i][13].setNorthDirection();
		}
		for(int i=22; i<26; i++){
			board.board[i][13].setRoad();
			board.board[i][13].setNorthDirection();
		}
		for(int i=18; i<26; i++){
			board.board[i][29].setRoad();
			board.board[i][29].setSouthDirection();
		}
		for(int i=29; i<32; i++){
			board.board[i][29].setRoad();
			board.board[i][29].setSouthDirection();
		}
		for(int i=29; i<32; i++){
			board.board[i][26].setRoad();
			board.board[i][26].setNorthDirection();
		}
		for(int i=18; i<21; i++){
			board.board[i][26].setRoad();
			board.board[i][26].setNorthDirection();
		}

		for(int i=0; i<board.nX; i++){
			for(int j=0; j<board.nY; j++){
				if (board.board[i][j].isRoad()){
					JPanel p = ((JPanel)boardPanel.getComponent(i*board.nY+j));
					p.setBackground(new Color(255,255,255));
				}
			}
		}

		boardPanel.invalidate();
	}
	
	public void removeCars(Board board) {
		for(Car car : board.UAVs){
			JPanel p = ((JPanel)boardPanel.getComponent(car.position.x+car.position.y*board.nX));
			p.setBorder(BorderFactory.createLineBorder(Color.white));			
		}
		boardPanel.invalidate();
	}

	public void displayCars(Board board) {
		for(Car car : board.UAVs){
			JPanel p = ((JPanel)boardPanel.getComponent(car.position.x+car.position.y*board.nX));
			p.setBorder(BorderFactory.createLineBorder(Color.red,3));			
		}
		boardPanel.invalidate();
	}

	private Component createButtonPanel(Board board) {
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(600,50));
		panel.setLocation(new Point(0,0));
		
		step = new JButton("Step");
		panel.add(step);
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(run.getText().equals("Run")) board.step();
				else board.stop();
			}
		});
		reset = new JButton("Reset");
		panel.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				board.reset();
			}
		});
		run = new JButton("Run");
		panel.add(run);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(run.getText().equals("Run")){
					int time = -1;
					try {
						time = Integer.valueOf(speed.getText());
					} catch(Exception e){
						JTextPane output = new JTextPane();
						output.setText("Please insert an integer value to set the time per step\nValue inserted = "+speed.getText());
						JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.PLAIN_MESSAGE);
					}
					if(time>0){
						board.run(time);
	 					run.setText("Stop");						
					}
 				} else {
					board.stop();
 					run.setText("Run");
 				}
			}
		});
		speed = new JTextField(" time per step in [1,100] ");
		speed.setMargin(new Insets(5,5,5,5));
		panel.add(speed);
		
		return panel;
	}
}
