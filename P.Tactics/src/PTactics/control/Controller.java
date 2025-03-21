package PTactics.control;

import java.util.InputMismatchException;
import PTactics.control.Commands.Command;
import PTactics.control.Commands.CommandGenerator;
import PTactics.model.GameObjects.Troop;
import PTactics.control.Maps.MapSelector;
import PTactics.model.Game.DangerMediator;
import PTactics.model.Game.Game;
import PTactics.model.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;
import PTactics.View.GameView;

public class Controller implements ControllerInterface{
	private Game _game;
	private GameView _gameView;
	private boolean _endTurn;
	private Troop _troop;
	public static int mapSelected = 1;
	
	public Controller() {
		setup();
	}
	
	public void run() {
		this.setup();
		while(!this.isFinish()) {
			startOfTurn();
			while(!_endTurn) {
				String[] userCommand = _gameView.getPrompt();
				Command command = CommandGenerator.parse(userCommand);
				
				 if (command != null) { 
			        command.execute(this);
			        if (_troop != null && !_troop.isAlive()) {
			        	_troop = null;
			        }
			        System.out.println("Current troop selected: " +(this._troop==null?"none":this._troop.getId()) + (this._troop==null?"":(" In position:"+(this._troop.getPos().getY()+1)+" "+(this._troop.getPos().getX()+1))));
			        System.out.println(_troop == null? "" : "Moves left: " + _troop.getMovesLeft());
			        System.out.println(_troop == null? "" : !_troop.isAbility()? "" : "Ability turns left: " + _troop.abilityUsesLeft());
			        showGame();
				 } else {
					 _gameView.showError(Utils.MsgErrors.UNKNOWN_COMMAND);
				 }
			}
			nextTurn();
		}
	}
	
	private final void setup() {
		int numPlayers = 0;
		boolean correct = false;
		//TODO: Give them to decide between maps or randomizer
		this._gameView = new GameView();
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
			for(Troop t : MapSelector.getTroops(p)) {
				_game.addNewElement(t, t.getPos());
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
	    _waitForEnter(); //First one as a cin.get()
	    _waitForEnter(); //Second, to receive the enter key from user
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
	public void setTroop(Troop t) {
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
	public void updatePlayers() 
	{
		this._game.updatePlayers();
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
	public void selectTroop(Position pos) throws Exception {
		_game.selectTroop(pos);
	}
	
	public boolean isTroopSelected() {
		return _game.isTroopSelected();
	}
	
	public boolean canMove(Position pos) {
		return _game.canMove(pos);
	}
	
	public void moveTroop(Position pos) throws IllegalArgumentException{
		_game.moveTroop(pos);
	}
	
	public void troopAbility(Position pos) throws Exception{
		_game.troopAbility(pos);
	}
	
	public void takeAim(Direction _dirToAim) {
		_game.takeAim(_dirToAim);
	}
}
