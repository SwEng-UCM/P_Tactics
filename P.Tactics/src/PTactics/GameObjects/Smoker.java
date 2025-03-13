package PTactics.GameObjects;

import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class Smoker extends Troop {
	private Position smokePos;
	public Smoker(Position pos, Player p) {
		super(pos, p);
		this._visionRange=10;
		this._abilityUses=3;
		this._moveRange=5;
		this._movesLeft=this._moveRange;
		_id = Utils.TroopUtils.SMOKER_TROOP_ID;
	}
	
	public Smoker(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		this._visionRange=10;
		this._abilityUses=3;
		this._moveRange=5;
		this._movesLeft=this._moveRange;
		_id = Utils.TroopUtils.SMOKER_TROOP_ID;
	}

	@Override
	public void activateAbility() {
		
		
	}
	public void setSmokePosition() 
	{
		
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
		// TODO Auto-generated method stub
		
	}
}
