package PTactics.Game;

import java.util.InputMismatchException;
import PTactics.Commands.Command;
import PTactics.Commands.CommandGenerator;
import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.Troop;
import PTactics.Utils.MapSelector;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;
import PTactics.View.GameView;

public class Controller implements ControllerInterface{
	private Game _game;
	private GameView _gameView;
	private boolean _endTurn;
	private Troop _troop;
	public static int mapSelected = 0;
	
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
			        command.execute(this, _troop);
			        System.out.println("Current troop selected: " + (this._troop==null?"none":("In position:"+(this._troop.getPos().Y+1)+" "+(this._troop.getPos().X+1))));
			        showGame();
				 } else {
					 _gameView.showError(Utils.MsgErrors.UNKNOWN_COMMAND);
				 }
			}
			nextTurn();
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
		for(Integer i = 1; i <= numPlayers; ++i) {
			Player p = new Player(i.toString(), dangerMediator);
			for(Position pos : MapSelector.getTroops(mapSelected, i)) {
				Troop t1 = new Troop(pos, p);
				_game.addNewElement(t1, t1.getPos());
				}
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
	
	public void nextTurn() {
		_troop = null;
		_game.nextTurn();
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
