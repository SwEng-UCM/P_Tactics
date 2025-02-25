package PTactics.Commands;

import PTactics.Game.ControllerInterface;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Utils;

public class SelectTroopCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_SELECT_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_SELECT_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_SELECT_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_SELECT_HELP;	
	
	public SelectTroopCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	//TODO: Do the selection here maybe.
	@Override
	public void execute(ControllerInterface CI, Troop t) {
		CI.selectSoldier();
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