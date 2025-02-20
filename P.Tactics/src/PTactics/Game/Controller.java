package PTactics.Game;

import java.util.InputMismatchException;
import java.util.Objects;
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
		while(this.isFinish()) {
			Troop t = this.selectSoldier();
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
	
	private boolean isFinish() {	//In principle, we do like player 0 turn --> check if player 1 has alive troops...
		for(Troop t : _currentGame.getPlayer().getTroops()) {
			if(t.isAlive()) return false;
		}
		return true;
	}
	
	private Troop selectSoldier() {		//Because select soldier is necessary, it will not be part of the commands, at least for now
		Scanner scanner = new Scanner(System.in);								//Setting the scanner
		int posX = 0; int posY = 0;

		while(true) {		//I am not afraid of consequences
			try {
			//Get the coordinates of user
			_currentGameView.showMessage(Utils.MessageUtils.ASK_SELECT_SOLDIER);
			posX = scanner.nextInt(); posY = scanner.nextInt();
			Position pos = new Position(posX,posY);
			if(posX < 0 || posX > Game._boardWidth - 1 || posY < 0 || posY > Game._boardLength) throw new Exception(Utils.MsgErrors.INVALID_COORDINATES);
			
			//Search if troop is on board and is from the player
			Troop g = (Troop) _currentGame.getBoard().getGameObject(pos);
			if (Objects.isNull(g)) throw new Exception(Utils.MsgErrors.INVALID_SELECTION);								//Have to check if it exists (is a GO)
			if (!g.isAlive()) throw new Exception(Utils.MsgErrors.INVALID_SELECTION);								    //Have to check if it is a troop alive (walls and dead troops will return false)
			if(!_currentGame.getPlayer().hasTroop(g)) throw new Exception(Utils.MsgErrors.INVALID_SELECTION);   		//Have to check that it belongs to the player (sorry for the casting)
			return g;
			} 
			catch(InputMismatchException inputError) {
				_currentGameView.showMessage(Utils.MsgErrors.INVALID_INPUT);
			}
			catch(ClassCastException wrongObject) {
				_currentGameView.showMessage(Utils.MsgErrors.INVALID_SELECTION);
			}
			catch(Exception wrongCoords) {
				_currentGameView.showMessage(wrongCoords.getMessage());
			}
		}
	}
}
