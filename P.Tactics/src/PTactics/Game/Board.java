package PTactics.Game;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import PTactics.GameObjects.GameObject;
import PTactics.Utils.Position;

public class Board extends LinkedHashMap <Position,GameObject>implements BoardInterface {


	
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
	public GameObject whatInPos(Position p) {	
		return this.get(p);
	}

}
