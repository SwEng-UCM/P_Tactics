package PTactics.control;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

import PTactics.CPU.EasyCPU;
import PTactics.CPU.HardCPU;
import PTactics.CPU.MediumCPU;
import PTactics.control.commands.Command;
import PTactics.control.commands.CommandGenerator;
import PTactics.control.maps.MapSelector;
import PTactics.model.game.Board;
import PTactics.model.game.DangerMediator;
import PTactics.model.game.Game;
import PTactics.model.game.Observable;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.GameObjectCreator;
import PTactics.utils.Position;
import PTactics.view.GameObserver;

public abstract class Controller implements ControllerInterface, Observable<GameObserver> {
	protected Game _game;
	protected boolean _endTurn;
	public static int mapSelected = 1;
	protected int _numPlayers = 0;
	protected List<String> _playerNames = new ArrayList<>();
	private List<GameObserver> _observers;

	public Controller() {
		_endTurn = false;
		_observers = new ArrayList<>();
	}

	@Override
	public JSONObject report() {
		return _game.report();
	}

	@Override
	public void setPlayerNum(int playerNum) {
		this._numPlayers = playerNum;
	}

	@Override
	public void setPlayerNames(List<String> names) {
		_playerNames = names;
	}

	@Override
	public void setMap(int i) {
		MapSelector.mapSelected = i + 1;
	}

	@Override
	public void createGame() {
		_game = new Game(this);
	}

	@Override
	public List<String> getPlayerNames() {
		return _playerNames;
	}

	@Override
	public String getCurrentPlayerName() {
		int idxPlayer = _game.getNumPlayer() - 1;
		return _playerNames.get(idxPlayer);
	}

	@Override
	public void setUpPlayerVsCPU(int levelCPU) {
		boolean playersSetUp = false;

		if (!playersSetUp) {
			DangerMediator dangerMediator = new DangerMediator();
			for (Integer i = 1; i <= _numPlayers; ++i) {
				if (i <= 1) {
					Player p = new Player(i.toString(), dangerMediator);
					for (Troop t : MapSelector.getTroops(p)) {
						Board.getInstance().addObj(t.getPos(), t);
					}
					_game.addPlayer(p);
				} else {
					switch (levelCPU) {
					case 0:
						Player cpu = new Player(i.toString(), dangerMediator, new EasyCPU(this));
						for (Troop t : MapSelector.getTroops(cpu)) {
							Board.getInstance().addObj(t.getPos(), t);
						}
						_game.addPlayer(cpu);
						break;
					case 1:
						Player cpu1 = new Player(i.toString(), dangerMediator, new MediumCPU(this));
						for (Troop t : MapSelector.getTroops(cpu1)) {
							Board.getInstance().addObj(t.getPos(), t);
						}
						_game.addPlayer(cpu1);
						break;
					case 2:
						Player cpu2 = new Player(i.toString(), dangerMediator, new HardCPU(this));
						for (Troop t : MapSelector.getTroops(cpu2)) {
							Board.getInstance().addObj(t.getPos(), t);
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
	@Override
	public boolean isFinish() {
		return (_game.getPlayer().winPoints() >= Board.getInstance().pointsToWin()) || _game.isLastPlayerStanding();
	}

	@Override
	public void setupPlayers() {
		boolean playersSetUp = false;

		if (!playersSetUp) {
			DangerMediator dangerMediator = new DangerMediator();
			for (Integer i = 1; i <= _numPlayers; ++i) {
				Player p = new Player(i.toString(), dangerMediator);
				for (Troop t : MapSelector.getTroops(p)) {
					Board.getInstance().addObj(t.getPos(), t);
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

	@Override
	public void nextTurn() {
		_game.nextTurn();
	}

	@Override
	public void update() {
		this._game.update();
	}

	@Override
	public void updatePlayers() {
		this._game.updatePlayers();
	}

	@Override
	public int getNumPlayer() {
		return _game.getNumPlayer();
	}

	@Override
	public int getCurrentPlayerWinZone() {
		return Board.getInstance().pointsToWin() - _game.getPlayer().winPoints();
	}

	@Override
	public boolean cpuIsPlaying() {
		return _game.cpuIsPlaying();
	}

	@Override
	public void addObserver(GameObserver o) {
		_observers.add(o);
	}

	@Override
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

	@Override
	public boolean isTroopSelected() {
		return _game.isTroopSelected();
	}

	@Override
	public boolean canMove(Position pos) {
		return _game.canMove(pos);
	}

	@Override
	public void moveTroop(Position pos) throws IllegalArgumentException {
		_game.moveTroop(pos);
	}

	@Override
	public void troopAbility(Position pos) throws Exception {
		_game.troopAbility(pos);
	}

	@Override
	public void takeAim(Direction _dirToAim) {
		_game.takeAim(_dirToAim);
	}

	@Override
	public Boolean isTroop(Position pos) {
		return this._game.isTroop(pos);
	}

	@Override
	public Troop getCurrentTroop() {
		return _game.getCurrentTroop();
	}

	@Override
	public boolean dangerTile(Position pos) {
		return _game.dangerTile(pos);
	}

	@Override
	public List<Position> getPath() {
		return _game.getPath();
	}

	@Override
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
			Board.getInstance().addObj(new Position(jo.getInt("PositionX"), jo.getInt("PositionY")),
					GameObjectCreator.createGameObject(jo, this));
		}
	}

	protected void _loadController(JSONObject gameState) {
		_game.set(gameState);
		this._numPlayers = gameState.getInt("Players");
		_endTurn = false;
	}

	@Override
	public void updateOnPlayersUpdate() {
		for (GameObserver o : _observers) {
			o.onPlayersUpdate(_game);
		}
	}

	@Override
	public void updateOnBoardUpdate() {
		for (GameObserver o : _observers) {
			o.onBoardUpdate(_game);
		}
	}

	@Override
	public void updateOnTroopAction() {
		for (GameObserver o : _observers) {
			o.onTroopAction(_game);
		}
	}

	@Override
	public void updateOnTroopSelection() {
		for (GameObserver o : _observers) {
			o.onTroopSelection(_game);
		}
	}

	@Override
	public void updateOnNextTurn() {
		for (GameObserver o : _observers) {
			o.onNextTurn(_game);
		}
	}

	@Override
	public void updateOnTroopUnSelection() {
		for (GameObserver o : _observers) {
			o.onTroopUnSelection(_game);
		}
	}

	@Override
	public void executeCommand(String[] args) {
		Command command = CommandGenerator.parse(args);
		command.execute(this);
	}

	@Override
	public Player getPlayer() {
		return _game.getPlayer();
	}

	@Override
	public Player getPlayer(int idx) {
		return _game.getPlayer(idx);
	}

	@Override
	public TroopInfo getCurrentTroopInfo() {
		return getCurrentTroop() != null
				? new TroopInfo(_game.getCurrentTroop().getId(), _game.getCurrentTroop().getPos(),
						_game.getCurrentTroop().getMovesLeft(), _game.getCurrentTroop().abilityUsesLeft())
				: null;
	}

	@Override
	public void onDeadTroopSelected() {
		_game.onDeadTroopSelected();
	}

	@Override
	public List<Position> getEnemyTroops() {
		return _game.getEnemyTroops();
	}

	@Override
	public boolean isOnline() {
		return false;
	}

	@Override
	public boolean isMyTurn() {
		return true;
	}

	@Override
	public void logPlayers() {
	}

	@Override
	public boolean isWinPosition(Position pos) {
		return Board.getInstance().isWinPosition(pos);
	}
}
