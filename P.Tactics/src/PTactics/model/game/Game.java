package PTactics.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import PTactics.control.ControllerInterface;
import PTactics.control.maps.MapSelector;
import PTactics.model.gameObjects.GameObject;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;
import PTactics.view.GUI.Icons;

public class Game {
	public static int _boardLength; // This is the first value (y)
	public static int _boardWidth; // This is the second value (x)
	private int _currPlayer;
	private Troop _currTroop;

	private List<Player> _players;
	private ControllerInterface ctrl;

	// Constructor
	public Game(ControllerInterface ctrl) {
		Game._boardLength = MapSelector.getLength();
		Game._boardWidth = MapSelector.getWidth();
		Position._gameLength = MapSelector.getLength();
		Position._gameWidth = MapSelector.getWidth();
		this._players = new ArrayList<>();
		this._currPlayer = 0;
		this.ctrl = ctrl;
	}
	
	public Game(JSONObject gameState, ControllerInterface ctrl) {
		this.ctrl = ctrl;
		set(gameState);
	}

	// Game State Setup
	public void set(JSONObject gameState) {
		Position._gameLength = gameState.getInt("BoardLenght");
		Position._gameWidth = gameState.getInt("BoardWidth");
		_boardLength = gameState.getInt("BoardLenght");
		_boardWidth = gameState.getInt("BoardWidth");
		_currPlayer = gameState.getInt("Turn");
		_players = new ArrayList<>();
		_currTroop = null;
	}

	public void inicialize() { // total update, only called on the setup
		InicializeTurns();
		Board.getInstance().update();
		inicializePlayers();
	}

	private void InicializeTurns() {
		_players.get(0).startTurn();
	}

	private void inicializePlayers() {
		for (Player p : _players) {
			p.update();
		}
	}

	// Player Management
	public void addPlayer(Player p) {
		this._players.add(p);
	}

	public Player getPlayer() {
		return this._players.get(_currPlayer);
	}

	public Player getPlayer(int idx) {
		return this._players.get(idx - 1);
	}

	public int getNumPlayer() { // Human view
		return this._currPlayer + 1;
	}

	public void updatePlayers() {
		for (Player p : _players) {
			p.update();
		}
		updateOnPlayersUpdate();
	}

	public void nextTurn() {
		Board.getInstance().nextTurn();
		_currTroop = null;
		_players.get(_currPlayer).endTurn();
		_players.get(_currPlayer).clearKills();
		_players.get(_currPlayer).update();
		update();

		do {
			_currPlayer++;
			if (_currPlayer >= _players.size()) {
				_currPlayer = 0;
			}
		} while (_players.get(_currPlayer).hasNoTroopsLeft());

		_players.get(_currPlayer).startOfTurnDeadCheck();
		_players.get(_currPlayer).startTurn();
		updateOnNextTurn();
		if (!ctrl.isFinish()) {
			SwingUtilities.invokeLater(() -> _players.get(_currPlayer).ComputeTurn());
		}
	}

