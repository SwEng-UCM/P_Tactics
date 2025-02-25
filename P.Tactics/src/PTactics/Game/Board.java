package PTactics.Game;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import PTactics.GameObjects.GameObject;
import PTactics.Utils.Position;

public class Board extends LinkedHashMap <Position,GameObject>implements BoardInterface {
	int size; //what is this???
	
	public Board() {
		// perhaps create an init consrtuctor to add the walls and troops 
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
	public void update() {//this needs to be rewritten as an iterator.
		/*Set <Position> sAux = this.keySet();
		for(Position p: sAux) {
			this.get(p).update();
		}*/
		Iterator<Map.Entry<Position, GameObject>> iterator = this.entrySet().iterator();
	    while (iterator.hasNext()) {
	        iterator.next().getValue().update();
	    }
	}
	public String toString(Position p) {
		if(this.containsKey(p)) return this.get(p).toString();
		return " ";
	}

	@Override
	public boolean isValid(Position pos) {
		if (pos.getX() < 0 || pos.getX() >= Game._boardWidth || pos.getY() < 0 || pos.getY() >= Game._boardLength)
			return false;
		return true;
	}
}
