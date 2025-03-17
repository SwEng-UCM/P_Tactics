package PTactics.Commands;

import java.util.zip.DataFormatException;

import PTactics.Game.ControllerInterface;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Direction;
import PTactics.Utils.Utils;

public class AimCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_AIM_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_AIM_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_AIM_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_AIM_HELP;	
	private Direction _dirToAim;
	public AimCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI, Troop _currTroop) {
		try {
			_currTroop.takeAim(_dirToAim);
			CI.updatePlayers();
			}
		catch(NullPointerException e) {
			System.out.println(Utils.MsgErrors.UNSELECTED_TROOP);
		}
	}

	@Override
	protected Command parse(String[] sa) {
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
