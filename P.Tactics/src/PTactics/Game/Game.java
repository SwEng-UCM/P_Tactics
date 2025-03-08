package PTactics.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public class Game {
	public static int _boardLength; 			//This is the first value (y)
	public static int _boardWidth;			//This is the second value (x)
	private List<Player> _players;
	private int _currPlayer;
	
	public Game(int length, int width){
		if(length <= 0 || width <= 0) throw new IllegalArgumentException("Map needs valid distance.");
		Game._boardLength = length;
		Game._boardWidth = width;
		Position._gameLength=length;
		Position._gameWidth=width;
		this._players = new ArrayList<>();
		this._currPlayer = 0;
	}
	
	//Just in case
	Game(){
		this(10 ,10);
	}
	
	public void addNewElement(GameObject g, Position pos) {
		if(Objects.isNull(g)) throw new IllegalArgumentException("A null object cannot be added to game.");
		Board.getInstance().addObj(pos, g);
	}

	
	GameObject getGameObject (Position pos) {
		return Board.getInstance().getGameObject(pos);
	}
	
	void eraseGameObject(Position pos) {
		Board.getInstance().eraseFromPos(pos);
	}

	public String positionToString(Position p) {	//without  the not  null check the game breaks.
		boolean visible = _players.get(_currPlayer).isVisible(p.getX(), p.getY());
		if (Board.getInstance().getGameObject(p)!=null&&Board.getInstance().getGameObject(p).isSolid()) return Board.getInstance().toString(p);
		if (visible) {
			if (Board.getInstance().getGameObject(p)!=null&&!Board.getInstance().getGameObject(p).isAlive()) {
				return " ";
			}
			if(_players.get(_currPlayer).isVisible(p.getX(), p.getY())) return Board.getInstance().toString(p);
		}
		return "*";
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

	public GameObject objectInPos(Position pos ) {
		return Board.getInstance().getGameObject(pos);
	}
	
	public BoardInterface getBoard() {
		return Board.getInstance();
	}
	
	public void  setPositionOnBoard(Position p1, Position p2, GameObject GO) 
	{
		Board.getInstance().setPosition(p1, p2, GO);
	}
	
	public int getNumPlayer() { //Human view
		return this._currPlayer + 1;
	}
	
	public void nextTurn() {
		_currPlayer++;
		if(_currPlayer >= _players.size()) {
			_currPlayer = 0;
		}
	}
}