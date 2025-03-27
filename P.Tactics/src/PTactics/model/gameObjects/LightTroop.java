package PTactics.model.gameObjects;

import java.util.ArrayList;
import java.util.List;

import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;

public class LightTroop extends Troop {
	int iFrames;
	
	public LightTroop(Position pos, Player p) {
		super(pos, p);
		initVars();
		_id = Utils.TroopUtils.LIGHT_TROOP_ID;
	}
	
	public LightTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		initVars();
		_id = Utils.TroopUtils.LIGHT_TROOP_ID;
	}
	public void initVars() 
	{
		this._visionRange = 4;
		this._shootRange = 4;
		this._moveRange = 8; 
		this._movesLeft = this._moveRange; 
		this._abilityUses = 1;		//Why a 100?
		this.iFrames = 0;
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
	public void update() {
		if(this.iFrames < 1 && this.isAbility()) this.deactivateAbility();
		Move();
		if (_player.getDanger(getPos()) && !this.isAbility()) {
			onHit();
		}
		this.iFrames--;
	}
	@Override
	public String toString() {
		return "L"+ super.toString();
	}

	@Override
	public void nextTurn() {
		deactivateAbility();
	}

}
