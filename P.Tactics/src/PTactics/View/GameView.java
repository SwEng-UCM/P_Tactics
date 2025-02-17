package PTactics.View;

import PTactics.Game.Game;
import PTactics.Game.Player;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;
import PTactics.Utils.StringUtils;

public class GameView {
	private final String _SPACE = " ";
	private final String _CELL_SIZED_VALUE = " %s "; //TODO: make it change with the _CELL_SIZE attribute
	private final String _VERTICAL_LINE = "┃";
	private final String _HORIZONTAL_LINE = "━";
	private static final String UPPER_LEFT_CORNER = "┌";
	private static final String UPPER_RIGHT_CORNER = "┐";
	private static final String LOWER_LEFT_CORNER = "└";
	private static final String LOWER_RIGHT_CORNER = "┘";
	private final int _CELL_SIZE = 3;
	private final int _INITIAL_SPACE = 3;
	
	public GameView() {
	}
	
	private void newLine() {
		showMessage("");
	}
	private void showTop(Game _game) {
		
		System.out.print(StringUtils.repeat(_SPACE, _INITIAL_SPACE + 1));
		if (_game.getWidth() >= 10) {
			for (int i = 0; i < _game.getWidth(); i++) {
				if (((i + 1) / 10) != 0) System.out.print(_CELL_SIZED_VALUE.formatted(((i + 1) / 10)));
				else System.out.print(StringUtils.repeat(_SPACE, _CELL_SIZE));
			}
		}
		newLine();
		System.out.print(StringUtils.repeat(_SPACE, _INITIAL_SPACE + 1));
		for (int i = 0; i < _game.getWidth(); i++) {
			System.out.print(_CELL_SIZED_VALUE.formatted((i + 1) % 10));
		}
		newLine();
		System.out.println(StringUtils.repeat(_SPACE, _INITIAL_SPACE) + UPPER_LEFT_CORNER + 
						 StringUtils.repeat(_HORIZONTAL_LINE, _game.getWidth() * _CELL_SIZE) + UPPER_RIGHT_CORNER);
	}
	
	private void showMiddle(Game _game) {
		for (int i = 0; i < _game.getLength(); i++) {
			System.out.print(StringUtils.leftPad(i + 1, _INITIAL_SPACE) + _VERTICAL_LINE);
			for (int j = 0; j < _game.getWidth(); j++) {
				Position pos = new Position(j, i);
				System.out.print(_CELL_SIZED_VALUE.formatted(_game.positionToString(pos)));
			}
			System.out.println(_VERTICAL_LINE);
		}
	}
	
	public void showGame(Game _game) {
		showTop(_game);
		showMiddle(_game);
		showBottom(_game);	
	}
	
	private void showBottom(Game _game) {
		System.out.println(StringUtils.repeat(_SPACE, _INITIAL_SPACE) + LOWER_LEFT_CORNER + 
				 StringUtils.repeat(_HORIZONTAL_LINE, _game.getWidth() * _CELL_SIZE) + LOWER_RIGHT_CORNER);
	}
	
	public void showEndMessage() {
		
	}
	
	public void showWelcomeMessage() {
		
	}
	
	public void showMessage(String msg) {
		//TODO: Format this message.
		System.out.println(msg);
	}
}
