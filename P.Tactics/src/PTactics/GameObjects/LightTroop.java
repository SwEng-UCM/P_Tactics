package PTactics.GameObjects;

import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class LightTroop extends Troop {
	int iFrames;
	
	public LightTroop(Position pos, Player p) {
		super(pos, p);
		this._visionRange = 10;
		this._shootRange = 5;
		this._moveRange = 20; 
		this._movesLeft = this._moveRange; 
		this._abilityUses = 100;
		this.iFrames = 0;
		_id = Utils.TroopUtils.LIGHT_TROOP_ID;
	}
	
	public LightTroop(Position pos, Player p, Direction dir) {
		super(pos, p);
		this._visionRange = 10;
		this._shootRange = 5;
		this._moveRange = 20; 
		this._movesLeft = this._moveRange; 
		this._abilityUses = 100;
		this.iFrames = 0;
		this._dir = dir;
		_id = Utils.TroopUtils.LIGHT_TROOP_ID;
	}

	@Override
	public void activateAbility() {

		this.iFrames = 5;
		this._abilityActive = true;
		
	}

	@Override
	public void deactivateAbility() {
		//iFrames = 0;
		this._abilityActive = false;
	}
	@Override
	public void onHit() {
		if(!this.isAbility()) alive = false; // this does not work well with other stuff, I think it should completely avoid the hit
	}
	@Override
	public void update() {
		if(this.iFrames < 1 && this.isAbility()) this.deactivateAbility();
		Move();
		this.iFrames--;
	}
	@Override
	public String toString() {
		return "L";
	}

	@Override
	public void nextTurn() {
		//deactivateAbility();
	}

}
