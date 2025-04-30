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
	private int _droneSide;
	private int _droneHeight;
	private List<Integer> _abilityTime;
	private List<List<Position>> _droneArea;
	
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
		List<Position> drone = new ArrayList<Position>(); 
		for (int i = 0; i < area.size(); i++) {
			if (i != 0 && i % (_droneSide * _droneHeight) == 0) {
				_droneArea.add(drone);
				drone.clear();
			}
			drone.add(area.get(i));
		}
		this._abilityActive = true;
	}
	
	public void initVars() {
		_visionRange = Math.max(Game._boardLength, Game._boardWidth);;
		_moveRange = 3;
		_shootRange = Math.max(Game._boardLength, Game._boardWidth);
		_abilityTime = new ArrayList<>();
		_abilityUses = 2;
		for (int i = 0; i < _abilityUses; i++) {
			_abilityTime.add(3);
		}
        _movesLeft = _moveRange;
        _droneSide = 1;
        _droneHeight = 1;
        _droneArea = new ArrayList<>();
    }
	
	public List<Position> visiblePositions() {
		List<Position> visiblePositions = new ArrayList<>();
		
		if (!isAlive()) {
			return visiblePositions;
		}
		visiblePositions.add(getPos());
		
		if (isAbility()) {
			for (List<Position> drone: _droneArea) {
				visiblePositions.addAll(drone);							
			}
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

	@Override
	public void nextTurn() {
		_movesLeft = _moveRange;
		if (isAbility()) {
			for (int i = 0; i < _droneArea.size(); i++) {
				_abilityTime.set(i, _abilityTime.get(i) - 1);
			}
		}
		
		while (!_abilityTime.isEmpty() && _abilityTime.getFirst() == 0) {
			deactivateAbility();
		}
	}
	
	public void activateAbility(Position pos) {
		_abilityActive = true;
		_abilityUses--;
		
		List<Position> drone = new ArrayList<>();
		for (int i = -_droneSide; i <= _droneSide; i++) {
			for (int j = -_droneHeight; j <= _droneHeight; j++) {
				Position areaPos = new Position(pos.getX() + i, pos.getY() + j);
				if (areaPos.isValid()) {
					drone.add(new Position(pos.getX() + i, pos.getY() + j));					
				}
			}				
		}
		
		_droneArea.add(drone);
	}
	
	@Override
	public void deactivateAbility() {
		if (_droneArea.size() == 1) {
			_abilityActive = false;			
		}	
		_droneArea.removeFirst();
		_abilityTime.removeFirst();
	}
	
	@Override
	public void undoAbility(Position _abilityPos) {
		deactivateAbility();
		_droneArea.clear();
		for (int i = 0; i < _droneArea.size(); i++) {
			_abilityTime.set(i, _abilityTime.get(i) + 1);
		}
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
	
	@Override
	public JSONObject report() {
		JSONObject troopReport = super.report();
		
		if(!_droneArea.isEmpty()) {
			JSONArray droneArea = new JSONArray();
			for(List<Position> drone: _droneArea) {
				for(Position p : drone) {
					JSONObject jo = new JSONObject();
					jo.put("PositionX", p.getX());
					jo.put("PositionY", p.getY());
					droneArea.put(jo);
				}				
			}
			troopReport.put("DroneArea", droneArea);
		}
		
		return troopReport;
	}
}
