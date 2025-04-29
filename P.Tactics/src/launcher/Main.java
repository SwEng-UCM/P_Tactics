package launcher;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import PTactics.control.ControllerConsole;
import PTactics.control.ControllerGUI;
import PTactics.view.GUI.Icons;

public class Main {
	
	public static String[] startOptions = {"Console Mode","GUI Mode"};
	
	public static void main(String[] args) {
		ImageIcon icon = new ImageIcon(Icons.otherIcons.HOLDFIRE_ICON5.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH));
		int answer = JOptionPane.showOptionDialog(
				null, 
				"Do you want to play on console mode or GUI mode?", 
				"Start game option", 
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				icon, 
				startOptions, 
				null);
		
		if(answer == 0) {
			new ControllerConsole();
		}
		else if(answer == 1){
			new ControllerGUI();
		}
	}
}
