package launcher;

import PTactics.control.Controller;

public class Main {
	
	public static void main(String[] args) {
		Controller control = new Controller("1");
		/*if(args[0]=="0") 
>>>>>>> Stashed changes
		{
			control.runConsole();
		}
		else 
		{
			control.runGUI();
		}*/
		control.runGUI();
	}
}
