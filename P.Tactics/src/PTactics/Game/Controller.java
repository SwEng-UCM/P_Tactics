package PTactics.Game;

import java.util.InputMismatchException;
import PTactics.Commands.Command;
import PTactics.Commands.CommandGenerator;
import PTactics.GameObjects.Cloaker;
import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;
import PTactics.View.GameView;

public class Controller implements ControllerInterface{
	private Game _game;
	private GameView _gameView;
	private boolean _endTurn;
	private Troop _troop;
	
	public Controller() {
		this._gameView = new GameView();
	}
	
	public void run() {
		this.setup();
		while(!this.isFinish()) {
			startOfTurn();
			while(!_endTurn) {
				String[] userCommand = _gameView.getPrompt();
				Command command = CommandGenerator.parse(userCommand);
				
				 if (command != null) { 
			        command.execute(this, _troop); //TODO: need an interface to protect game, probably will receive troop t too
			        System.out.println("Current troop selected: " + (this._troop==null?"none":("In position:"+(this._troop.getPos().Y+1)+" "+(this._troop.getPos().X+1))));
			        showGame();
				 } else {
					 _gameView.showError(Utils.MsgErrors.UNKNOWN_COMMAND);
				 }
			}
			_game.nextTurn();
		}
	}
	
	private void setup() {
		int numPlayers = 0;
		boolean correct = false;
		//TODO: Give them to decide between maps or randomizer
		this._game = new Game();
		_gameView.showMessage(Utils.MessageUtils.WELCOME_MSG);
		_gameView.showMessage(Utils.MessageUtils.ASK_NUMBER_PLAYERS);
		
		while(!correct) {
			try {
				numPlayers = _gameView.getInt();
				if(numPlayers < 2 || numPlayers > 4) throw new Exception();
				correct = true;
			}
			catch (InputMismatchException inputError) {
				_gameView.showMessage(Utils.MsgErrors.INVALID_INPUT);
				_gameView.get();	//Clearing the buffer to avoid infinite loop!
				correct = false;
			} 
			catch (Exception e) {
				_gameView.showMessage(Utils.MsgErrors.INVALID_NUM_PLAYERS);
				correct = false;
			}
		}
		DangerMediator dangerMediator = new DangerMediator();
		//TODO: Give troops to each player:
		for(Integer i = 1; i <= numPlayers; ++i) {
			Player p = new Player(i.toString(), dangerMediator);
			//for(int i1 = 0; i1 < Utils.Data.STARTING_SOLDIERS; ++i1) {					//TODO: This is just a demo
				//Troop t = new Troop(new Position(i1,i1), _currentGame.getBoard());
				//p.addTroops(t);															//Adding manually because addTroops() --> adds to current player and we do not want them
				//_currentGame.addNewElement(t, t.getPos());
				if(i == 1) { //uncomment troop creation when troops fully work
					Cloaker t1 = new Cloaker(new Position(2,3), p, _game.getBoard());
					_game.addNewElement(t1, t1.getPos());
					
					//Troop t2 = new Troop(new Position(3,3), p, _game.getBoard());
					//_game.addNewElement(t2, t2.getPos());
					
					//Troop t3 = new Troop(new Position(4,3), p, _game.getBoard());
					//_game.addNewElement(t3, t3.getPos());
				}
				else if (i == 2) {
					Cloaker t1 = new Cloaker(new Position(2,8), p, _game.getBoard());
					_game.addNewElement(t1, t1.getPos());
					
					//Troop t2 = new Troop(new Position(6,9), p, _game.getBoard());
					//_game.addNewElement(t2, t2.getPos());
					
					//Troop t3 = new Troop(new Position(9,9), p, _game.getBoard());
					//_game.addNewElement(t3, t3.getPos());
				}
			//}
			_game.addPlayer(p);
		}
		_game.update();
	}
	
	private boolean isFinish() {	//In principle, we do like player 0 turn --> check if player 1 has alive troops...
		for(Troop t : _game.getPlayer().getTroops()) {
			if(t.isAlive()) return false;
		}
		return true;
	}
	
	//TODO: Needs fixing because Java is dumb and I am not going to create a cmd controller class just for this, yet.
	private void startOfTurn() {
		_endTurn = false;
		_cleanConsole();
	    _gameView.showMessage("Player " + getNumPlayer() + ": " + Utils.MessageUtils.START_TURN);
	    _waitForEnter();
	    _waitForEnter();
		_gameView.showMessage("Player " + getNumPlayer() + ": ");
	    _gameView.showGame(_game);
	}
	

	@Override
	public void endTurn() {
		_endTurn = true;
	}
	
	@Override
	public void setTroop(Troop t) {		//Because select soldier is necessary, it will not be part of the commands, at least for now
		this._troop = t;
	}
	
	private void _cleanConsole() {
		//System.out.print("\033[H\033[2J");  
	    //System.out.flush();
		for(int i = 0; i < 50; ++i) System.out.println(" ");
	}
	
	private void _waitForEnter() {
		_gameView.get();
	}
	
	@Override
	public void update() {
		this._game.update();
		
	}
	public void showGame() {
		this._gameView.showGame(_game);
	}

	@Override
	public int getNumPlayer() {
		return _game.getNumPlayer();
	}
	
	@Override
	public String[] getPrompt() {
		return _gameView.getPrompt();
	}
	
	@Override
	public int getInt() {
		return _gameView.getInt();
	}
	
	@Override
	public void showMessage(String msg) {
		_gameView.showMessage(msg);
	}

	@Override
	public  GameObject getGameObject(Position pos) {
		return _game.getGameObject(pos);
	}
}
