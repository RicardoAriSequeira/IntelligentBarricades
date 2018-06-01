package barricades;

import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;


public class GraphicalInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	
	static JTextField speed, iterations;
	static JPanel cityPanel;
	static JButton run, reset, step, train;
	static JLabel time;
	static JCheckBox display;

	public long startTime;
	public long totalTime;
	
	public GraphicalInterface(City city) {
		setTitle("Barricades");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(640, 600);
		add(createButtonPanel(city));
		cityPanel = new JPanel();
		cityPanel.setSize(new Dimension(600,500));
		cityPanel.setLocation(new Point(20,60));
		cityPanel.setLayout(new GridLayout(city.nX,city.nY));
		for(int i=0; i<city.nX; i++)
			for(int j=0; j<city.nY; j++)
				cityPanel.add(new JPanel());
		displayCity(city);
		city.GUI = this;
		add(cityPanel);
	}

	public void displayCity(City city) {

		int x, y;

		for(int i=0; i<city.nX; i++){

			for(int j=0; j<city.nY; j++){

				x = city.map.board[i][j].getCoordinates().y;
				y = city.map.board[i][j].getCoordinates().x;

				JPanel p = ((JPanel)cityPanel.getComponent(x*city.nY+y));

				if (city.map.board[i][j].isRoad()){

					p.setBackground(Color.white);

					if (city.map.board[i][j].isBarricade())
						p.setBorder(BorderFactory.createLineBorder(Color.red));

					else
						p.setBorder(BorderFactory.createLineBorder(Color.white));

				} else {
					p.setBackground(Color.black);
					p.setBorder(BorderFactory.createLineBorder(Color.black));
				}
				if (city.map.board[i][j].isGarage())
					p.setBackground(Color.yellow);

			}

		}

		cityPanel.invalidate();
	}
	
	public void removeCars(City city) {

		for(Civil civil : city.civils){
			JPanel p = ((JPanel)cityPanel.getComponent(civil.position.x+civil.position.y*city.nX));
			p.setBackground(Color.white);
			if (city.map.getCell(civil.position).isBarricade())
				p.setBorder(BorderFactory.createLineBorder(Color.red));
			else
				p.setBorder(BorderFactory.createLineBorder(Color.white));

		}

		for(Police police : city.polices){
			JPanel p = ((JPanel)cityPanel.getComponent(police.position.x+police.position.y*city.nX));
			p.setBackground(Color.white);
			if (city.map.getCell(police.position).isBarricade())
				p.setBorder(BorderFactory.createLineBorder(Color.red));
			else
				p.setBorder(BorderFactory.createLineBorder(Color.white));	
		}

		JPanel p = ((JPanel)cityPanel.getComponent(city.thief.position.x+city.thief.position.y*city.nX));
		p.setBackground(Color.white);
		if (city.map.getCell(city.thief.position).isBarricade())
				p.setBorder(BorderFactory.createLineBorder(Color.red));
			else
				p.setBorder(BorderFactory.createLineBorder(Color.white));		

		cityPanel.invalidate();
	}

	public void displayCars(City city) {

		for(Civil civil : city.civils){
			JPanel p = ((JPanel)cityPanel.getComponent(civil.position.x+civil.position.y*city.nX));
			p.setBackground(Color.gray);		
		}

		for(Police police : city.polices){
			JPanel p = ((JPanel)cityPanel.getComponent(police.position.x+police.position.y*city.nX));
			p.setBackground(Color.blue);
		}

		JPanel p = ((JPanel)cityPanel.getComponent(city.thief.position.x+city.thief.position.y*city.nX));
		p.setBackground(Color.red);

		cityPanel.invalidate();
	}

	public void updateClock() {
		/*
		totalTime = (System.currentTimeMillis() - startTime) / 1000;
		time.setText(totalTime +"s");
		*/
	}

	public void setTrainText(String s) {
		train.setText(s);
	}

	private Component createButtonPanel(City city) {

		JPanel panel = new JPanel();
		panel.setSize(new Dimension(600,50));
		panel.setLocation(new Point(0,0));

		/*
		
		step = new JButton("Step");
		panel.add(step);
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(run.getText().equals("Run")) city.step();
				else city.stopRun();
			}
		});
		reset = new JButton("Reset");
		panel.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				city.reset();
			}
		});

		*/

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
						startTime = System.currentTimeMillis();
						city.run(time);
	 					run.setText("Stop");						
					}
 				} else {
					city.stopRun();
 					run.setText("Run");
 				}
			}
		});
		train = new JButton("Train");
		panel.add(train);
		train.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(train.getText().equals("Train")){
					city.train(Integer.valueOf(iterations.getText()), display.isSelected());
 					train.setText("Stop");						
				} else {
					city.stopTrain();
 					train.setText("Train");
 				}
			}
		});

		display = new JCheckBox("Display");
		panel.add(display);

		panel.add(new JLabel("Train Iterations:"));
		iterations = new JTextField("1000");
		iterations.setMargin(new Insets(5,5,5,5));
		panel.add(iterations);

		panel.add(new JLabel("Run Step Time:"));
		speed = new JTextField("20");
		speed.setMargin(new Insets(5,5,5,5));
		panel.add(speed);

		/*
		panel.add(new JLabel("Run Total Time:"));
		time = new JLabel(0 + "s");
		panel.add(time);

		*/
		
		return panel;
	}
}
