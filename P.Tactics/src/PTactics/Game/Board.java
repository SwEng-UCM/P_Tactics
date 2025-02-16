package PTactics.Game;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import PTactics.GameObjects.GameObject;
import PTactics.Utils.Position;

public class Board extends LinkedHashMap <Position,GameObject>implements BoardInterface {
	int size;
	
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
		return this.get(p).isSolid();
	}

	@Override
	public void erraseFromPos(Position p) throws IllegalArgumentException {
		if(!this.containsKey(p)) throw new IllegalArgumentException("Position not found in board");
		this.remove(p);
	}

	@Override
	public void erraseFromGO(GameObject o) {
		if(!this.containsValue(o)) throw new IllegalArgumentException("Object not found in board");
		this.remove(this.getPosition(o));
	}
	public void update() {
		Set <Position> sAux = this.keySet();
		for(Position p: sAux) {
			this.get(p).update();
		}
	}
	public String toString(Position p) {
		String s = this.get(p).toString(); // or getIcon mabye?
		return s == null? " " : s; // mabye centralize " " to messages?
	}
}
