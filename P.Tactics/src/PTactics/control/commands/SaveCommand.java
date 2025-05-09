package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.control.History;
import PTactics.utils.Utils;

public class SaveCommand extends Command {
	private String path = "";

	public SaveCommand() {
		super(Utils.CommandInfo.COMMAND_SAVE_NAME, Utils.CommandInfo.COMMAND_SAVE_SHORTCUT,
				Utils.CommandInfo.COMMAND_SAVE_DETAILS, Utils.CommandInfo.COMMAND_SAVE_HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		if (!path.isEmpty()) {
			StringBuilder fileName = new StringBuilder();
			fileName.append("/JSONgame");
			for (String s : CI.getPlayerNames()) {
				fileName.append(s);
			}
			fileName.append(".json");
			History.getInstance(CI).setOutFile(path, fileName.toString());
		}

		History.getInstance(CI).save();
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