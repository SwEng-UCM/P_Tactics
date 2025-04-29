package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.utils.Utils;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super(Utils.CommandInfo.COMMAND_HELP_NAME, Utils.CommandInfo.COMMAND_HELP_SHORTCUT, Utils.CommandInfo.COMMAND_HELP_DETAILS, Utils.CommandInfo.COMMAND_HELP_HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		System.out.println(CommandGenerator.commandHelp());
	}

	@Override
	public Command parse(String[] sa) {
		if(sa.length == 1  && matchCommand(sa[0])) 
		{
			return this;
		}
		else return null;
	}

}
