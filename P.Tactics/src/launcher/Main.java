package launcher;

import java.awt.EventQueue;

import PTactics.control.Controller;
import PTactics.view.GUI.GameWindow;

public class Main {
	
	public static void main(String[] args) {
		Controller control = new Controller();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow(control);
					window.GetGameWindow().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		control.run();
	}
}
