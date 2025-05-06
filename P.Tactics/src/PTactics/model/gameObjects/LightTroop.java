package PTactics.model.gameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;

import org.json.JSONObject;

import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GUI.Icons;

public class LightTroop extends Troop {
	private int _iFrames;

	public LightTroop(Position pos, Player p) {
		super(pos, p);
		this._visionRange = 4;
		this._shootRange = 4;
		this._moveRange = 8;
		this._movesLeft = this._moveRange;
		this._abilityUses = 1;
		this._iFrames = 0;
		_id = Utils.TroopUtils.LIGHT_TROOP_ID;
	}

	public LightTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		this._visionRange = 4;
		this._shootRange = 4;
		this._moveRange = 8;
		this._movesLeft = this._moveRange;
		this._abilityUses = 1;
		this._iFrames = 0;
		_id = Utils.TroopUtils.LIGHT_TROOP_ID;
	}

	public LightTroop(Position pos, Player p, Direction dir, int iFrames) {
		super(pos, p, dir);
		this._visionRange = 4;
		this._shootRange = 4;
		this._moveRange = 8;
		this._movesLeft = this._moveRange;
		this._abilityUses = 1;
		this._iFrames = 0;
		_id = Utils.TroopUtils.LIGHT_TROOP_ID;
		this._abilityUses = 0;
		this._iFrames = iFrames;
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
	public void update() {
		if (this._iFrames < 1 && this.isAbility()) {
			deactivateAbility();
		}
		
		if(!Objects.isNull(currentMove)) {
			currentMove.move();
			if (_player.getDanger(getPos()) && !this.isAbility()) {
				onHit();
			}
			_iFrames--;
		}
	}

	@Override
	public void nextTurn() {
		this._movesLeft = this._moveRange;
		deactivateAbility();
		this._abilityUses = 1;
	}

	@Override
	public void activateAbility(Position pos) {
		this._iFrames = 3;
		this._abilityActive = true;
	}

	@Override
	public void deactivateAbility() {
		this._abilityActive = false;
		this._abilityUses--;
	}

	@Override
	public void undoAbility(Position _abilityPos) {
		super.undoAbility(_abilityPos);
		_iFrames = 0;
	}

	@Override
	public String toString() {
		return "L" + super.toString();
	}

	@Override
	public ImageIcon toIcon() {
		if (_player.isMyTurn()) {
			if (_dir == Direction.UP) {
				return isAbility() ? Icons.TroopIcons.LightTroopIcons.TROOP_FACING_UP_DASH
						: Icons.TroopIcons.LightTroopIcons.TROOP_FACING_UP;
			} else if (_dir == Direction.DOWN) {
				return isAbility() ? Icons.TroopIcons.LightTroopIcons.TROOP_FACING_DOWN_DASH
						: Icons.TroopIcons.LightTroopIcons.TROOP_FACING_DOWN;
			} else if (_dir == Direction.LEFT) {
				return isAbility() ? Icons.TroopIcons.LightTroopIcons.TROOP_FACING_LEFT_DASH
						: Icons.TroopIcons.LightTroopIcons.TROOP_FACING_LEFT;
			} else if (_dir == Direction.RIGHT) {
				return isAbility() ? Icons.TroopIcons.LightTroopIcons.TROOP_FACING_RIGHT_DASH
						: Icons.TroopIcons.LightTroopIcons.TROOP_FACING_RIGHT;
			}
		} else {
			if (_dir == Direction.UP) {
				return Icons.TroopIcons.LightTroopIcons.ENEMY_TROOP_FACING_UP;
			} else if (_dir == Direction.DOWN) {
				return Icons.TroopIcons.LightTroopIcons.ENEMY_TROOP_FACING_DOWN;
			} else if (_dir == Direction.LEFT) {
				return Icons.TroopIcons.LightTroopIcons.ENEMY_TROOP_FACING_LEFT;
			} else if (_dir == Direction.RIGHT) {
				return Icons.TroopIcons.LightTroopIcons.ENEMY_TROOP_FACING_RIGHT;
			}
		}

		return Icons.TroopIcons.LightTroopIcons.TROOP_FACING_UP;
	}

	@Override
	public JSONObject report() {
		JSONObject troopReport = super.report();
		troopReport.put("iFrames", _iFrames);
		return troopReport;
	}
}
