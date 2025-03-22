package PTactics.control.commands;

import java.util.InputMismatchException;
import PTactics.control.ControllerInterface;
import PTactics.utils.Position;
import PTactics.utils.Utils;

public class SelectTroopCommand extends Command {
	private static final String NAME = Utils.CommandInfo.COMMAND_SELECT_NAME;
	private static final String SHORTCUT = Utils.CommandInfo.COMMAND_SELECT_SHORTCUT;
	private static final String DETAILS = Utils.CommandInfo.COMMAND_SELECT_DETAILS;
	private static final String HELP = Utils.CommandInfo.COMMAND_SELECT_HELP;
	private int _posX;
	private int _posY;

	public SelectTroopCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(ControllerInterface CI) {
		try {
			// Get the coordinates of user
			Position pos = new Position(_posX, _posY);
			if (!pos.isValid())
				throw new Exception(Utils.MsgErrors.INVALID_COORDINATES);
			// Search if troop is on board and is from the player
			CI.selectTroop(pos);
		} catch (InputMismatchException inputError) {
			CI.showMessage(Utils.MsgErrors.INVALID_INPUT);
		} catch (ClassCastException wrongObject) {
			CI.showMessage(Utils.MsgErrors.INVALID_SELECTION);
		} catch (Exception wrongCoords) {
			CI.showMessage(wrongCoords.getMessage());
		}
	}

	@Override
	protected Command parse(String[] sa) {
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