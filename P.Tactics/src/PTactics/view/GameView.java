package PTactics.view;

import java.util.Scanner;

import PTactics.model.game.Game;
import PTactics.utils.Position;
import PTactics.utils.StringUtils;
import PTactics.utils.Utils;

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
	Scanner scanner;
	public GameView() {
		scanner = new Scanner(System.in);
	}
	
	private void newLine() {
		showMessage("");
	}
	private void showTop(Game _game) {
		
		System.out.print(StringUtils.repeat(_SPACE, _INITIAL_SPACE + 1));
		if (Game._boardLength >= 10) {
			for (int i = 0; i < Game._boardWidth; i++) {
				if (((i + 1) / 10) != 0) System.out.print(_CELL_SIZED_VALUE.formatted(((i + 1) / 10)));
				else System.out.print(StringUtils.repeat(_SPACE, _CELL_SIZE));
			}
		}
		newLine();
		System.out.print(StringUtils.repeat(_SPACE, _INITIAL_SPACE + 1));
		for (int i = 0; i < Game._boardWidth; i++) {
			System.out.print(_CELL_SIZED_VALUE.formatted((i + 1) % 10));
		}
		newLine();
		System.out.println(StringUtils.repeat(_SPACE, _INITIAL_SPACE) + UPPER_LEFT_CORNER + 
						 StringUtils.repeat(_HORIZONTAL_LINE, Game._boardWidth * _CELL_SIZE) + UPPER_RIGHT_CORNER);
	}
	
	private void showMiddle(Game _game) {
		for (int i = 0; i < Game._boardWidth; i++) {
			System.out.print(StringUtils.leftPad(i + 1, _INITIAL_SPACE) + _VERTICAL_LINE);
			for (int j = 0; j < Game._boardWidth; j++) {
				Position pos = new Position(j, i);
				System.out.print(StringUtils.toSize(_CELL_SIZED_VALUE.formatted(_game.positionToString(pos))));
			}
			System.out.println(_VERTICAL_LINE);
		}
	}
	
	private void showInfo(Game _game) {
		System.out.println("Current troop selected: " + (!_game.isTroopSelected() ? "none" : _game.getTroop().getId()) 
							+ (!_game.isTroopSelected() ? "" : (" In position:" + ( _game.getTroop().getPos().getY() + 1)
							+ " " + ( _game.getTroop().getPos().getX()+1))));
		System.out.println(!_game.isTroopSelected() ? "" : "Moves left: " +  _game.getTroop().getMovesLeft());
		System.out.println(!_game.isTroopSelected() ? "" : ! _game.getTroop().isAbility()? "" : "Ability turns left: " 
							+  _game.getTroop().abilityUsesLeft());
	}
	
	public void showGame(Game _game) {
		showInfo(_game);
		showTop(_game);
		showMiddle(_game);
		showBottom(_game);	
	}
	
	private void showBottom(Game _game) {
		System.out.println(StringUtils.repeat(_SPACE, _INITIAL_SPACE) + LOWER_LEFT_CORNER + 
				 StringUtils.repeat(_HORIZONTAL_LINE, Game._boardWidth * _CELL_SIZE) + LOWER_RIGHT_CORNER);
	}
	
	public void showMessage(String msg) {
		System.out.println(msg);
	}
	
	public void showError(String message) {
        System.out.println(Utils.MessageUtils.ERROR.formatted(message));		
	}
	
	public String[] getPrompt() {
		//Maybe should create a scanner in the class for more efficiency.
		System.out.print(Utils.MessageUtils.PROMPT);
		String line = scanner.nextLine();
		String[] words = line.trim().split("\\s+");

        if(words[0] != "") { System.out.println(Utils.MessageUtils.DEBUG.formatted(line));}
        
		return words;
	}
	
	public int getInt() {
		System.out.print(Utils.MessageUtils.DATA);
		int line = scanner.nextInt();
		return line;
	}
	
	//Just flush buffer
	public void get() {
		scanner.nextLine();
	}
}
