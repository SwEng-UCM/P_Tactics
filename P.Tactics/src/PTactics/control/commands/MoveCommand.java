package PTactics.control.commands;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import PTactics.control.ControllerInterface;
import PTactics.utils.Position;
import PTactics.utils.Utils;

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

	public MoveCommand(int x, int y) // for GUI implementation
	{
		super(NAME, SHORTCUT, DETAILS, HELP);
		this._posX = x;
		this._posY = y;
	}

	@Override
	public void execute(ControllerInterface CI) {
		if (CI.isTroopSelected()) {
			boolean movesLeft = true;
			Position pos = new Position(_posX, _posY);
			try {
				CI.moveTroop(pos);
			} catch (IllegalArgumentException iae) {
				System.out.println(iae);
				movesLeft = false;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (movesLeft && CI.canMove(pos)) {
				SwingUtilities.invokeLater(() -> execute(CI));				
			}
			else if (!CI.currTroop().isAlive()) {
				CI.setTroop(null);
			}
		} else {
			System.out.println("Select a troop before executing a troop command, current troop selection is none");
		}
	}

	@Override
	public Command parse(String[] sa) {
		// Example: move 3 3 // m 3 3
		if (sa.length == 3 && matchCommand(sa[0])) {
			try {
				_posY = Integer.valueOf(sa[1]) - 1;
				_posX = Integer.valueOf(sa[2]) - 1;
			} catch (NumberFormatException n) {
				return null;
			}
			return this;
		} else
			return null;
	}

}