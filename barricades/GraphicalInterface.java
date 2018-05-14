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
	static JPanel cityPanel;
	static JButton run, reset, step;
	
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
		displayCars(city);
		city.GUI = this;
		add(cityPanel);
	}

	public void displayCity(City city) {
		for(int i=0; i<city.nX; i++){
			for(int j=0; j<city.nY; j++){ 
				JPanel p = ((JPanel)cityPanel.getComponent(i*city.nY+j));
				p.setBackground(new Color(0,0,0));
				p.setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}

		for(int i=0; i<city.nX; i++){
			for(int j=0; j<city.nY; j++){
				if (city.map.board[i][j].isRoad()){
					JPanel p = ((JPanel)cityPanel.getComponent(i*city.nY+j));
					p.setBackground(new Color(255,255,255));
				}
			}
		}

		cityPanel.invalidate();
	}
	
	public void removeCars(City city) {
		for(Car car : city.Cars){
			JPanel p = ((JPanel)cityPanel.getComponent(car.position.x+car.position.y*city.nX));
			p.setBorder(BorderFactory.createLineBorder(Color.white));			
		}
		cityPanel.invalidate();
	}

	public void displayCars(City city) {
		for(Car car : city.Cars){
			JPanel p = ((JPanel)cityPanel.getComponent(car.position.x+car.position.y*city.nX));
			p.setBorder(BorderFactory.createLineBorder(Color.red,3));			
		}
		cityPanel.invalidate();
	}

	private Component createButtonPanel(City city) {
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(600,50));
		panel.setLocation(new Point(0,0));
		
		step = new JButton("Step");
		panel.add(step);
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(run.getText().equals("Run")) city.step();
				else city.stop();
			}
		});
		reset = new JButton("Reset");
		panel.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				city.reset();
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
						city.run(time);
	 					run.setText("Stop");						
					}
 				} else {
					city.stop();
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
