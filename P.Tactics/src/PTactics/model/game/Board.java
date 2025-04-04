package PTactics.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;

import org.json.JSONArray;
import PTactics.control.maps.MapSelector;
import PTactics.model.gameObjects.GameObject;
import PTactics.model.gameObjects.SmokeObject;
import PTactics.model.gameObjects.Wall;
import PTactics.utils.Position;
import PTactics.view.GUI.Icons;

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
	
	public JSONArray report() {
		JSONArray mapReport = new JSONArray();
		for(GameObject g : this.values()) {
			mapReport.put(g.report());
		}
		return mapReport;
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
		List<Position> deadGuys = new ArrayList<Position>();
	    for (Map.Entry<Position, GameObject> entry : this.entrySet()) {
	    	if (entry.getValue() != null && !entry.getValue().isAlive()) {
	    		deadGuys.add(entry.getKey());
	    	}
	    	entry.getValue().update();	    		
        }
	    for (Position p : deadGuys) {
	    	_board.remove(p);
	    }
	}

	public String toString(Position p) {
		if(this.containsKey(p)) return this.get(p).toString();
		return " ";
	}
	
	public ImageIcon toIcon(Position p) {
		if(this.containsKey(p)) return this.get(p).toIcon();
		return Icons.otherIcons.FLOOR;
	}
	
	public void nextTurn() 
	{
		for (Map.Entry<Position, GameObject> entry : this.entrySet()) {
            entry.getValue().nextTurn();
        }
	}
	public void smoke(Position pos) 
	{
		Position center= pos;
		int range = 1;
		for (int dx = -range; dx <= range; dx++) {
	        for (int dy = -range; dy <= range; dy++) {
	            Position smokePos = new Position(center.getX() + dx, center.getY() + dy);
	            if (pos.isValid() && !Board.getInstance().isSolid(pos) && !this.containsKey(smokePos)) {
	                SmokeObject smoke= new SmokeObject(smokePos);
	                this.addObj(smokePos, smoke);
	            }
	        }
	    }
	}
	
	private void _addMap() {
		for(Position p : MapSelector.getWalls()) {
			this.addObj(p, new Wall(p));
		}
	}
}
