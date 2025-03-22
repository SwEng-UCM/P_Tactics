package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.utils.Utils;

public class ExitCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_EXIT_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_EXIT_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_EXIT_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_EXIT_HELP;	
	
	public ExitCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		CI.endTurn();
	}

	@Override
	protected Command parse(String[] sa) {
		if(sa.length == 1  && matchCommand(sa[0])) 
		{
			return this;
		}
		else return null;
	}

}