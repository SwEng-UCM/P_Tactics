package PTactics.control;

import java.awt.EventQueue;
import java.util.InputMismatchException;

import PTactics.model.game.DangerMediator;
import PTactics.model.game.Game;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GameConsoleView;
import PTactics.view.GUI.GameWindow;
import PTactics.control.commands.Command;
import PTactics.control.commands.CommandGenerator;
import PTactics.control.maps.MapSelector;

public class Controller implements ControllerInterface{
	private Game _game;
	private GameConsoleView _gameView;
	private boolean _endTurn;
	public static int mapSelected = 1;
	
	public Controller() {
		setup();
	}
	
	public void run() {
		
		while(!this.isFinish()) {
			startOfTurn();
			while(!_endTurn) {
				String[] userCommand = _gameView.getPrompt();
				Command command = CommandGenerator.parse(userCommand);
				
				 if (command != null) { 
			        command.execute(this);
			        _game.onDeadTroopSelected();
			        
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
		this._game = new Game();
		this._gameView = new GameConsoleView(_game);
		_gameView.showMessage(Utils.MessageUtils.WELCOME_MSG);
		_gameView.showMessage(Utils.MessageUtils.ASK_NUMBER_PLAYERS);
		
		//GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow(_game);
					window.GetGameWindow().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
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
		_game.inicialize();
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
		_gameView.showStartOfTurn(this, _game);
	}
	

	@Override
	public void endTurn() {
		_endTurn = true;
	}
	
	public void nextTurn() {
		_game.dropTroop();
		_game.nextTurn();
	}
	
	@Override
	public void setTroop(Troop t) {
		_game.setTroop(t);
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
