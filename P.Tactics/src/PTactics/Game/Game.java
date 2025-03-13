package PTactics.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.Troop;
import PTactics.Maps.MapSelector;
import PTactics.Utils.Position;

public class Game {
	public static int _boardLength; // This is the first value (y)
	public static int _boardWidth; // This is the second value (x)
	private List<Player> _players;
	private int _currPlayer;

	public Game() {
		Game._boardLength = MapSelector.getLength();
		Game._boardWidth = MapSelector.getWidth();
		Position._gameLength = MapSelector.getLength();
		Position._gameWidth = MapSelector.getWidth();
		this._players = new ArrayList<>();
		this._currPlayer = 0;
	}

	public void addNewElement(GameObject g, Position pos) {
		if (Objects.isNull(g))
			throw new IllegalArgumentException("A null object cannot be added to game.");
		Board.getInstance().addObj(pos, g);
	}

	GameObject getGameObject(Position pos) {
		return Board.getInstance().getGameObject(pos);
	}

	void eraseGameObject(Position pos) {
		Board.getInstance().eraseFromPos(pos);
	}

	public String positionToString(Position p) { // without the not null check the game breaks.
		boolean visible = _players.get(_currPlayer).isVisible(p.getX(), p.getY());
		if (_players.get(_currPlayer).lastTurnKill(p)) {
			return "†";
		}
		if (Board.getInstance().getGameObject(p)!=null&&!Board.getInstance().getGameObject(p).isSeeThrough()) return Board.getInstance().toString(p);
		if (visible) {
			if (Board.getInstance().getGameObject(p)!=null&&!Board.getInstance().getGameObject(p).isAlive()) {
				return " ";
			}
			if(_players.get(_currPlayer).isVisible(p.getX(), p.getY())) return Board.getInstance().toString(p);
		}
		if (visible) {
			if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isAlive()) {
				return " ";										//Returning dead soldier (not solid not alive entities)
			}
			if (_players.get(_currPlayer).isVisible(p.getX(), p.getY())) {
				return Board.getInstance().toString(p);			//Returning actual soldiers (alive not solid)
			}
		}
		return "*";												//Returning fog of war	(not visible)
	}

	void addPlayer(Player p) {
		this._players.add(p);
	}

	Player getPlayer() {
		return this._players.get(_currPlayer);
	}

	public void addTroops(Troop t) {
		addNewElement(t, t.getPos());
		_players.get(_currPlayer).addTroops(t);
	}

	private void updatePlayers() {
		for (Player p : _players) {
			p.update();
		}
	}

	public void update() {
		Board.getInstance().update();
		updatePlayers();
	}

	public GameObject objectInPos(Position pos) {
		return Board.getInstance().getGameObject(pos);
	}

	public BoardInterface getBoard() {
		return Board.getInstance();
	}

	public void setPositionOnBoard(Position p1, Position p2, GameObject GO) {
		Board.getInstance().setPosition(p1, p2, GO);
	}

	public int getNumPlayer() { // Human view
		return this._currPlayer + 1;
	}

	public void nextTurn() {
		Board.getInstance().nextTurn();
		//_players.get(_currPlayer).nextTurn(); 	//this is illegal (nerd emoji) idgaf rn
		_players.get(_currPlayer).clearKills();		// Im not proud of what I have done but this is just so easy and comfortable. 
		_players.get(_currPlayer).update();			//this is illegal (nerd emoji) idgaf rn
		_currPlayer++;
		if (_currPlayer >= _players.size()) {
			_currPlayer = 0;
		}
	}
}