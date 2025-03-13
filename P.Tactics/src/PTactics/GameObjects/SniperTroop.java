package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.List;
import PTactics.Game.Board;
import PTactics.Game.Game;
import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class SniperTroop extends Troop {
	private final int _VISION = Math.max(Game._boardLength, Game._boardWidth);
	private final int _MOVE = 3;
	private final int _USE = 3;
	private final int _DANGER = Math.max(Game._boardLength, Game._boardWidth);
	private final int _droneSide = 1;
	private final int _droneHeight = 1;
	private List<Position> _droneArea;
	
	public SniperTroop(Position pos, Player p) {
		super(pos, p);
		_visionRange = _VISION;
		_moveRange = _MOVE;
		_shootRange = _DANGER;
		_abilityUses = _USE;
        _movesLeft = _moveRange;
		_id = Utils.TroopUtils.SNIPER_TROOP_ID;
	}
	
	public SniperTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		_visionRange = _VISION;
		_moveRange = _MOVE;
		_shootRange = _DANGER;
		_abilityUses = _USE;
		_movesLeft = _moveRange;
		_id = Utils.TroopUtils.SNIPER_TROOP_ID;
	}

	public List<Position> visiblePositions() {
		List<Position> visiblePositions = new ArrayList<>();
		
		if (!isAlive()) {
			return visiblePositions;
		}
		visiblePositions.add(getPos());
		
		if (isAbility()) {
			visiblePositions.addAll(_droneArea);			
		}
		Position pos = new Position(getPos().getX(), getPos().getY());
		
		
		for (int i = 0; i < _visionRange; i++) {
			pos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
			if (!pos.isValid() || Board.getInstance().isSolid(pos))
				break;
			visiblePositions.add(pos);
		}
		
		return visiblePositions;	
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
	
	public String toString() {
		return "F" + super.toString();
	}
	
	public void activateAbility(Position pos) {
		_abilityActive = true;
		
		_droneArea = new ArrayList<>();
		for (int i = -_droneSide; i <= _droneSide; i++) {
			for (int j = -_droneHeight; j <= _droneHeight; j++) {
				Position areaPos = new Position(pos.getX() + i, pos.getY() + j);
				if (areaPos.isValid()) {
					_droneArea.add(new Position(pos.getX() + i, pos.getY() + j));					
				}
			}				
		}
	}

	@Override
	public void deactivateAbility() {
		_abilityActive = false;
	}

	@Override
	public void nextTurn() {
		_movesLeft = _moveRange;
		if (isAbility() && _abilityUses > 0) {
			_abilityUses--;
		}
		
		if (_abilityUses == 0) {
			deactivateAbility();
		}
	}

}
