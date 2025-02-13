package PTactics.View;

import PTactics.Game.Game;
import PTactics.Utils.StringUtils;

public class GameView {
	private final String _SPACE = " ";
	private final String _UPPER_COORD = " %s ";
	private final String _VERTICAL_LINE = "┃";
	private final String _HORIZONTAL_LINE = "━";
	private final int _CELL_SIZE = 3;
	private final int _INITIAL_SPACE = 3;
	
	private Game game;
	
	public GameView() {
		game = new Game(30, 30);
	}
	
	private void newLine() {
		showMessage("");
	}
	private void showTop() {
		
		System.out.print(StringUtils.repeat(_SPACE, _INITIAL_SPACE));
		for (int i = 0; i < game.getWidth(); i++) {
			System.out.print(_UPPER_COORD.formatted(i + 1));
		}
		newLine();
		System.out.print(StringUtils.repeat(_SPACE, _INITIAL_SPACE) + StringUtils.repeat(_HORIZONTAL_LINE, game.getWidth() * _CELL_SIZE));
	}
	
	public void showGame() {
		//game.posToString();
	}
	
	public void showEndMessage() {
		
	}
	
	public void showWelcomeMessage() {
		
	}
	
	public void showMessage(String msg) {
		//TODO: Format this message.
		System.out.println(msg);
	}
	
	public static void main(String[] args) {
		GameView view = new GameView();
		view.showTop();
	}
}
