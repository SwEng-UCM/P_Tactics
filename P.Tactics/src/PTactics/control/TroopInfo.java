package PTactics.control;

import java.awt.event.MouseEvent;

import PTactics.utils.Position;

public class TroopInfo {
	String ID;
	Position pos;
	int movesLeft;
	int abilityLeft;
	public TroopInfo(String iD, Position pos, int movesLeft, int abilityLeft) {
		// TODO Auto-generated constructor stub
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

}
