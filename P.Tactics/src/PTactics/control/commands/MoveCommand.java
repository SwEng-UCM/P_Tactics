package PTactics.control.commands;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import PTactics.control.ControllerInterface;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Position;
import PTactics.utils.Utils;

public class MoveCommand extends ReportCommand {
	private int _posX;
	private int _posY;
	private boolean snapTaken;

	public MoveCommand() {
		super(Utils.CommandInfo.COMMAND_MOVE_NAME, Utils.CommandInfo.COMMAND_MOVE_SHORTCUT, Utils.CommandInfo.COMMAND_MOVE_DETAILS, Utils.CommandInfo.COMMAND_MOVE_HELP);
	}

	public MoveCommand(int x, int y) // for GUI implementation
	{
		super(Utils.CommandInfo.COMMAND_MOVE_NAME, Utils.CommandInfo.COMMAND_MOVE_SHORTCUT, Utils.CommandInfo.COMMAND_MOVE_DETAILS, Utils.CommandInfo.COMMAND_MOVE_HELP);
		this._posX = x;
		this._posY = y;
		snapTaken = false;
	}
	
	@Override
	protected Snapshot getSnap(ControllerInterface CI) {
		return new MoveSnapshot(CI);
	}

	@Override
	public void execute(ControllerInterface CI) {
		if (CI.isTroopSelected()) {
			boolean movesLeft = true;
			Position pos = new Position(_posX, _posY);
			try {
				if(!snapTaken) {
					super.execute(CI);
					snapTaken = true;
				}
				CI.moveTroop(pos);
			} catch (IllegalArgumentException iae) {
				System.out.println(iae);
				movesLeft = false;
				super.eraseSnap(CI);
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
			snapTaken = false;
			return this;
		} else
			return null;
	}
	
	
	private class MoveSnapshot implements Snapshot {
		private String _commandId;
		private Position _initialPos;
		private Position _finalPos;
		private int _movesLeftBefore;
		private Troop _troopUsed;
		private ControllerInterface _ctrl;
		
		private MoveSnapshot(ControllerInterface CI) {
			_ctrl = CI;
			_commandId = getName();
			_troopUsed = CI.getGame().currentTroop();
			_initialPos = _troopUsed.getPos();
			_finalPos = new Position(_posX, _posY);
			_movesLeftBefore = _troopUsed.getMovesLeft();			
		}
		
		@Override
		public void restore() {
			_troopUsed.setPosition(_initialPos);
			if(!_troopUsed.isAlive()) { _troopUsed.revive(); }
			_troopUsed.setMovesLeft(_movesLeftBefore);
			_ctrl.updatePlayers();
		}
		
		@Override
		public void executeAgain() {
			_ctrl.selectTroop(_troopUsed);
			String[] s = {_commandId, String.valueOf(_finalPos.getX() +1), String.valueOf(_finalPos.getY()+1) };
			Command c = CommandGenerator.parse(s);
			c.execute(_ctrl);
			_ctrl.updatePlayers();
		}
	}
}