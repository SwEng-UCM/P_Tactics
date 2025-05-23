package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Position;
import PTactics.utils.Utils;

public class AbilityCommand extends ReportCommand {
	private int _posX;
	private int _posY;

	public AbilityCommand() {
		super(Utils.CommandInfo.COMMAND_ABILITY_NAME, Utils.CommandInfo.COMMAND_ABILITY_SHORTCUT,
				Utils.CommandInfo.COMMAND_ABILITY_DETAILS, Utils.CommandInfo.COMMAND_ABILITY_HELP);
	}

	public AbilityCommand(int x, int y) {
		super(Utils.CommandInfo.COMMAND_ABILITY_NAME, Utils.CommandInfo.COMMAND_ABILITY_SHORTCUT,
				Utils.CommandInfo.COMMAND_ABILITY_DETAILS, Utils.CommandInfo.COMMAND_ABILITY_HELP);
		_posX = x;
		_posY = y;
	}

	@Override
	public void execute(ControllerInterface CI) {
		try {
			CI.troopAbility(new Position(_posX, _posY));
			CI.update();
			super.execute(CI);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Command parse(String[] sa) {
		if (sa.length == 3 && matchCommand(sa[0])) {
			try {
				_posY = Integer.valueOf(sa[1]) - 1;
				_posX = Integer.valueOf(sa[2]) - 1;
			} catch (NumberFormatException n) {
				return null;
			}
			return this;
		} else if (sa.length == 1 && matchCommand(sa[0])) {
			_posX = -1;
			_posY = -1;
			return this;
		}
		return null;
	}

	@Override
	protected Snapshot getSnap(ControllerInterface CI) {
		return new AbilitySnapshot(CI);
	}

	private class AbilitySnapshot implements Snapshot {
		private String _commandId;
		private Position _abilityPos;
		private Troop _troopUsed;
		private ControllerInterface _ctrl;

		private AbilitySnapshot(ControllerInterface CI) {
			_ctrl = CI;
			_commandId = getName();
			_abilityPos = new Position(_posX, _posY);
			_troopUsed = CI.getCurrentTroop();
		}

		@Override
		public void restore() {
			_troopUsed.undoAbility(_abilityPos);
			_ctrl.update();
		}

		@Override
		public void executeAgain() {
			_ctrl.selectTroop(_troopUsed);
			String[] s = { _commandId, String.valueOf(_abilityPos.getY() + 1), String.valueOf(_abilityPos.getX() + 1) };
			Command c = CommandGenerator.parse(s);
			c.execute(_ctrl);
//			_ctrl.updatePlayers();
		}
	}
}