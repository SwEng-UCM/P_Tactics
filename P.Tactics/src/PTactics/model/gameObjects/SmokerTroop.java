package PTactics.model.gameObjects;

import java.util.ArrayList;
import java.util.List;

import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;

public class SmokerTroop extends Troop {
	public SmokerTroop(Position pos, Player p) {
		super(pos, p);
		initVars();
		_id = Utils.TroopUtils.SMOKER_TROOP_ID;
	}
	
	public SmokerTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		initVars();
		_id = Utils.TroopUtils.SMOKER_TROOP_ID;
	}
	public void initVars() 
	{
		this._visionRange=5;
		this._abilityUses=3;
		this._moveRange=8;
		this._shootRange = 5;
		this._movesLeft=this._moveRange;
	}
	public void activateAbility(Position position) {
		Board.getInstance().smoke(position);
		this._abilityUses--;
	}
	
	public List<Position> dangerPositions() {
		List<Position> dangerPositions = new ArrayList<>();
		
		if (!_aiming) {
			return dangerPositions;
		}
		
		Position visPos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
		for (int i = 0; i < _shootRange; i++) {
			if (visPos.isValid() && Board.getInstance().isSeeThrough(visPos)) {
				dangerPositions.add(visPos);
				visPos = new Position(visPos.getX() + _dir.getX(), visPos.getY() + _dir.getY());
			} else break;
		}
		
		return dangerPositions;	
	}
	
	@Override
	public void deactivateAbility() {
	}
	
	@Override
	public String toString() {
		return "S"  + super.toString();
	}

	@Override
	public void nextTurn() {
	
		
	}
}
