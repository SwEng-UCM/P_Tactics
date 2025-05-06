package P.Tactics.Multiplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.json.JSONObject;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.control.maps.MapSelector;
import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.GameObject;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;
import PTactics.view.GUI.Icons;

public class OnlineGame {
	public static int _boardLength; // This is the first value (y)
	public static int _boardWidth; // This is the second value (x)
	private int _currPlayer;
	private Troop _currTroop;

	private ControllerInterface ctrl;

	// Constructor
	public OnlineGame(ControllerInterface ctrl) {
		OnlineGame._boardLength = MapSelector.getLength();
		OnlineGame._boardWidth = MapSelector.getWidth();
		Position._gameLength = MapSelector.getLength();
		Position._gameWidth = MapSelector.getWidth();
		this._currPlayer = 0;
		this.ctrl = ctrl;
	}


//	public void inicialize() { // total update, only called on the setup
//		InicializeTurns();
//		Board.getInstance().update();
//		inicializePlayers();
//	}
	
//	private void InicializeTurns() {
//		_players.get(0).startTurn();
//	}
//
//	private void inicializePlayers() {
//		for (Player p : _players) {
//			p.update();
//		}
//	}

	// Player Management
//	public void addPlayer(Player p) {
//		this._players.add(p);
//	}

//	public Player getPlayer() {
//		return this.ctrl.getPlayer();
//	}

//	public Player getPlayer(int idx) {
//		return this._players.get(idx - 1);
//	}

//	public int getNumPlayer() { // Human view
//		return this._currPlayer + 1;
//	}

//	public void updatePlayers() {
//		for (Player p : _players) {
//			p.update();
//		}
//		updateOnPlayersUpdate();
//	}

	public void nextTurn() {
		_currTroop = null;

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
			return ((Troop) t).getPlayerID().equals(ctrl.getPlayer().getId());
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
		if (!((Troop) t).getPlayerID().equals(ctrl.getPlayer().getId())) {
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
		ctrl.updatePlayers();
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
		return ctrl.getPlayer().isInDanger(pos);
	}

	public List<Position> getPath(Position pos) {
		return _currTroop == null ? null : _currTroop.getCurrentPath(pos);
	}

	public List<Position> hoverPath(Position pos) {
		return _currTroop == null ? null : _currTroop.hoverPath(pos);
	}


	// Board Display

	public Icon positionToIcon(Position p) {
        boolean visible = ctrl.getPlayer().isVisible(p.getX(), p.getY());

        //Wall
        if (Board.getInstance().getGameObject(p) != null && Board.getInstance().getGameObject(p).isSolid()
                && !Board.getInstance().getGameObject(p).isSeeThrough()) {
            return new ImageIcon(Board.getInstance().toIcon(p).getImage().getScaledInstance(Position.tileSize,
            		Position.tileSize, 4), Board.getInstance().toIcon(p).toString());
        }

        //Troop dead
        if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isAlive()) {
            return Icons.TroopIcons.DEAD;
        }

        //If visible
        if (visible) {
            //Anything visible
            if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isSeeThrough()) {
                return new ImageIcon(Board.getInstance().toIcon(p).getImage().getScaledInstance(Position.tileSize,
                		Position.tileSize, 4), Board.getInstance().toIcon(p).toString());
            }

            //Just floor
            return new ImageIcon(Board.getInstance().toIcon(p).getImage().getScaledInstance(Position.tileSize,
            		Position.tileSize, 4), Board.getInstance().toIcon(p).toString());
        }

        //Return fog
        return new ImageIcon(Icons.otherIcons.FOG.getImage().getScaledInstance(Position.tileSize, Position.tileSize, 4), Board.getInstance().toIcon(p).toString());
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
		ctrl.updatePlayers();
	}

	// Report
}