	public boolean isLastPlayerStanding() {
		for (Player player : _players) {
			if (!player.equals(getPlayer())) {
				for (Troop troop : player.getTroops()) {
					if (troop.isAlive()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean cpuIsPlaying() {
		return getPlayer().isCPU();
	}

	// Board Management
	public void updateBoard() {
		Board.getInstance().update();
		updateOnBoardUpdate();
	}

	// Troop Management

	public boolean isTroopSelected() {
		return !(_currTroop == null);
	}

	public Boolean isTroop(Position pos) {
		GameObject t = Board.getInstance().getGameObject(pos);
		if (!Objects.isNull(t) && t.isAlive() && t.isSeeThrough()) {
			return ((Troop) t).getPlayerID().equals(_players.get(_currPlayer).getId());
		}
		return false;
	}

	public void selectTroop(Position pos) throws Exception {
		GameObject t = Board.getInstance().getGameObject(pos);
		if (Objects.isNull(t)) {
			throw new Exception(Utils.MsgErrors.INVALID_SELECTION);
		}
		if (!t.isAlive()) {
			throw new Exception(Utils.MsgErrors.INVALID_SELECTION);
		}
		if (!((Troop) t).getPlayerID().equals(_players.get(_currPlayer).getId())) {
			throw new Exception(Utils.MsgErrors.INVALID_SELECTION);
		}
		_currTroop = (Troop) t;
		updateOnTroopSelection();
	}

	public void selectTroop(Troop t) {
		_currTroop = t;
		updateOnTroopSelection();
	}

	public boolean canMove(Position pos) {
		return _currTroop.isAlive()
				&& (!(_currTroop.getPos().getX() == pos.getX()) || !(_currTroop.getPos().getY() == pos.getY()))
				&& (Board.getInstance().getGameObject(pos) == null
						|| Board.getInstance().getGameObject(pos).isWalkable());
	}

	public void moveTroop(Position pos) throws IllegalArgumentException {
		_currTroop.startMove(pos);
		_currTroop.update();
		updatePlayers();
		updateOnTroopAction();
	}

	public void troopAbility(Position pos) throws Exception {
		if (_currTroop.abilityUsesLeft() == 0) {
			throw new Exception("No uses left for the ability");
		}
		if (!_currTroop.getId().equals(Utils.TroopUtils.LIGHT_TROOP_ID) && !pos.isValid()) {
			throw new Exception("No uses left for the ability");
		}
		_currTroop.activateAbility(pos);
		updateOnTroopAction();
	}

	public void takeAim(Direction _dirToAim) {
		_currTroop.takeAim(_dirToAim);
		updateOnTroopAction();
	}

	public Troop getCurrentTroop() {
		return _currTroop;
	}

	public void onDeadTroopSelected() {
		if (_currTroop != null && !_currTroop.isAlive()) {
			_currTroop = null;
		}
	}

	// Path & Danger Utilities
	public boolean dangerTile(Position pos) {
		return _players.get(_currPlayer).isInDanger(pos);
	}

	public List<Position> getPath() {
		return _currTroop == null ? null : _currTroop.getCurrentPath();
	}

	public List<Position> hoverPath(Position pos) {
		return _currTroop == null ? null : _currTroop.hoverPath(pos);
	}

	public List<Position> getEnemyTroops() {
		List<Position> returnList = new ArrayList<Position>();
		for (Player p : this._players) {
			if (!p.isMyTurn()) {
				for (Troop t : p.getTroops()) {
					returnList.add(t.getPos());
				}
			}
		}
		return returnList;
	}

	// Board Display
	public String positionToString(Position p) {
		boolean visible = _players.get(_currPlayer).isVisible(p.getX(), p.getY());
		if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isSeeThrough()) {
			return Board.getInstance().toString(p);
		}
		if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isAlive()) {
			return Utils.TroopUtils.TROOP_DEAD;
		}
		if (visible) {
			if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isAlive()) {
				if (_players.get(_currPlayer).lastTurnKill(p)) {
					return Utils.TroopUtils.TROOP_DEAD;
				}
				return Utils.TroopUtils.TROOP_DEAD;
			}
			if (_players.get(_currPlayer).isVisible(p.getX(), p.getY())) {
				return Board.getInstance().toString(p);
			}
		}
		if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isAlive()) {
			if (_players.get(_currPlayer).lastTurnKill(p)) {
				return "†";
			}
		}
		return "*";
	}

	// ACTUAL GOOD posToIcon//
	public Icon positionToIcon(Position p) {
		boolean visible = _players.get(_currPlayer).isVisible(p.getX(), p.getY());

		// Wall
		if (Board.getInstance().getGameObject(p) != null && Board.getInstance().getGameObject(p).isSolid()
				&& !Board.getInstance().getGameObject(p).isSeeThrough()) {
			return new ImageIcon(Board.getInstance().toIcon(p).getImage().getScaledInstance(Position.tileSize,
					Position.tileSize, 4));
		}

		// Troop dead
		if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isAlive()) {
			return Icons.TroopIcons.DEAD;
		}

		// If visible
		if (visible) {
			// Anything visible
			if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isSeeThrough()) {
				return new ImageIcon(Board.getInstance().toIcon(p).getImage().getScaledInstance(Position.tileSize,
						Position.tileSize, 4));
			}

			// Just floor
			return new ImageIcon(Board.getInstance().toIcon(p).getImage().getScaledInstance(Position.tileSize,
					Position.tileSize, 4));
		}

		// Return fog
		return new ImageIcon(
				Icons.otherIcons.FOG.getImage().getScaledInstance(Position.tileSize, Position.tileSize, 4));
	}

	// Observers
	public void addObserver(GameObserver o) {
		ctrl.addObserver(o);
	}

	public void removeObserver(GameObserver o) {
		ctrl.removeObserver(o);
	}

	// Update Callback //
	void updateOnPlayersUpdate() {
		ctrl.updateOnPlayersUpdate();
	}

	void updateOnBoardUpdate() {
		ctrl.updateOnBoardUpdate();
	}

	void updateOnTroopAction() {
		ctrl.updateOnTroopAction();
	}

	void updateOnTroopSelection() {
		ctrl.updateOnTroopSelection();
	}

	void updateOnNextTurn() {
		ctrl.updateOnNextTurn();
	}

	void updateOnTroopUnSelection() {
		ctrl.updateOnTroopUnSelection();
	}

	// Update Entry Point
	public void update() {
		updateBoard();
		updatePlayers();
	}

	// Report
	public JSONObject report() {
		JSONObject report = new JSONObject();
		report.put("Players", _players.size());
		report.put("BoardLenght", _boardLength);
		report.put("BoardWidth", _boardWidth);
		report.put("Turn", this.getNumPlayer() - 1);
		report.put("Board", Board.getInstance().report());
		return report;
	}
}