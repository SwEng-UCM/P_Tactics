package PTactics.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public class Game {
	public static int _boardLength; 			//This is the first value (y)
	public static int _boardWidth;			//This is the second value (x)
	private BoardInterface _board;
	private List<Player> _players;
	private int _currPlayer;
	
	public Game(int length, int width){
		if(length <= 0 || width <= 0) throw new IllegalArgumentException("Map needs valid distance.");
		Game._boardLength = length;
		Game._boardWidth = width;
		Position._gameLength=length;
		Position._gameWidth=width;
		this._board = new Board(); // add walls here?
		this._players = new ArrayList<>();
		this._currPlayer = 0;
	}
	
	//Just in case
	Game(){
		this(10 ,10);
	}
	
	public void addNewElement(GameObject g, Position pos) {//la posicion tiene que venir adaptada de la vista humana a la vista maquina
		if(Objects.isNull(g)) throw new IllegalArgumentException("A null object cannot be added to game.");
		_board.addObj(pos, g);
	}

	
	GameObject getGameObject (Position pos) {
		return this._board.getGameObject(pos);
	}
	
	void eraseGameObject(Position pos) {
		_board.eraseFromPos(pos);
	}

	public String positionToString(Position p) {	//without  the not  null check the game breaks.
		if (_board.getGameObject(p)!=null&&_board.getGameObject(p).isSolid()) return _board.toString(p);
		
		if(_players.get(_currPlayer).isVisible(p.getX(), p.getY())) return _board.toString(p);
		return "*";
	}
	
	void addPlayer(Player p) {
		this._players.add(p);
	}
	
	Player getPlayer() {	//This should receive an index or smth. // this is illegal I feel like, make the call for a function of game and let it handle its players
		return this._players.get(_currPlayer);
	}
	// try this instead
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
		_board.update();
		updatePlayers();
	}

	public GameObject objectInPos(Position pos ) {
		return _board.getGameObject(pos);
	}
	
	public BoardInterface getBoard() {
		return _board;
	}
	
	public void  setPositionOnBoard(Position p1, Position p2, GameObject GO) 
	{
		this._board.setPosition(p1, p2, GO);
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