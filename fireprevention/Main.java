package fireprevention;

import java.awt.EventQueue;

/**
 * Multi-agent system creation
 * @author Rui Henriques
 */
public class Main {

	public static void main(String[] args) {
		Board board = new Board(32,32,3);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicalInterface frame = new GraphicalInterface(board);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
