package launcher;

import javax.swing.JOptionPane;

import PTactics.control.ControllerConsole;
import PTactics.control.ControllerGUI;

public class Main {
	
	public static String[] startOptions = {"Console Mode","GUI Mode"};
	
	public static void main(String[] args) {
		int answer = JOptionPane.showOptionDialog(
				null, 
				"Do you want to play on console mode or GUI mode?", 
				"Start game option", 
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
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
