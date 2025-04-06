package launcher;

import PTactics.control.Controller;
import PTactics.control.ControllerConsole;
import PTactics.control.ControllerGUI;

public class Main {
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Controller control = args[0].equalsIgnoreCase("0") ? new ControllerConsole() : new ControllerGUI();
	}
}
