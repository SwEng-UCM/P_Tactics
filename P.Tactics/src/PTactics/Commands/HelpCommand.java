package PTactics.Commands;

import PTactics.Game.ControllerInterface;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Utils;

public class HelpCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_HELP_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_HELP_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_HELP_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_HELP_HELP;	
	
	public HelpCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI, Troop _currTroop) {
		System.out.println(CommandGenerator.commandHelp());
	}

	@Override
	protected Command parse(String[] sa) {
		if(sa.length == 1  && matchCommand(sa[0])) 
		{
			return this;
		}
		else return null;
	}

}
