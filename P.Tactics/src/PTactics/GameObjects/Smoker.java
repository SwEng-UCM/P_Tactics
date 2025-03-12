package PTactics.GameObjects;

import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;

public class Smoker extends Troop {

	public Smoker(Position pos, Player p) {
		super(pos, p);
		this._visionRange=10;
		this._abilityUses=3;
		this._moveRange=5;
		this._movesLeft=this._moveRange;
	}
	
	public Smoker(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		this._visionRange=10;
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
	public String toString() {
		return "S";
	}
}
