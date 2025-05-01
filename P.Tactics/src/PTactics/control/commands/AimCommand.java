package PTactics.control.commands;

import java.util.zip.DataFormatException;

import PTactics.control.ControllerInterface;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Utils;

public class AimCommand extends ReportCommand {
	private Direction _dirToAim;
	public AimCommand() {
		super(Utils.CommandInfo.COMMAND_AIM_NAME, Utils.CommandInfo.COMMAND_AIM_SHORTCUT, Utils.CommandInfo.COMMAND_AIM_DETAILS, Utils.CommandInfo.COMMAND_AIM_HELP);
	}
	public AimCommand(Direction dir) {
		super(Utils.CommandInfo.COMMAND_AIM_NAME, Utils.CommandInfo.COMMAND_AIM_SHORTCUT, Utils.CommandInfo.COMMAND_AIM_DETAILS, Utils.CommandInfo.COMMAND_AIM_HELP);
		_dirToAim = dir;
	}

	@Override
	public void execute(ControllerInterface CI) {
		if(CI.isTroopSelected()) {
			super.execute(CI);
			CI.takeAim(_dirToAim);
			CI.updatePlayers();
		}
		else {
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
	
	@Override
	protected Snapshot getSnap(ControllerInterface CI) {
		return new AimSnapshot(CI);
	}

	private class AimSnapshot implements Snapshot {
		private String _commandId;
		private Direction _initialDir;
		private Direction _finalDir;
		private Troop _troopUsed;
		private ControllerInterface _ctrl;
		
		private AimSnapshot(ControllerInterface CI) {
			_ctrl = CI;
			_commandId = getName();
			_initialDir = CI.getGame().getCurrentTroop().getDir();
			_finalDir = _dirToAim;
			_troopUsed = CI.getGame().getCurrentTroop();
		}
		
		@Override
		public void restore() {
			_troopUsed.takeAim(_initialDir);
			_ctrl.updatePlayers();
		}

		@Override
		public void executeAgain() {
			_ctrl.selectTroop(_troopUsed);
			String[] s = {_commandId, _finalDir.toString() };
			Command c = CommandGenerator.parse(s);
			c.execute(_ctrl);
//			_ctrl.updatePlayers();
		}
		
	}
}

