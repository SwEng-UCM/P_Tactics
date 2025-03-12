package PTactics.GameObjects;

import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;

public class LightTroop extends Troop {
	int iFrames;
	private final String  _ID = "light";
	
	public LightTroop(Position pos, Player p) {
		super(pos, p);
		this._visionRange = 10;
		this._shootRange = 5;
		this._moveRange = 20; 
		this._movesLeft = this._moveRange; 
		this._abilityUses = 100;
		_id = _ID;
	}
	
	public LightTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		this._visionRange = 10;
		this._shootRange = 5;
		this._moveRange = 20; 
		this._movesLeft = this._moveRange; 
		this._abilityUses = 100;
		_id = _ID;
	}

	@Override
	public void activateAbility() {

		this.iFrames = 5;
		this._abilityActive = true;
		
	}

	@Override
	public void deactivateAbility() {
		this._abilityActive = false;
	}
	@Override
	public void onHit() {
		if(!this.isAbility()) alive = false;
	}
	@Override
	public void update() {
		if(this.iFrames < 1 && this.isAbility()) this.deactivateAbility();
		Move();
		this.iFrames--;
	}

}
