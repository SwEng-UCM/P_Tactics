package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.control.Tracker;
import PTactics.utils.Utils;

public class UndoCommand extends Command {

	private static final String NAME = Utils.CommandInfo.COMMAND_UNDO_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_UNDO_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_UNDO_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_UNDO_HELP;	
	
	public UndoCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		Tracker.getInstance(CI).undo();
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
