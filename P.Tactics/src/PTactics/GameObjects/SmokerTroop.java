package PTactics.GameObjects;

import PTactics.Game.Board;
import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

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
	@Override
	public void deactivateAbility() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		return "S"  + super.toString();
	}

	@Override
	public void nextTurn() {
	
		
	}
}
