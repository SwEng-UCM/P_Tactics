package PTactics.Game;

import java.util.InputMismatchException;
import java.util.Scanner;

import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;
import PTactics.View.GameView;

public class Controller {
	private Game _currentGame;
	private GameView _currentGameView;
	
	public Controller() {
		this._currentGameView = new GameView();
	}
	
	
	public void run() {
		this.setup();
		while(true) {
			//Select soldier 
			//Move soldier
			//Attack/Aim
			//Special action
		}
	}
	
	private void setup() {
		Scanner scanner = new Scanner(System.in);
		int numPlayers = 0;
		boolean correct = false;
		//TODO: Give them to decide between maps or randomizer
		this._currentGame = new Game();
		_currentGameView.showMessage(Utils.MessageUtils.WELCOME_MSG);
		_currentGameView.showMessage(Utils.MessageUtils.ASK_NUMBER_PLAYERS);
		
		while(!correct) {
			try {
				numPlayers = scanner.nextInt();
				if(numPlayers < 2 || numPlayers > 4) throw new Exception();
				correct = true;
			}
			catch (InputMismatchException inputError) {
				_currentGameView.showMessage(Utils.MsgErrors.INVALID_INPUT);
				scanner.nextLine(); 	//Clearing the buffer to avoid infinite loop!
				correct = false;
			} 
			catch (Exception e) {
				_currentGameView.showMessage(Utils.MsgErrors.INVALID_NUM_PLAYERS);
				correct = false;
			}
		}
		
		//TODO: Give troops to each player:
		for(Integer i = 0; i < numPlayers; ++i) {
			Player p = new Player(i.toString());
			for(int i1 = 0; i1 < Utils.Data.STARTING_SOLDIERS; ++i1) {					//TODO: This is just a demo
				Troop t = new Troop(new Position(i1,i1), _currentGame.getBoard());
				p.addTroops(t);															//Adding manually because addTroops() --> adds to current player and we do not want them
				_currentGame.addNewElement(t, t.getPos());
			}
			_currentGame.addPlayer(p);
		}
		
		scanner.close();
	}
	
}
