package PTactics.Commands;

import PTactics.Utils.Utils;

public class MoveCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_MOVE_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_MOVE_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_MOVE_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_MOVE_HELP;	
	private int _posX;
	private int _posY;
	
	public MoveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	protected void execute() {
		//TODO: Need to ask Tim what to do
	}

	@Override
	protected Command parse(String[] sa) {
		//Example: move 3 3 // m 3 3
		if(sa.length == 3  && matchCommand(sa[0])) 
		{
			try {
				_posX =Integer.valueOf(sa[1]);
				_posY =Integer.valueOf(sa[2]);
			} catch(NumberFormatException n) {
				//So it is not recognized as a command, maybe throw a Exception instead?
				return null;
			}
			return this;
		}
		else return null;
	}

}