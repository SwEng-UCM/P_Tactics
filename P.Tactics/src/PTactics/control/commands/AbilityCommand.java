package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.utils.Position;
import PTactics.utils.Utils;

public class AbilityCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_ABILITY_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_ABILITY_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_ABILITY_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_ABILITY_HELP;
	private int _posX;
	private int _posY;
	public AbilityCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	public AbilityCommand(int x, int y) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		_posX = x;
		_posY = y; 
	}

	@Override
	public void execute(ControllerInterface CI) {
		try {
			CI.troopAbility(new Position(_posX, _posY));
			CI.update();
		} catch (Exception e) {
			e.printStackTrace();;
		}
	}

	@Override
	public Command parse(String[] sa) {
		if(sa.length == 3  && matchCommand(sa[0])) {
			try {
				_posY =Integer.valueOf(sa[1])-1;
				_posX =Integer.valueOf(sa[2])-1;
			} catch(NumberFormatException n) { 
				return null;	    
				}
			return this;
		} 
		else if(sa.length == 1) {
			_posX = -1;
			_posY = -1;
			return this;
		}
		return null;
	}
}