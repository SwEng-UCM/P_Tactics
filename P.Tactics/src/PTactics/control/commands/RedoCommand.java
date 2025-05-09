package PTactics.control.commands;

import java.util.Objects;

import PTactics.control.ControllerInterface;
import PTactics.control.Tracker;
import PTactics.utils.Utils;

public class RedoCommand extends Command {

	public RedoCommand() {
		super(Utils.CommandInfo.COMMAND_REDO_NAME, Utils.CommandInfo.COMMAND_REDO_SHORTCUT,
				Utils.CommandInfo.COMMAND_REDO_DETAILS, Utils.CommandInfo.COMMAND_REDO_HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		Snapshot s = Tracker.getTracker(CI).popRedo();
		if (!Objects.isNull(s)) {
			s.executeAgain();
		}
	}

	@Override
	public Command parse(String[] sa) {
		if (sa.length == 1 && matchCommand(sa[0])) {
			return this;
		} else
			return null;
	}

}