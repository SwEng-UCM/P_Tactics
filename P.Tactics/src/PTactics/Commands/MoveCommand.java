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
		while(!(_currTroop.getPos().X == _posX) || !(_currTroop.getPos().Y == _posY))
		{
			if(_currTroop.getMovesLeft() > 0) {
				CI.update();
				CI.showGame();
			}
			else {
				System.out.print("Troop has no moves left");
				break;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
				//So it is not recognized as a command, maybe throw a Exception instead?
				return null;
			}
			return this;
		}
		else return null;
	}

}