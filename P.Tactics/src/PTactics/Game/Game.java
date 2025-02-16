package PTactics.Game;

import java.util.Objects;



import PTactics.GameObjects.GameObject;
import PTactics.Utils.Position;

public class Game {
	private final int _boardLength; 			//This is the first value (y)
	private final int _boardWidth;				//This is the second value (x)
	private BoardInterface board;		
	
	public Game(int lenght, int width){
		//TODO: Change exception to our own made exceptions.
		if(lenght <= 0 || width <= 0) throw new IllegalArgumentException("Map needs valid distance.");
		this._boardLength = lenght;
		this._boardWidth = width;
		this.board = new Board(); // add walls here?
	}
	
	//Just in case
	Game(){
		this(10 ,10);
	}
	
	public int getLength() {
		return this._boardLength;
	}
	
	public int getWidth() {
		return this._boardWidth;
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

	// TODO: pensar como hacer esto 
/*	public String positionToString(Position pos) {		
		if (_GameObjectList[pos.getX()][pos.getY()] == null) return " ";
		return _GameObjectList[pos.getX()][pos.getY()].toString();
	}
	*/
	public void update() {
		board.update();
	}
}
