package PTactics.control.commands;

import java.util.Arrays;
import java.util.List;

public class CommandGenerator {
	//Add commands here
	private static List<Command> _commands = Arrays.asList(
	       new HelpCommand(),
	       new MoveCommand(),
	       new AimCommand(),
	       new ExitCommand(),
	       new SelectTroopCommand(),
	       new AbilityCommand(),
	       new SaveCommand(),
	       new LoadCommand(),
	       new UndoCommand(),
	       new RedoCommand()
	    );;
	
	CommandGenerator(){}
	
	public static Command parse(String[] argument) {
		Command cmd = null;
		for(Command c : _commands) {
			cmd = c.parse(argument);
			if(cmd != null) {
				return cmd;
			}
		}
		return null;
	}
	
	public static String commandHelp() {
		String s = "";
		for(Command c: _commands) {
			s += c.GetDetails() + " : " + c.GetHelp() + "\n";
		}
		return s;
	}
}
