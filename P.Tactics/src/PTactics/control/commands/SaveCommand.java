package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.control.Tracker;
import PTactics.utils.Utils;

public class SaveCommand extends Command {

	private static final String NAME = Utils.CommandInfo.COMMAND_SAVE_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_SAVE_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_SAVE_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_SAVE_HELP;	
	
	public SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		Tracker.getInstance(CI).save();
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