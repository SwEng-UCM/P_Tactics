package PTactics.control;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

import P.Tactics.CPU.EasyCPU;
import P.Tactics.CPU.HardCPU;
import P.Tactics.CPU.MediumCPU;
import PTactics.control.maps.MapSelector;
import PTactics.model.game.DangerMediator;
import PTactics.model.game.Game;
import PTactics.model.game.Observable;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.GameObjectCreator;
import PTactics.utils.Position;
import PTactics.view.GameObserver;

public abstract class Controller implements ControllerInterface,Observable<GameObserver> {
	protected Game _game;
	protected boolean _endTurn;
	public static int mapSelected = 1;
	public static int tileSize = 50;
	protected int _numPlayers = 0;
	protected List<String> _playerNames = new ArrayList<>();
	private List<GameObserver> _observers;
	
	public Controller() {
		_game = new Game(this);
		_endTurn = false;
		_observers = new ArrayList<>();
	}
	
	public JSONObject report() {
		return this.getGame().report();
	}
	
	public void setPlayerNum(int playerNum) {
		this._numPlayers = playerNum;
	}

	public void setPlayerNames(List<String> names) {
		_playerNames = names;
	}
	
	public List<String> getPlayerNames(){
		return _playerNames;
	}
	
	public String getCurrentPlayerName() {
		int idxPlayer = _game.getNumPlayer() - 1;
		return _playerNames.get(idxPlayer);
		
	}
	
	public void setUpPlayerVsCPU(int levelCPU) {
		boolean playersSetUp = false;

		if (!playersSetUp) {
			DangerMediator dangerMediator = new DangerMediator();
			for (Integer i = 1; i <= _numPlayers; ++i) {
				if(i<=1) 
				{
					Player p = new Player(i.toString(), dangerMediator);
					for (Troop t : MapSelector.getTroops(p)) {
						_game.addNewElement(t, t.getPos());
					}
					_game.addPlayer(p);
				}
				else 
				{
					switch(levelCPU) 
					{
					case 0:
						Player cpu = new Player(i.toString(), dangerMediator, new EasyCPU(this));
						for (Troop t : MapSelector.getTroops(cpu)) {
							_game.addNewElement(t, t.getPos());
						}
						_game.addPlayer(cpu);
						break;
					case 1:
						Player cpu1 = new Player(i.toString(), dangerMediator, new MediumCPU(this));
						for (Troop t : MapSelector.getTroops(cpu1)) {
							_game.addNewElement(t, t.getPos());
						}
						_game.addPlayer(cpu1);
						break;
					case 2:
						Player cpu2 = new Player(i.toString(), dangerMediator, new HardCPU(this));
						for (Troop t : MapSelector.getTroops(cpu2)) {
							_game.addNewElement(t, t.getPos());
						}
						_game.addPlayer(cpu2);
						break;
					
					}
				}
			}
			_game.inicialize();
			playersSetUp = true;
		}
	}
	
	// In principle, we do like player 0 turn --> check if player 1 has alive
	// troops...
	public boolean isFinish() {
		for (Troop t : _game.getPlayer().getTroops()) {
			if (t.isAlive())
				return false;
		}
		return true;
	}

	public void setupPlayers() {
		boolean playersSetUp = false;

		if (!playersSetUp) {
			DangerMediator dangerMediator = new DangerMediator();
			for (Integer i = 1; i <= _numPlayers; ++i) {
				Player p = new Player(i.toString(), dangerMediator);
				for (Troop t : MapSelector.getTroops(p)) {
					_game.addNewElement(t, t.getPos());
				}
				_game.addPlayer(p);
			}
			_game.inicialize();
			playersSetUp = true;
		}
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

	public void updatePlayers() {
		this._game.updatePlayers();
	}

	@Override
	public int getNumPlayer() {
		return _game.getNumPlayer();
	}

	public void addObserver(GameObserver o) {
		_observers.add(o);
	}
	public void removeObserver(GameObserver o) {
		_observers.remove(o);
	}
	
	@Override
	public void selectTroop(Position pos) throws Exception {
		_game.selectTroop(pos);
	}
	
	@Override
	public void selectTroop(Troop t) {
		_game.selectTroop(t);
	}

	public boolean isTroopSelected() {
		return _game.isTroopSelected();
	}

	public boolean canMove(Position pos) {
		return _game.canMove(pos);
	}

	public void moveTroop(Position pos) throws IllegalArgumentException {
		_game.moveTroop(pos);
	}

	public void troopAbility(Position pos) throws Exception {
		_game.troopAbility(pos);
	}

	public void takeAim(Direction _dirToAim) {
		_game.takeAim(_dirToAim);
	}

	public Boolean isTroop(Position pos) {
		return this._game.isTroop(pos);
	}

	public Game getGame() {
		return this._game;
	}

	public Troop currTroop() {
		return _game.currentTroop();
	}

	public boolean dangerTile(Position pos) {
		return _game.dangerTile(pos);
	}

	@Override
	public List<Position> getPath(Position pos) {
		return _game.getPath(pos);
	}

	public List<Position> hoverPath(Position pos) {
		return _game.hoverPath(pos);
	}
	
	@Override
	public void load(InputStream is) {
		JSONObject gameState = new JSONObject(new JSONTokener(is));
		 _loadController(gameState);
		_loadPlayers(gameState);
		_loadBoard(gameState);
	}

	private void _loadPlayers(JSONObject gameState) {
		boolean playersSetUp = false;
		_game.set(gameState);

		if (!playersSetUp) {
			DangerMediator dangerMediator = new DangerMediator();
			for (Integer i = 1; i <= _numPlayers; ++i) {
				Player p = new Player(i.toString(), dangerMediator);
				_game.addPlayer(p);
			}
			_game.inicialize();
			playersSetUp = true;
		}
	}
	
	private void _loadBoard(JSONObject gameState) {
		for (int i1 = 0; i1 < gameState.getJSONArray("Board").length(); i1++) {
			JSONObject jo = (JSONObject) gameState.getJSONArray("Board").get(i1);
			_game.addNewElement(GameObjectCreator.createGameObject(jo, this),
					new Position(jo.getInt("PositionX"), jo.getInt("PositionY")));
		}
	}
	
	protected void _loadController(JSONObject gameState) {
		this._numPlayers = gameState.getInt("Players");
		_endTurn = false;
	}
	public void updateOnPlayersUpdate() {
		for (GameObserver o : _observers) {
			o.onPlayersUpdate(_game);
		}
	}
	public void updateOnBoardUpdate() {
		for (GameObserver o : _observers) {
			o.onBoardUpdate(_game);
		}
	}
	public void updateOnTroopAction() {
		for (GameObserver o : _observers) {
			o.onTroopAction(_game);
		}
	}
	public void updateOnTroopSelection() {
		for (GameObserver o : _observers) {
			o.onTroopSelection(_game);
		}	
	}
	public void updateOnNextTurn() {
		for (GameObserver o : _observers) {
			o.onNextTurn(_game);
		}	
	}
	public void updateOnTroopUnSelection() {
		for (GameObserver o : _observers) {
			o.onTroopUnSelection(_game);
		}	
	}
}
