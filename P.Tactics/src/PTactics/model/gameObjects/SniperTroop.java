package PTactics.model.gameObjects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONObject;

import PTactics.control.maps.MapSelector;
import PTactics.model.game.Board;
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
		_visionRange = Math.max(MapSelector.getLength(), MapSelector.getWidth());
		;
		_moveRange = 3;
		_shootRange = Math.max(MapSelector.getLength(), MapSelector.getWidth());
		_abilityTime = new ArrayList<>();
		_abilityUses = 2;
		for (int i = 0; i < _abilityUses; i++) {
			_abilityTime.add(3);
		}
		_movesLeft = _moveRange;
		_droneSide = 1;
		_droneHeight = 1;
		_droneArea = new ArrayList<>();
		_id = Utils.TroopUtils.SNIPER_TROOP_ID;
	}

	public SniperTroop(Position pos, Player p, Direction dir) {
		super(pos, p, dir);
		_visionRange = Math.max(MapSelector.getLength(), MapSelector.getWidth());
		;
		_moveRange = 3;
		_shootRange = Math.max(MapSelector.getLength(), MapSelector.getWidth());
		_abilityTime = new ArrayList<>();
		_abilityUses = 2;
		for (int i = 0; i < _abilityUses; i++) {
			_abilityTime.add(3);
		}
		_movesLeft = _moveRange;
		_droneSide = 1;
		_droneHeight = 1;
		_droneArea = new ArrayList<>();
		_id = Utils.TroopUtils.SNIPER_TROOP_ID;
	}

	public SniperTroop(Position pos, Player p, Direction dir, List<List<Position>> area) {
		super(pos, p, dir);
		_visionRange = Math.max(MapSelector.getLength(), MapSelector.getWidth());
		;
		_moveRange = 3;
		_shootRange = Math.max(MapSelector.getLength(), MapSelector.getWidth());
		_abilityTime = new ArrayList<>();
		_abilityUses = 2;
		for (int i = 0; i < _abilityUses; i++) {
			_abilityTime.add(3);
		}
		_movesLeft = _moveRange;
		_droneSide = 1;
		_droneHeight = 1;
		_droneArea = new ArrayList<>(area);
		_id = Utils.TroopUtils.SNIPER_TROOP_ID;
		this._abilityActive = true;
	}

	@Override
	public List<Position> visiblePositions() {
		List<Position> visiblePositions = new ArrayList<>();
		if (!isAlive()) {
			return visiblePositions;
		}
		visiblePositions.add(getPos());

		if (isAbility()) {
			for (List<Position> drone : _droneArea) {
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

	@Override
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
		super.undoAbility(_abilityPos);
		_droneArea.removeLast();
		_abilityTime.removeLast();
		_abilityTime.addLast(3);
	}

	@Override
	public String toString() {
		return "F" + super.toString();
	}

	@Override
	public ImageIcon toIcon() {
		if (_player.isMyTurn()) {
			if (_dir == Direction.UP) {
				return Icons.TroopIcons.SniperIcons.TROOP_FACING_UP;
			} else if (_dir == Direction.DOWN) {
				return Icons.TroopIcons.SniperIcons.TROOP_FACING_DOWN;
			} else if (_dir == Direction.LEFT) {
				return Icons.TroopIcons.SniperIcons.TROOP_FACING_LEFT;
			} else if (_dir == Direction.RIGHT) {
				return Icons.TroopIcons.SniperIcons.TROOP_FACING_RIGHT;
			}
		} else {
			if (_dir == Direction.UP) {
				return Icons.TroopIcons.SniperIcons.ENEMY_TROOP_FACING_UP;
			} else if (_dir == Direction.DOWN) {
				return Icons.TroopIcons.SniperIcons.ENEMY_TROOP_FACING_DOWN;
			} else if (_dir == Direction.LEFT) {
				return Icons.TroopIcons.SniperIcons.ENEMY_TROOP_FACING_LEFT;
			} else if (_dir == Direction.RIGHT) {
				return Icons.TroopIcons.SniperIcons.ENEMY_TROOP_FACING_RIGHT;
			}
		}

		return Icons.TroopIcons.SniperIcons.TROOP_FACING_UP;
	}

	@Override
	public JSONObject report() {
		JSONObject troopReport = super.report();

		if (!_droneArea.isEmpty()) {
			JSONArray droneArea = new JSONArray();
			for (List<Position> drone : _droneArea) {
				JSONArray revealPos = new JSONArray();
				for (Position p : drone) {
					JSONObject jo = new JSONObject();
					jo.put("PositionX", p.getX());
					jo.put("PositionY", p.getY());
					revealPos.put(jo);
				}
				droneArea.put(revealPos);
			}
			troopReport.put("DroneArea", droneArea);
		}

		return troopReport;
	}
}
