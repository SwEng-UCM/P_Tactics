package PTactics.control.Commands;

import java.util.concurrent.TimeUnit;

import PTactics.control.ControllerInterface;
import PTactics.Utils.Position;
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
	public void execute(ControllerInterface CI) {
		if(CI.isTroopSelected()) 
		{
			Position pos = new Position(_posX, _posY);
			while (CI.canMove(pos)) {
				try {
					CI.moveTroop(pos);
				} catch (IllegalArgumentException iae) {
					iae.printStackTrace();
					break;
				}
				CI.showGame();				
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else
		{
			System.out.println("Select a troop before executing a troop command, current troop selection is none");
		}
	}

	@Override
	protected Command parse(String[] sa) {
		//Example: move 3 3 // m 3 3
		if(sa.length == 3  && matchCommand(sa[0])) 
		{
			try {
				_posY =Integer.valueOf(sa[1])-1;
				_posX =Integer.valueOf(sa[2])-1;
			} catch(NumberFormatException n) {
				return null;
			}
			return this;
		}
		else return null;
	}

}