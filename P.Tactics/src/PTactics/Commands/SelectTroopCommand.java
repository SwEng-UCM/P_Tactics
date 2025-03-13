package PTactics.Commands;

import java.util.InputMismatchException;
import java.util.Objects;

import PTactics.Game.ControllerInterface;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

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

	// TODO: Do the selection here maybe.
	@Override
	public void execute(ControllerInterface CI, Troop t) {
		try {
			// Get the coordinates of user
			Position pos = new Position(_posX, _posY);
			if (!pos.isValid())
				throw new Exception(Utils.MsgErrors.INVALID_COORDINATES);

			// Search if troop is on board and is from the player
			Troop g = (Troop) CI.getGameObject(pos);
			Integer i = CI.getNumPlayer();
			if (Objects.isNull(g)) {
				throw new Exception(Utils.MsgErrors.INVALID_SELECTION); // Have to check if it exists (is a GO)
			}
			if (!g.isAlive()) {
				throw new Exception(Utils.MsgErrors.INVALID_SELECTION); // Have to check if it is a troop alive (walls
																		// and dead troops will return false)
			}
			if (!g.getPlayer().equals(i.toString())) {
				throw new Exception(Utils.MsgErrors.INVALID_SELECTION); // Have to check that it belongs to the player
																		// (sorry for the casting)
			}
			CI.setTroop(g);

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