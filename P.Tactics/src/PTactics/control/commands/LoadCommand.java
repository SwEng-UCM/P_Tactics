package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.control.History;
import PTactics.utils.Utils;

public class LoadCommand extends Command {

	private static final String NAME = Utils.CommandInfo.COMMAND_LOAD_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_LOAD_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_LOAD_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_LOAD_HELP;	
	private String path = "";
	
	public LoadCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		try {
			if(!path.isEmpty()) {
				History.getInstance(CI).setInFile(path);
			}
			History.getInstance(CI).load();
		}
		finally {
			path = "";
		}
	}

	@Override
	public Command parse(String[] sa) {
		if(sa.length == 1  && matchCommand(sa[0])) 
		{
			return this;
		}
		else if(sa.length == 2 && matchCommand(sa[0])) {
			path = sa[1];
			return this;
		}
		else return null;
	}

}