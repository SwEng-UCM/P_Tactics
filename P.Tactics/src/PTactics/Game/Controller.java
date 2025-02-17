package PTactics.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;
import PTactics.View.GameView;

public class Controller {
	private Game _currentGame;
	private GameView _currentGameView;
	
	public Controller() {	//Should fix this
		this._currentGame = new Game();
		this._currentGameView = new GameView();
	}
	
	//Showing for tomorrow
	public static void main(String[] args) {
		Game game = new Game();
		game.addPlayer(new Player("01"));
		Troop t = new Troop(new Position(1, 1), game.getBoard());
		game.addTroops(t); //also adds the troop to the real board
		GameView view = new GameView();
		boolean ok = true;
		String moveStr = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (ok) { // this is an awfull hack to read the move input
			game.update();
			view.showGame(game);			
			view.showMessage("Choose where to move the troop: ");
			try {
				moveStr = reader.readLine();
			} catch (IOException e) {}
			String[] coords = moveStr.trim().split(" ");
			// this next line is my greatest shame
			//game.getGameObject(new Position(Integer.parseInt(coords[0]) - 1, Integer.parseInt(coords[1]) - 1)).CalcNewMove(new Position(Integer.parseInt(coords[2]) - 1, Integer.parseInt(coords[3]) - 1));
			if (coords[0].equals(coords[1]) && coords[0].equals(coords[2]) && coords[0].equals(coords[3]));
				ok = false;
		}
	}
	
	/*
	public void run() {
		_currentGameView.showWelcomeMessage();
		//We give the troops to each player (for this sprint jusjt give troops)
		while(true) {
			//Select soldier 
			//Move soldier
			//Attack/Aim
			//Special action
		}
	}
	*/
}
