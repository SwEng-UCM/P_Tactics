package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.control.History;
import PTactics.utils.Utils;

public class SaveCommand extends Command {

	private static final String NAME = Utils.CommandInfo.COMMAND_SAVE_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_SAVE_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_SAVE_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_SAVE_HELP;	
	private String path = "";
	
	public SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		if(!path.isEmpty()) {
			StringBuilder fileName = new StringBuilder();
			fileName.append("/JSONgame");  
			for(String s : CI.getPlayerNames()) {
				fileName.append(s);
			}
			fileName.append(".json");
			History.getInstance(CI).setOutFile(path, fileName.toString());
		}
		
		History.getInstance(CI).save();
		System.exit(0);
	}

	@Override
	public Command parse(String[] sa) {
		if(sa.length == 1  && matchCommand(sa[0])) {
			return this;
		}
		else if(sa.length == 2 && matchCommand(sa[0])) {
			path = sa[1];
			return this;
		}
		else return null;
	}

}