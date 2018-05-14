package barricades;

import java.awt.EventQueue;

/**
 * Multi-agent system creation
 * @author Rui Henriques
 */
public class Main {

	public static void main(String[] args) {
		City city = new City(32, 32);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicalInterface frame = new GraphicalInterface(city);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
