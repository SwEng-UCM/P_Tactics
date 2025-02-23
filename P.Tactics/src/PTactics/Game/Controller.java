package PTactics.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import PTactics.GameObjects.GameObject;
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
		String moveStr = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		game.update();
		view.showGame(game);			
		while (true) { // this is an awfull hack to read the move input
			view.showMessage("Choose where to move the troop: ");
			try {
				moveStr = reader.readLine();
			} catch (IOException e) {}
			String[] coords = moveStr.trim().split(" ");
			GameObject movable=game.getGameObject(new Position(Integer.parseInt(coords[0]) - 1, Integer.parseInt(coords[1]) - 1));
			movable.AddToMove(new Position(Integer.parseInt(coords[2]) - 1, Integer.parseInt(coords[3]) - 1));
			while(!(movable.getPos().X == (Integer.parseInt(coords[2]) - 1)) || !(movable.getPos().Y == (Integer.parseInt(coords[3]) - 1))) 
			{
				
				game.update();
				view.showGame(game);
			}
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
