package PTactics.model.gameObjects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GUI.Icons;

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
			} else if (visPos.isValid() && !Board.getInstance().isSolid(visPos)) {
				dangerPositions.add(visPos);
				break;
			}
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
		this._movesLeft = this._moveRange;
		deactivateAbility();
	}
	
	
	@Override
	public ImageIcon toIcon() {
		if (_player.isMyTurn()) {
			if(_dir == Direction.UP) {
				return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_UP;
			}
			else if(_dir == Direction.DOWN) {
				return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_DOWN;
			}
			else if(_dir == Direction.LEFT) {
				return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_LEFT;
			}
			else if(_dir == Direction.RIGHT) {
				return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_RIGHT;
			}
		} else {
			if(_dir == Direction.UP) {
				return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_UP;
			}
			else if(_dir == Direction.DOWN) {
				return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_DOWN;
			}
			else if(_dir == Direction.LEFT) {
				return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_LEFT;
			}
			else if(_dir == Direction.RIGHT) {
				return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_RIGHT;
			}
		}
		
		return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_UP;
	}
}
