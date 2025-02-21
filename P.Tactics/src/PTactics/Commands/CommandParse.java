package PTactics.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandParse {
	private List<Command> _commands;
	
	CommandParse(){
		this._commands = new ArrayList<>();
		//Add commands in here
	}
	
	public Command parse(String argument) {
		Command cmd = null;
		for(Command c : _commands) {
			cmd = c.parse(argument);
			if(cmd != null) {
				return cmd;
			}
		}
		return null;
	}
}
