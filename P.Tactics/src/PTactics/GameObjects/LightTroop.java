package PTactics.GameObjects;

import PTactics.Game.BoardInterface;
import PTactics.Game.Player;
import PTactics.Utils.Position;

public class LightTroop extends Troop {
	int iFrames;
	
	public LightTroop(Position pos, Player p, BoardInterface BI) {
		super(pos, p, BI);
		this._visionRange = 10;
		this._shootRange = 5;
		this._moveRange = 20; 
		this._movesLeft = this._moveRange; 
		this._abilityUses = 100;
		this.iFrames = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activateAbility() {
		// TODO Auto-generated method stub
		this.iFrames = 5;
		this._abilityActive = true;
		
	}

	@Override
	public void deactivateAbility() {
		// TODO Auto-generated method stub
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
	@Override
	public String toString() {
		return "L";
	}

}
