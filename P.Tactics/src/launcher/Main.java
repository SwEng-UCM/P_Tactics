package launcher;

import PTactics.control.Controller;

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
