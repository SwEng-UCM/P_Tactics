package PTactics.Game;

import PTactics.View.GameView;

public class Controller {
	private Game _currentGame;
	private GameView _currentGameView;
	
	public Controller() {
		this._currentGame = new Game();
		this._currentGameView = new GameView();
	}
	
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
}
