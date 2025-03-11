package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.List;

import PTactics.Game.BoardInterface;
import PTactics.Game.Game;
import PTactics.Game.Player;
import PTactics.Utils.Position;

public class SniperTroop extends Troop {

	private final int _VISION = Math.max(Game._boardLength, Game._boardWidth);
	private final int _MOVE = 3;
	private final int _USE = 3;
	private final int _DANGER = Math.max(Game._boardLength, Game._boardWidth);
	
	public SniperTroop(Position pos, Player p, BoardInterface BI) {
		super(pos, p, BI);
		_visionRange = _VISION;
		_moveRange = _MOVE;
		_shootRange = _DANGER;
		_abilityUses = _USE;
	}

	public List<Position> visiblePositions() {
		List<Position> visiblePositions = new ArrayList<>();
		
		if (!isAlive()) {
			return visiblePositions;
		}
		visiblePositions.add(getPos());
		Position pos = new Position(getPos().getX(), getPos().getY());
		
		
		for (int i = 0; i < _visionRange; i++) {
			pos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
			if (!pos.isValid() || BI.isSolid(pos))
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
		for (int i = 0; i < _shootRange; i++) {		// TODO: maybe change vision range
			if (visPos.isValid() && !BI.isSolid(visPos)) {
				dangerPositions.add(visPos);
				visPos = new Position(visPos.getX() + _dir.getX(), visPos.getY() + _dir.getY());
			} else break;
			
		}
		
		return dangerPositions;	
	}
	
	@Override
	public void activateAbility() {
		_abilityActive = true;
	}

	@Override
	public void deactivateAbility() {
		_abilityActive = false;
	}

}
