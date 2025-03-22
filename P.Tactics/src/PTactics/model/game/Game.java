package PTactics.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import PTactics.control.maps.MapSelector;
import PTactics.model.gameObjects.GameObject;
import PTactics.model.gameObjects.SmokerTroop;
import PTactics.model.gameObjects.SniperTroop;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;

public class Game implements Observable<GameObserver>{
	public static int _boardLength; // This is the first value (y)
	public static int _boardWidth; // This is the second value (x)
	private List<Player> _players;
	private int _currPlayer;
	private Troop _currTroop;
	private List<GameObserver> _observers;

	public Game() {
		Game._boardLength = MapSelector.getLength();
		Game._boardWidth = MapSelector.getWidth();
		Position._gameLength = MapSelector.getLength();
		Position._gameWidth = MapSelector.getWidth();
		this._players = new ArrayList<>();
		this._currPlayer = 0;
		_observers = new ArrayList<>();
	}

	public void addNewElement(GameObject g, Position pos) {
		if (Objects.isNull(g))
			throw new IllegalArgumentException("A null object cannot be added to game.");
		Board.getInstance().addObj(pos, g);
	}

	public GameObject getGameObject(Position pos) {
		return Board.getInstance().getGameObject(pos);
	}

	void eraseGameObject(Position pos) {
		Board.getInstance().eraseFromPos(pos);
	}
	
	public Troop currentTroop() {
		return _currTroop;
	}

	public String positionToString(Position p) { 
		boolean visible = _players.get(_currPlayer).isVisible(p.getX(), p.getY());
		if (Board.getInstance().getGameObject(p)!=null&&!Board.getInstance().getGameObject(p).isSeeThrough()) {
			return Board.getInstance().toString(p);
		}
		
		if (visible) {
			if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isAlive()) {
				if (_players.get(_currPlayer).lastTurnKill(p)) {
					return Utils.TroopUtils.TROOP_DEAD;
				}
				return Utils.TroopUtils.TROOP_DEAD;				//Returning dead soldier (not solid not alive entities)
			}
			if (_players.get(_currPlayer).isVisible(p.getX(), p.getY())) {
				return Board.getInstance().toString(p);			//Returning actual soldiers (alive not solid)
			}
			
		}
		// just in case in the future a new way of killing without seeing is added
		if (Board.getInstance().getGameObject(p) != null && !Board.getInstance().getGameObject(p).isAlive()) {
			if (_players.get(_currPlayer).lastTurnKill(p)) {
				return "†";
			}
		}
		return "*";												//Returning fog of war	(not visible)
	}

	public void addPlayer(Player p) {
		this._players.add(p);
	}

	public Player getPlayer() {
		return this._players.get(_currPlayer);
	}

	public void addTroops(Troop t) {
		addNewElement(t, t.getPos());
		_players.get(_currPlayer).addTroops(t);
	}

	public void updatePlayers() {
		for (Player p : _players) {
			p.update();
		}
		for (GameObserver o : _observers) {
			o.onPlayersUpdate(this);
		}
	}

	public void update() { // total update, only called on the setup
		Board.getInstance().update();
		updatePlayers();
	}
	
	public void updateBoard() {
		Board.getInstance().update();
		for (GameObserver o : _observers) {
			o.onBoardUpdate(Board.getInstance());
		}
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
		_players.get(_currPlayer).endTurn();		
		_players.get(_currPlayer).clearKills();		// Im not proud of what I have done but this is just so easy and comfortable. 
		_players.get(_currPlayer).update();			//this is illegal (nerd emoji) idgaf rn
		_currPlayer++;
		if (_currPlayer >= _players.size()) {
			_currPlayer = 0;
		}
		_players.get(_currPlayer).startOfTurnDeadCheck();
		_players.get(_currPlayer).startTurn();	
		
		for (GameObserver o : _observers) {
			o.onNextTurn(this);
		}
	}
	
	public boolean isTroopSelected() {
		return !(_currTroop == null);
	}

	public void selectTroop(Position pos) throws Exception{
		GameObject t = Board.getInstance().getGameObject(pos);
		if (Objects.isNull(t)) {
			throw new Exception(Utils.MsgErrors.INVALID_SELECTION); // Have to check if it exists (is a GO)
		}
		if (!t.isAlive()) {
			throw new Exception(Utils.MsgErrors.INVALID_SELECTION); // Have to check if it is a troop alive (walls
																	// and dead troops will return false)
		}
		if (!((Troop) t).getPlayer().equals(_players.get(_currPlayer).getId())) {
			throw new Exception(Utils.MsgErrors.INVALID_SELECTION); // Have to check that it belongs to the player
																	// (sorry for the casting)
		}		
		
		_currTroop = (Troop) t;
		
		for (GameObserver o : _observers) {
			o.onTroopSelection(this);
		}		
	}
	
	public boolean canMove(Position pos) {
		return _currTroop.isAlive() && (!(_currTroop.getPos().getX() == pos.getX()) || !(_currTroop.getPos().getY() == pos.getY()));
	}

	public void moveTroop(Position pos) throws IllegalArgumentException{
		_currTroop.AddToMove(pos);
		_currTroop.update();
		updatePlayers();
		for (GameObserver o : _observers) {
			o.onTroopAction(this);
		}
	}

	public void troopAbility(Position pos) throws Exception {
		if (_currTroop.isAbility()) {
			throw new Exception("Ability is already in use");
		}
		else if(pos.getX() == -1 && pos.getY() == -1 && _currTroop.getId() == Utils.TroopUtils.LIGHT_TROOP_ID) {
			_currTroop.activateAbility();
		}
		else {
			
			if (_currTroop.abilityUsesLeft() == 0) {
				throw new Exception("No uses left for the ability");
			}
			if (_currTroop.getId() == Utils.TroopUtils.SNIPER_TROOP_ID) {
				SniperTroop st = (SniperTroop) _currTroop;
				st.activateAbility(pos);
			}
			if (_currTroop.getId() == Utils.TroopUtils.SMOKER_TROOP_ID) {
				SmokerTroop st = (SmokerTroop) _currTroop;
				st.activateAbility(pos);
			}
			else {
				_currTroop.activateAbility();
			}
		}
		
		for (GameObserver o : _observers) {
			o.onTroopAction(this);
		}
	}

	public void takeAim(Direction _dirToAim) {
		_currTroop.takeAim(_dirToAim);
		for (GameObserver o : _observers) {
			o.onTroopAction(this);
		}	
	}
	
	public void troopLook(Direction dir) {
		_currTroop.setDirection(dir);
		for (GameObserver o : _observers) {
			o.onTroopAction(this);
		}
	}

	public void dropTroop() {
		_currTroop = null;
		for (GameObserver o : _observers) {
			o.onTroopSelection(this);
		}
	}

	public void setTroop(Troop t) {
		_currTroop = t;
		for (GameObserver o : _observers) {
			o.onTroopSelection(this);
		}
	}
	
	public void onDeadTroopSelected() {
		if (_currTroop != null && !_currTroop.isAlive()) {
        	_currTroop = null;
        }
	}
	
	public Troop getTroop() {
		return _currTroop;
	}

	public void InicializeTurns() {
		_players.get(0).startTurn();		
	}

	@Override
	public void addObserver(GameObserver o) {
		_observers.add(o);		
	}

	@Override
	public void removeObserver(GameObserver o) {
		_observers.remove(o);
	}
	
}