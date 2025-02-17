package PTactics.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



import PTactics.GameObjects.GameObject;
import PTactics.Utils.Position;

public class Game {
	private final int boardLength; 			//This is the first value (y)
	private final int boardWidth;				//This is the second value (x)
	private BoardInterface board;
	private List<Player> players;
	int currPlayer;
	
	public Game(int lenght, int width){
		//TODO: Change exception to our own made exceptions.
		if(lenght <= 0 || width <= 0) throw new IllegalArgumentException("Map needs valid distance.");
		this.boardLength = lenght;
		this.boardWidth = width;
		this.board = new Board(); // add walls here?
		this.players = new ArrayList<>();
		this.currPlayer = 0;
	}
	
	//Just in case
	Game(){
		this(10 ,10);
	}
	
	public int getLength() {
		return this.boardLength;
	}
	
	public int getWidth() {
		return this.boardWidth;
	}
	
	public void addNewElement(GameObject g, Position pos) {//la posicion tiene que venir adaptada de la vista humana a la vista maquina
		if(Objects.isNull(g)) throw new IllegalArgumentException("A null object cannot be added to game.");
		board.addObj(pos, g);
	}
	
	GameObject getGameObject (Position pos) {
		return this.board.getGameObject(pos);
	}
	
	void eraseGameObject(Position pos) {
		board.erraseFromPos(pos);
	}

	public String positionToString(Position p) {		
		if(players.get(currPlayer).isVisible(p.getX(), p.getY())) return board.toString(p);
		return "X";
	}
	
	void addPlayer(Player p) {
		this.players.add(p);
	}
	
	Player getPlayer() {	//This should receive an index or smth.
		return this.players.getFirst();
	}
	
	public void update() {
		board.update();
	}
}
