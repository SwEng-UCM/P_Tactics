package PTactics.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



import PTactics.GameObjects.GameObject;
import PTactics.Utils.Position;

public class Game {
	private final int _boardLength; 			//This is the first value (y)
	private final int _boardWidth;				//This is the second value (x)
	private GameObject[][] _GameObjectList;		
	
	Game(int lenght, int width){
		//TODO: Change exception to our own made exceptions.
		if(lenght <= 0 || width <= 0) throw new IllegalArgumentException("Map needs valid distance.");
		this._boardLength = lenght;
		this._boardWidth = width;
		this._GameObjectList = new GameObject[_boardLength][_boardWidth];
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
	
	public void addNewElement(GameObject g, Position pos) {
		if(Objects.isNull(g)) throw new IllegalArgumentException("A null object cannot be added to game.");
		_GameObjectList[pos.getY()][pos.getX()] = g;
	}
	
	GameObject getGameObject (Position pos) {
		return this._GameObjectList[pos.getY()][pos.getX()];
	}
	
	void eraseGameObject(Position pos) {
		_GameObjectList[pos.getY()][pos.getX()] = null;
	}
}
