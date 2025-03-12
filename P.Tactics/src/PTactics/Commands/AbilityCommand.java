package PTactics.Commands;

import PTactics.Game.ControllerInterface;
import PTactics.GameObjects.SniperTroop;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

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

	@Override
	public void execute(ControllerInterface CI, Troop _currTroop) {
		if (_currTroop.getId() == Utils.TroopUtils.SNIPER_TROOP_ID) {
			SniperTroop st = (SniperTroop) _currTroop;
			st.activateAbility(new Position(_posX, _posY));
		}
		else {
			_currTroop.activateAbility();
		}
	}

	@Override
	protected Command parse(String[] sa) {
		if(sa.length == 3  && matchCommand(sa[0])) {
		try {
			_posY =Integer.valueOf(sa[1])-1;
			_posX =Integer.valueOf(sa[2])-1;
		} catch(NumberFormatException n) { return null;	}
		return this;
		} 
		return null;
	}
}