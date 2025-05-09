package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.utils.Utils;

public class ExitCommand extends Command {

	public ExitCommand() {
		super(Utils.CommandInfo.COMMAND_EXIT_NAME, Utils.CommandInfo.COMMAND_EXIT_SHORTCUT,
				Utils.CommandInfo.COMMAND_EXIT_DETAILS, Utils.CommandInfo.COMMAND_EXIT_HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		CI.endTurn();
	}

	@Override
	public Command parse(String[] sa) {
		if (sa.length == 1 && matchCommand(sa[0])) {
			return this;
		} else
			return null;
	}

}