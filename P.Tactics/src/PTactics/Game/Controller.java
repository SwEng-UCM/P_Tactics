package PTactics.Game;

import java.util.InputMismatchException;
import PTactics.Commands.Command;
import PTactics.Commands.CommandGenerator;
import PTactics.GameObjects.GameObject;
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
	
	public void run() {
		this.setup();
		while(!this.isFinish()) {
			startOfTurn();
			while(!_endTurn) {
				String[] userCommand = _currentGameView.getPrompt();
				Command command = CommandGenerator.parse(userCommand);
				
				 if (command != null) { 
			        command.execute(this, _currTroop); //TODO: need an interface to protect game, probably will receive troop t too
			        System.out.println("Current troop selected: " + (this._currTroop==null?"none":("In position:"+(this._currTroop.getPos().Y+1)+" "+(this._currTroop.getPos().X+1))));
			        showGame();
				 } else {
					 _currentGameView.showError(Utils.MsgErrors.UNKNOWN_COMMAND);
				 }
			}
			_currentGame.nextTurn();
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
					Troop t1 = new Troop(new Position(2,3), p, _currentGame.getBoard());
					p.addTroops(t1);
					_currentGame.addNewElement(t1, t1.getPos());
					
					Troop t2 = new Troop(new Position(3,3), p, _currentGame.getBoard());
					p.addTroops(t2);
					_currentGame.addNewElement(t2, t2.getPos());
					
					Troop t3 = new Troop(new Position(4,3), p, _currentGame.getBoard());
					p.addTroops(t3);
					_currentGame.addNewElement(t3, t3.getPos());
				}
				else if (i == 2) {
					Troop t1 = new Troop(new Position(2,8), p, _currentGame.getBoard());
					p.addTroops(t1);
					_currentGame.addNewElement(t1, t1.getPos());
					
					Troop t2 = new Troop(new Position(6,9), p, _currentGame.getBoard());
					p.addTroops(t2);
					_currentGame.addNewElement(t2, t2.getPos());
					
					Troop t3 = new Troop(new Position(9,9), p, _currentGame.getBoard());
					p.addTroops(t3);
					_currentGame.addNewElement(t3, t3.getPos());
				}
			}
			_currentGame.addPlayer(p);
		}
		_currentGame.update();
	}
	
	private boolean isFinish() {	//In principle, we do like player 0 turn --> check if player 1 has alive troops...
		for(Troop t : _currentGame.getPlayer().getTroops()) {
			if(t.isAlive()) return false;
		}
		return true;
	}
	
	//TODO: Needs fixing because Java is dumb and I am not going to create a cmd controller class just for this, yet.
	private void startOfTurn() {
		_endTurn = false;
		_cleanConsole();
	    _currentGameView.showMessage("Player " + getNumPlayer() + ": " + Utils.MessageUtils.START_TURN);
	    _waitForEnter();
	    _waitForEnter();
		_currentGameView.showMessage("Player " + getNumPlayer() + ": ");
	    _currentGameView.showGame(_currentGame);
	}
	

	@Override
	public void endTurn() {
		_endTurn = true;
	}
	
	@Override
	public void setTroop(Troop t) {		//Because select soldier is necessary, it will not be part of the commands, at least for now
		this._currTroop = t;
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
	public void showGame() {
		this._currentGameView.showGame(_currentGame);
	}

	@Override
	public int getNumPlayer() {
		return _currentGame.getNumPlayer();
	}
	
	@Override
	public String[] getPrompt() {
		return _currentGameView.getPrompt();
	}
	
	@Override
	public int getInt() {
		return _currentGameView.getInt();
	}
	
	@Override
	public void showMessage(String msg) {
		_currentGameView.showMessage(msg);
	}

	@Override
	public  GameObject getGameObject(Position pos) {
		return _currentGame.getGameObject(pos);
	}
}
