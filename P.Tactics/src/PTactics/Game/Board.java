package PTactics.Game;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.SmokeObject;
import PTactics.GameObjects.Wall;
import PTactics.Maps.MapSelector;
import PTactics.Utils.Position;

public class Board extends ConcurrentHashMap  <Position,GameObject>implements BoardInterface {
	private static final long serialVersionUID = 1L;
	private static Board _board;
	
	private Board() {
		_addMap();
	}
	
	public static BoardInterface getInstance() {
		if(Objects.isNull(_board)) {
			_board = new Board();
		}
				
		return _board;
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
	public boolean isSeeThrough(Position p) {
		if (get(p) == null) return true;
		return get(p).isSeeThrough();
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
	
	public void Smoke(Position pos) 
	{
		Position center= pos;
		int range = 2;
		for (int dx = -range; dx <= range; dx++) {
	        for (int dy = -range; dy <= range; dy++) {
	            Position smokePos = new Position(center.getX() + dx, center.getY() + dy);
	            if (pos.isValid() && !Board.getInstance().isSolid(pos)) {
	                SmokeObject smoke= new SmokeObject(smokePos);
	                this.addObj(smokePos, smoke);
	            }
	        }
	    }
	}
	//@Override // NOT NEEDED, SEE POSITION!
	/*public boolean isValid(Position pos) {
		if (pos.getX() < 0 || pos.getX() >= Game._boardWidth || pos.getY() < 0 || pos.getY() >= Game._boardLength)
			return false;
		return true;
	}*/
	
	private void _addMap() {
		for(Position p : MapSelector.getWalls()) {
			this.addObj(p, new Wall(p));
		}
	}
}
