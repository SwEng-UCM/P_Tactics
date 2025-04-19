package PTactics.model.gameObjects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONObject;

import PTactics.model.game.Board;
import PTactics.model.game.Game;
import PTactics.model.game.Player;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GUI.Icons;

public class SniperTroop extends Troop {
	private  int _droneSide;
	private  int _droneHeight;
	private List<Position> _droneArea;
	
	public SniperTroop(Position pos, Player p) {
		super(pos, p);
		initVars();
		_id = Utils.TroopUtils.SNIPER_TROOP_ID;
	}
	
	public SniperTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		initVars();
		_id = Utils.TroopUtils.SNIPER_TROOP_ID;
	}
	
	public SniperTroop(Position pos, Player p, Direction dir, List<Position> area) {
		super(pos, p, dir);
		initVars();
		_id = Utils.TroopUtils.SNIPER_TROOP_ID;
		_droneArea = area;
		this._abilityActive = true;
		this._abilityUses = 0;
	}
	
	public void initVars() 
	{
		_visionRange = Math.max(Game._boardLength, Game._boardWidth);;
		_moveRange = 3;
		_shootRange = Math.max(Game._boardLength, Game._boardWidth);
		_abilityUses = 3;
        _movesLeft = _moveRange;
        _droneSide = 1;
        _droneHeight = 1;
        _droneArea = new ArrayList<>();
    }
	
	@Override
	public JSONObject report() {
		JSONObject troopReport = super.report();
		
		if(!_droneArea.isEmpty()) {
			JSONArray droneArea = new JSONArray();
			for(Position p : _droneArea) {
				JSONObject jo = new JSONObject();
				jo.put("PositionX", p.getX());
				jo.put("PositionY", p.getY());
				droneArea.put(jo);
			}
			troopReport.put("DroneArea", droneArea);
		}
		
		return troopReport;
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
			if (!pos.isValid() || !Board.getInstance().isSeeThrough(pos)) {
				if (!Board.getInstance().isSolid(pos)) {
					visiblePositions.add(pos);
				}
				break;					
			}
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
			} else if (visPos.isValid() && !Board.getInstance().isSolid(visPos)) {
				dangerPositions.add(visPos);
				break;
			}
		}
		
		return dangerPositions;	
	}
	
	public String toString() {
		return "F" + super.toString();
	}
	@Override
	public ImageIcon toIcon() {
		if (_player.isMyTurn()) {
			if(_dir == Direction.UP) {
				return Icons.TroopIcons.SniperIcons.TROOP_FACING_UP;
			}
			else if(_dir == Direction.DOWN) {
				return Icons.TroopIcons.SniperIcons.TROOP_FACING_DOWN;
			}
			else if(_dir == Direction.LEFT) {
				return Icons.TroopIcons.SniperIcons.TROOP_FACING_LEFT;
			}
			else if(_dir == Direction.RIGHT) {
				return Icons.TroopIcons.SniperIcons.TROOP_FACING_RIGHT;
			}
		} else {
			if(_dir == Direction.UP) {
				return Icons.TroopIcons.SniperIcons.ENEMY_TROOP_FACING_UP;
			}
			else if(_dir == Direction.DOWN) {
				return Icons.TroopIcons.SniperIcons.ENEMY_TROOP_FACING_DOWN;
			}
			else if(_dir == Direction.LEFT) {
				return Icons.TroopIcons.SniperIcons.ENEMY_TROOP_FACING_LEFT;
			}
			else if(_dir == Direction.RIGHT) {
				return Icons.TroopIcons.SniperIcons.ENEMY_TROOP_FACING_RIGHT;
			}
		}
		
		return Icons.TroopIcons.SniperIcons.TROOP_FACING_UP;
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

	@Override
	public void undoAbility(Position _abilityPos) {
		deactivateAbility();
		_droneArea.clear();
		_abilityUses++;
	}
}
