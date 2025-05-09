package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.control.History;
import PTactics.utils.Utils;

public class LoadCommand extends Command {
	private String path = "";

	public LoadCommand() {
		super(Utils.CommandInfo.COMMAND_LOAD_NAME, Utils.CommandInfo.COMMAND_LOAD_SHORTCUT,
				Utils.CommandInfo.COMMAND_LOAD_DETAILS, Utils.CommandInfo.COMMAND_LOAD_HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		try {
			if (!path.isEmpty()) {
				History.getInstance(CI).setInFile(path);
			}
			History.getInstance(CI).load();
		} finally {
			path = "";
		}
	}

	@Override
	public Command parse(String[] sa) {
		if (sa.length == 1 && matchCommand(sa[0])) {
			return this;
		} else if (sa.length == 2 && matchCommand(sa[0])) {
			path = sa[1];
			return this;
		} else
			return null;
	}

}