package PTactics.GameObjects;

import PTactics.Game.Board;
import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class SmokerTroop extends Troop {
	public SmokerTroop(Position pos, Player p) {
		super(pos, p);
		this._visionRange=10;
		this._abilityUses=3;
		this._moveRange=5;
		this._shootRange = 5;
		this._movesLeft=this._moveRange;
		_id = Utils.TroopUtils.SMOKER_TROOP_ID;
	}
	
	public SmokerTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		this._visionRange=10;
		this._abilityUses=3;
		this._moveRange=5;
		this._shootRange = 5;
		this._movesLeft=this._moveRange;
		_id = Utils.TroopUtils.SMOKER_TROOP_ID;
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
