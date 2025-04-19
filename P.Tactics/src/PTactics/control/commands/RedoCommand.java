package PTactics.control.commands;

import java.util.Objects;

import PTactics.control.ControllerInterface;
import PTactics.control.Tracker;
import PTactics.utils.Utils;

public class RedoCommand extends Command {

	private static final String NAME = Utils.CommandInfo.COMMAND_REDO_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_REDO_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_REDO_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_REDO_HELP;	
	
	public RedoCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		Snapshot s = Tracker.getTracker(CI).popRedo();
		if(!Objects.isNull(s)) {
			s.executeAgain();
		}
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