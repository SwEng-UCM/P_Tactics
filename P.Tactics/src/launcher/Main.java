package launcher;

import java.awt.EventQueue;

import PTactics.control.Controller;
import PTactics.view.GUI.GameWindow;

public class Main {
	
	public static void main(String[] args) {
		Controller control = new Controller(args[0]);
		if(args[0].equalsIgnoreCase("0")) 
		{
			control.runConsole();
		}
		else 
		{
			control.runGUI();
		}
	}
}
