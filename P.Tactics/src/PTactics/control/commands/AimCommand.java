package PTactics.control.commands;

import java.util.zip.DataFormatException;

import PTactics.control.ControllerInterface;
import PTactics.utils.Direction;
import PTactics.utils.Utils;

public class AimCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_AIM_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_AIM_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_AIM_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_AIM_HELP;	
	private Direction _dirToAim;
	public AimCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	public AimCommand(Direction dir) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		_dirToAim = dir;
	}

	@Override
	public void execute(ControllerInterface CI) {
		try {
			CI.takeAim(_dirToAim);
			CI.updatePlayers();
			}
		catch(NullPointerException e) {
			System.out.println(Utils.MsgErrors.UNSELECTED_TROOP);
		}
	}

	@Override
	public Command parse(String[] sa) {
		//Example: aim left // a left
				if(sa.length == 2  && matchCommand(sa[0])) 
				{
					try {
						_dirToAim = Direction.toDir(sa[1].toUpperCase());
						if(_dirToAim == Direction.NONE) throw new DataFormatException();
					} catch(DataFormatException n) {
						return null;
					}
					return this;
				}
				else return null;
	}

}
