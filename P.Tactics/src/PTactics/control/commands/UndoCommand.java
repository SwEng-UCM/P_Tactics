package PTactics.control.commands;

import java.util.Objects;

import PTactics.control.ControllerInterface;
import PTactics.control.Tracker;
import PTactics.utils.Utils;

public class UndoCommand extends Command {
	public UndoCommand() {
		super(Utils.CommandInfo.COMMAND_UNDO_NAME, Utils.CommandInfo.COMMAND_UNDO_SHORTCUT, Utils.CommandInfo.COMMAND_UNDO_DETAILS, Utils.CommandInfo.COMMAND_UNDO_HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		Snapshot s = Tracker.getTracker(CI).popUndo();
		if(!Objects.isNull(s)) {
			s.restore();
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
