package PTactics.Game;

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
		Troop t = new Troop(new Position(2, 2));
		game.getPlayer().addTroops(t);
		game.addNewElement(t, t.getPos());
		GameView view = new GameView();
		view.showGame(game);
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
