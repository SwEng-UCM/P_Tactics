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

public class SmokerTroop extends Troop {
	public SmokerTroop(Position pos, Player p) {
		super(pos, p);
		this._visionRange=5;
		this._abilityUses=3;
		this._moveRange=8;
		this._shootRange = 5;
		this._movesLeft=this._moveRange;
		_id = Utils.TroopUtils.SMOKER_TROOP_ID;
	}
	
	public SmokerTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		this._visionRange=5;
		this._abilityUses=3;
		this._moveRange=8;
		this._shootRange = 5;
		this._movesLeft=this._moveRange;
		_id = Utils.TroopUtils.SMOKER_TROOP_ID;
	}
	
	@Override
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
	public void nextTurn() {
		this._movesLeft = this._moveRange;
	}
	
	@Override
	public void activateAbility(Position pos) {
		Board.getInstance().addSmoke(pos);
		this._abilityUses--;
	}
	
	@Override
	public void deactivateAbility() {}

	@Override
	public void undoAbility(Position _abilityPos) {
		_abilityUses++;
		Board.getInstance().eraseSmoke(_abilityPos);
	}
	
	@Override
	public String toString() {
		return "S"  + super.toString();
	}
	
	@Override
	public ImageIcon toIcon() {
		if (_player.isMyTurn()) {
			if(_dir == Direction.UP) {
				return Icons.TroopIcons.SmokerIcons.TROOP_FACING_UP;
			}
			else if(_dir == Direction.DOWN) {
				return Icons.TroopIcons.SmokerIcons.TROOP_FACING_DOWN;
			}
			else if(_dir == Direction.LEFT) {
				return Icons.TroopIcons.SmokerIcons.TROOP_FACING_LEFT;
			}
			else if(_dir == Direction.RIGHT) {
				return Icons.TroopIcons.SmokerIcons.TROOP_FACING_RIGHT;
			}
		} else {
			if(_dir == Direction.UP) {
				return Icons.TroopIcons.SmokerIcons.ENEMY_TROOP_FACING_UP;
			}
			else if(_dir == Direction.DOWN) {
				return Icons.TroopIcons.SmokerIcons.ENEMY_TROOP_FACING_DOWN;
			}
			else if(_dir == Direction.LEFT) {
				return Icons.TroopIcons.SmokerIcons.ENEMY_TROOP_FACING_LEFT;
			}
			else if(_dir == Direction.RIGHT) {
				return Icons.TroopIcons.SmokerIcons.ENEMY_TROOP_FACING_RIGHT;
			}
		}
		
		return Icons.TroopIcons.SmokerIcons.TROOP_FACING_UP;
	}
}
