package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import PTactics.Game.BoardInterface;
import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class Cloaker extends Troop {

	public Cloaker(Position pos, Player p, BoardInterface BI) {
		super(pos, p, BI);
		this._visionRange=2;
		this._abilityUses=3;
		this._moveRange=5;
		this._movesLeft=this._moveRange;
	}

	@Override
	public void activateAbility() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivateAbility() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Position> visiblePositions() {
	    List<Position> visiblePositions = new ArrayList<>();
	    
	    if (!isAlive()) {
	        return visiblePositions;
	    }
	    
	    Position center = getPos();// Vision range as radius (half of the diameter)
	    
	    for (int dx = -_visionRange; dx <= _visionRange; dx++) {
	        for (int dy = -_visionRange; dy <= _visionRange; dy++) {
	            Position pos = new Position(center.getX() + dx, center.getY() + dy);
	            if (pos.isValid() && !BI.isSolid(pos)) {
	                visiblePositions.add(pos);
	            }
	        }
	    }
	    
	    return visiblePositions;
	}
	@Override
	public String toString() {
		return "C";
	}
}
