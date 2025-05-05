package PTactics.control;

import java.awt.event.MouseEvent;

import org.json.JSONObject;

import PTactics.utils.Position;

public class TroopInfo {
	String ID;
	Position pos;
	int movesLeft;
	int abilityLeft;
	public TroopInfo(String iD, Position pos, int movesLeft, int abilityLeft) {
		this.ID = iD;
		this.pos = pos;
		this.movesLeft = movesLeft;
		this.abilityLeft = abilityLeft;
	}
	public String getId() {
		// TODO Auto-generated method stub
		return ID;
	}
	public Position getPos() {
		// TODO Auto-generated method stub
		return pos;
	}
	public int getMovesLeft() {
		// TODO Auto-generated method stub
		return movesLeft;
	}
	public int abilityUsesLeft() {
		// TODO Auto-generated method stub
		return abilityLeft;
	}
	
	public JSONObject report() {
	    JSONObject obj = new JSONObject();
	    obj.put("ID", this.ID);
	    obj.put("x", this.pos.getX());
	    obj.put("y", this.pos.getY());
	    obj.put("movesLeft", this.movesLeft);
	    obj.put("abilitiesLeft", this.abilityLeft);
	    return obj;
	}

}
