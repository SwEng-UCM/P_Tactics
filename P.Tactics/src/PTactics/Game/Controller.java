package PTactics.Game;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import PTactics.Commands.Command;
import PTactics.Commands.CommandGenerator;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;
import PTactics.View.GameView;

public class Controller implements ControllerInterface{
	private Game _currentGame;
	private GameView _currentGameView;
	private boolean _endTurn;
	private Troop _currTroop;
	
	public Controller() {
		this._currentGameView = new GameView();
	}
	
	@Override
	public void endTurn() {
		_endTurn = false;
	}
	
	public void run() {
		this.setup();
		while(!this.isFinish()) {
			startOfTurn();
			while(!_endTurn) {
				//_currentGame.update();
				String[] userCommand = _currentGameView.getPrompt();
				Command command = CommandGenerator.parse(userCommand);
				
				 if (command != null) { 
			        command.execute(this, _currTroop);	//TODO: need an interface to protect game, probably will receive troop t too
				 } else {
					 _currentGameView.showError(Utils.MsgErrors.UNKNOWN_COMMAND);
				 }
			}

		}
	}
	
	private void setup() {
		int numPlayers = 0;
		boolean correct = false;
		//TODO: Give them to decide between maps or randomizer
		this._currentGame = new Game();
		_currentGameView.showMessage(Utils.MessageUtils.WELCOME_MSG);
		_currentGameView.showMessage(Utils.MessageUtils.ASK_NUMBER_PLAYERS);
		
		while(!correct) {
			try {
				numPlayers = _currentGameView.getInt();
				if(numPlayers < 2 || numPlayers > 4) throw new Exception();
				correct = true;
			}
			catch (InputMismatchException inputError) {
				_currentGameView.showMessage(Utils.MsgErrors.INVALID_INPUT);
				_currentGameView.get();	//Clearing the buffer to avoid infinite loop!
				correct = false;
			} 
			catch (Exception e) {
				_currentGameView.showMessage(Utils.MsgErrors.INVALID_NUM_PLAYERS);
				correct = false;
			}
		}
		
		//TODO: Give troops to each player:
		for(Integer i = 1; i <= numPlayers; ++i) {
			Player p = new Player(i.toString());
			for(int i1 = 0; i1 < Utils.Data.STARTING_SOLDIERS; ++i1) {					//TODO: This is just a demo
				//Troop t = new Troop(new Position(i1,i1), _currentGame.getBoard());
				//p.addTroops(t);															//Adding manually because addTroops() --> adds to current player and we do not want them
				//_currentGame.addNewElement(t, t.getPos());
				if(i == 1) {
					Troop t1 = new Troop(new Position(3,2), _currentGame.getBoard());
					p.addTroops(t1);
					_currentGame.addNewElement(t1, t1.getPos());
					
					Troop t2 = new Troop(new Position(3,3), _currentGame.getBoard());
					p.addTroops(t2);
					_currentGame.addNewElement(t2, t2.getPos());
					
					Troop t3 = new Troop(new Position(3,4), _currentGame.getBoard());
					p.addTroops(t3);
					_currentGame.addNewElement(t3, t3.getPos());
				}
				else if (i == 2) {
					Troop t1 = new Troop(new Position(2,8), _currentGame.getBoard());
					p.addTroops(t1);
					_currentGame.addNewElement(t1, t1.getPos());
					
					Troop t2 = new Troop(new Position(6,9), _currentGame.getBoard());
					p.addTroops(t2);
					_currentGame.addNewElement(t2, t2.getPos());
					
					Troop t3 = new Troop(new Position(9,10), _currentGame.getBoard());
					p.addTroops(t3);
					_currentGame.addNewElement(t3, t3.getPos());
				}
			}
			_currentGame.addPlayer(p);
		}
	}
	
	private boolean isFinish() {	//In principle, we do like player 0 turn --> check if player 1 has alive troops...
		for(Troop t : _currentGame.getPlayer().getTroops()) {
			if(t.isAlive()) return false;
		}
		return true;
	}
	
	//TODO: Needs fixing because Java is dumb and I am not going to create a cmd controller class just for this, yet.
	private void startOfTurn() {	//Not safe, very probably explodes, tried with console, buffer, scanner and system.in, all throw internally a IOException and close the Stream
		_cleanConsole();
	    //_currentGameView.showMessage("Player " + this._currentGame.getNumPlayer() + ": " + Utils.MessageUtils.START_TURN);
	    _waitForEnter();
		_currentGameView.showMessage("Player " + this._currentGame.getNumPlayer() + ": ");
	    _currentGameView.showGame(_currentGame);
	}
	
	@Override
	public void selectSoldier() {		//Because select soldier is necessary, it will not be part of the commands, at least for now
		int posX = 0; int posY = 0;
		boolean finish = false;
		while(!finish) {		// Sergio: I am not afraid of consequences Arturo: I am
			try {
			//Get the coordinates of user
			_currentGameView.showMessage(Utils.MessageUtils.ASK_SELECT_SOLDIER);
			posX = _currentGameView.getInt(); posY = _currentGameView.getInt();
			Position pos = new Position(posX,posY);
			if(posX < 0 || posX > Game._boardWidth - 1 || posY < 0 || posY > Game._boardLength) throw new Exception(Utils.MsgErrors.INVALID_COORDINATES);
			
			//Search if troop is on board and is from the player
			Troop g = (Troop) _currentGame.getGameObject(pos);	
			if (Objects.isNull(g)) throw new Exception(Utils.MsgErrors.INVALID_SELECTION);								//Have to check if it exists (is a GO)
			if (!g.isAlive()) throw new Exception(Utils.MsgErrors.INVALID_SELECTION);								    //Have to check if it is a troop alive (walls and dead troops will return false)
			if(!_currentGame.getPlayer().hasTroop(g)) throw new Exception(Utils.MsgErrors.INVALID_SELECTION);   		//Have to check that it belongs to the player (sorry for the casting)
			_currTroop = g;
			finish = true;
			
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
	
	private void _cleanConsole() {
		//System.out.print("\033[H\033[2J");  
	    //System.out.flush();
		for(int i = 0; i < 50; ++i) System.out.println(" ");
	}
	
	private void _waitForEnter() {
		_currentGameView.get();
	}

	@Override
	public void update() {
		this._currentGame.update();
		
	}
	public void showGame() 
	{
		this._currentGameView.showGame(_currentGame);
	}

	@Override
	public int getNumPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}
}
