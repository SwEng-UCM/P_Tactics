package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.utils.Utils;

public class LoadCommand extends Command {

	private static final String NAME = Utils.CommandInfo.COMMAND_LOAD_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_LOAD_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_LOAD_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_LOAD_HELP;	
	
	public LoadCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		//Tracker.getInstance(CI).load();
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