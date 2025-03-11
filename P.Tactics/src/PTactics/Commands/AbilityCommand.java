package PTactics.Commands;

import PTactics.Game.ControllerInterface;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Utils;

public class AbilityCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_ABILITY_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_ABILITY_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_ABILITY_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_ABILITY_HELP;

	public AbilityCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI, Troop _currTroop) {
		// Execute ability t.ability();
	}

	@Override
	protected Command parse(String[] sa) {
		// Example: ability // b
		if (sa.length == 1 && matchCommand(sa[0])) {
			return this;
		} else
			return null;
	}

}