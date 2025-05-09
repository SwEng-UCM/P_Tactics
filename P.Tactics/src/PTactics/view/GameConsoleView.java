package PTactics.view;

import java.util.Scanner;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Game;
import PTactics.utils.Position;
import PTactics.utils.StringUtils;
import PTactics.utils.Utils;

public class GameConsoleView implements GameObserver {
	private final String _SPACE = " ";
	private final String _CELL_SIZED_VALUE = " %s "; // TODO: make it change with the _CELL_SIZE attribute
	private final String _VERTICAL_LINE = "┃";
	private final String _HORIZONTAL_LINE = "━";
	private static final String UPPER_LEFT_CORNER = "┌";
	private static final String UPPER_RIGHT_CORNER = "┐";
	private static final String LOWER_LEFT_CORNER = "└";
	private static final String LOWER_RIGHT_CORNER = "┘";
	private final int _CELL_SIZE = 3;
	private final int _INITIAL_SPACE = 3;
	Scanner scanner;

	// current thread to interrupt console input waiting
	public GameConsoleView(ControllerInterface CI) {
		scanner = new Scanner(System.in);
		CI.addObserver(this);
	}

	private void newLine() {
		showMessage("");
	}

	private void showTop(Game _game) {

		System.out.print(StringUtils.repeat(_SPACE, _INITIAL_SPACE + 1));
		if (Game._boardLength >= 10) {
			for (int i = 0; i < Game._boardWidth; i++) {
				if (((i + 1) / 10) != 0)
					System.out.print(_CELL_SIZED_VALUE.formatted(((i + 1) / 10)));
				else
					System.out.print(StringUtils.repeat(_SPACE, _CELL_SIZE));
			}
		}
		newLine();
		System.out.print(StringUtils.repeat(_SPACE, _INITIAL_SPACE + 1));
		for (int i = 0; i < Game._boardWidth; i++) {
			System.out.print(_CELL_SIZED_VALUE.formatted((i + 1) % 10));
		}
		newLine();
		System.out.println(StringUtils.repeat(_SPACE, _INITIAL_SPACE) + UPPER_LEFT_CORNER
				+ StringUtils.repeat(_HORIZONTAL_LINE, Game._boardWidth * _CELL_SIZE) + UPPER_RIGHT_CORNER);
	}

	private void showMiddle(Game _game) {
		for (int i = 0; i < Game._boardLength; i++) {
			System.out.print(StringUtils.leftPad(i + 1, _INITIAL_SPACE) + _VERTICAL_LINE);
			for (int j = 0; j < Game._boardWidth; j++) {
				Position pos = new Position(j, i);
				System.out.print(StringUtils.toSize(_CELL_SIZED_VALUE.formatted(_game.positionToString(pos))));
			}
			System.out.println(_VERTICAL_LINE);
		}
	}

	private void _cleanConsole() {
		// System.out.print("\033[H\033[2J");
		// System.out.flush();
		for (int i = 0; i < 50; ++i)
			System.out.println(" ");
	}

	private void _waitForEnter() {
		get();
	}

	public void showStartOfTurn(ControllerInterface ctrl, Game game) {
		_cleanConsole();
		showMessage("Player " + ctrl.getNumPlayer() + ": " + Utils.MessageUtils.START_TURN);
		_waitForEnter(); // First one as a cin.get()
		_waitForEnter(); // Second, to receive the enter key from user
		showMessage("Player " + ctrl.getNumPlayer() + ": ");
		showGame(game);
	}

	private void showInfo(Game _game) {
		System.out.println(
				"Current troop selected: " + (!_game.isTroopSelected() ? "none" : _game.getCurrentTroop().getId())
						+ (!_game.isTroopSelected() ? ""
								: (" In position:" + (_game.getCurrentTroop().getPos().getY() + 1) + " "
										+ (_game.getCurrentTroop().getPos().getX() + 1))));
		System.out.println(!_game.isTroopSelected() ? "" : "Moves left: " + _game.getCurrentTroop().getMovesLeft());
		System.out.println(!_game.isTroopSelected() ? ""
				: !_game.getCurrentTroop().isAbility() ? ""
						: "Ability turns left: " + _game.getCurrentTroop().abilityUsesLeft());
	}

	public void showGame(Game _game) {
		showInfo(_game);
		showTop(_game);
		showMiddle(_game);
		showBottom(_game);
	}

	private void showBottom(Game _game) {
		System.out.println(StringUtils.repeat(_SPACE, _INITIAL_SPACE) + LOWER_LEFT_CORNER
				+ StringUtils.repeat(_HORIZONTAL_LINE, Game._boardWidth * _CELL_SIZE) + LOWER_RIGHT_CORNER);
	}

	public void showMessage(String msg) {
		System.out.println(msg);
	}

	public void showError(String message) {
		System.out.println(Utils.MessageUtils.ERROR.formatted(message));
	}

	public String[] getPrompt() {
		// Maybe should create a scanner in the class for more efficiency.
		System.out.print(Utils.MessageUtils.PROMPT);
		String line = scanner.nextLine();
		String[] words = line.trim().split("\\s+");

		if (words[0] != "") {
			System.out.println(Utils.MessageUtils.DEBUG.formatted(line));
		}

		return words;
	}

	public int getInt() {

		System.out.print(Utils.MessageUtils.DATA);
		int line = scanner.nextInt();
		return line;
	}

	Thread getPlayerNumberThread = new Thread(() -> {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Data > ");
			@SuppressWarnings("unused")
			int input = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Console input interrupted.");
		}
	});

	// Just flush buffer
	public void get() {
		scanner.nextLine();
	}

	@Override
	public void onPlayersUpdate(Game game) {
		showGame(game);
	}

	@Override
	public void onBoardUpdate(Game game) {
		showGame(game);
	}

	@Override
	public void onTroopAction(Game game) {
		// showGame(game);
	}

	@Override
	public void onTroopSelection(Game game) {
		showGame(game);
	}

	@Override
	public void onNextTurn(Game game) {
		showGame(game);
	}

	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub

	}
}
