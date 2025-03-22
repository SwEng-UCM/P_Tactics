package PTactics.view.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GameWindow {

	private JFrame gameWindowFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow();
					window.gameWindowFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		gameWindowFrame = new JFrame();
		gameWindowFrame.setTitle("P.Tactics");
		gameWindowFrame.setBounds(100, 100, 1243, 956);
		gameWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindowFrame.getContentPane().setLayout(null);
		
		GameInfoPanel GameInfo= new GameInfoPanel();
		GameInfo.setBounds(0, 0, 1227, 60);
		gameWindowFrame.getContentPane().add(GameInfo);
	}

}
