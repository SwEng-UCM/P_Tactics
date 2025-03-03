package PTactics.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.Wall;
import PTactics.Utils.Position;

public class Board extends ConcurrentHashMap  <Position,GameObject>implements BoardInterface {
	int size; //what is this???
	
	public Board() {
		map1();
	}
	
	public void addObj(Position p, GameObject o) {
		this.put(p, o);
	}
	
	public Position getPosition(GameObject o) {
		Set <Position> sAux = this.keySet();
		for(Position p: sAux) {
			if(this.get(p).equals(o)) return p;
		}
		return null;
	}

	@Override
	public void setPosition(Position p1, Position p2, GameObject o) { // moves obj o from p1 to p2
		this.remove(p1, o);
		this.put(p2, o);
	}

	@Override
	public GameObject getGameObject(Position p) {
		return this.get(p);
	}
	
	public boolean isSolid(Position p) {
		if (get(p) == null) return false;
		return get(p).isSolid();
	}

	@Override
	public void eraseFromPos(Position p) throws IllegalArgumentException {
		if(!this.containsKey(p)) throw new IllegalArgumentException("Position not found in board");
		this.remove(p);
	}

	@Override
	public void eraseFromGO(GameObject o) {
		if(!this.containsValue(o)) throw new IllegalArgumentException("Object not found in board");
		this.remove(this.getPosition(o));
	}
	public void update() {
	    for (Map.Entry<Position, GameObject> entry : this.entrySet()) {
            entry.getValue().update();
        }
	}

	public String toString(Position p) {
		if(this.containsKey(p)) return this.get(p).toString();
		return " ";
	}

	//@Override // NOT NEEDED, SEE POSITION!
	/*public boolean isValid(Position pos) {
		if (pos.getX() < 0 || pos.getX() >= Game._boardWidth || pos.getY() < 0 || pos.getY() >= Game._boardLength)
			return false;
		return true;
	}*/
	
	private void map1() {
		this.addObj(new Position(3, 1), new Wall(new Position(3, 1), this));
		this.addObj(new Position(7, 2), new Wall(new Position(7, 2), this));
		this.addObj(new Position(2, 3), new Wall(new Position(2, 3), this));
		this.addObj(new Position(5, 4), new Wall(new Position(5, 4), this));
		this.addObj(new Position(9, 4), new Wall(new Position(9, 4), this));
		this.addObj(new Position(4, 6), new Wall(new Position(4, 6), this));
		this.addObj(new Position(8, 7), new Wall(new Position(8, 7), this));
		this.addObj(new Position(1, 9), new Wall(new Position(1, 9), this));
		this.addObj(new Position(6, 10), new Wall(new Position(6, 10), this));
	}
}
