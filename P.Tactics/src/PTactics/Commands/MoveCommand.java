package PTactics.Commands;

import java.util.concurrent.TimeUnit;

import PTactics.Game.ControllerInterface;
import PTactics.GameObjects.Troop;
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
	public void execute(ControllerInterface CI, Troop _currTroop) {
		_currTroop.AddToMove(new Position(_posX,_posY));
		while(_currTroop.isAlive() && (!(_currTroop.getPos().getX() == _posX) || !(_currTroop.getPos().getY() == _posY)))
		{
			try {
				CI.update();
				CI.showGame();
			}
			catch(IllegalArgumentException e) { // this stops the updating of all gameObjects after the troop that throws the exception but no other way occurs to me without a big refactor
				System.out.println(e);
				break;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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