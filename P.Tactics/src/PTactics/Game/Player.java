package PTactics.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public class Player implements DangerObject{
	private String _id;
	private boolean[][] _visibility;
	private boolean[][] _danger;
	private Map<Position, Boolean> _lastTurnKills;
	private List<Troop> _troops;
	private DangerMediator _dangerMediator;

	public Player(String id, DangerMediator dm) {
		this._id = id;
		_visibility = new boolean[Game._boardWidth][Game._boardLength];
		_danger = new boolean[Game._boardWidth][Game._boardLength];
		this._troops = new ArrayList<>();
		_dangerMediator = dm;
		_dangerMediator.registerComponent(this);
		_lastTurnKills = new HashMap<>();
	}

	public boolean isVisible(int x, int y) {
		return _visibility[x][y];
	}

	public List<Troop> getTroops() {
		return Collections.unmodifiableList(new ArrayList<>(_troops));
	}

	public void addTroops(Troop t) {
		this._troops.add(t);
	}
	
	public boolean hasTroop(Troop t) {
		return _troops.contains(t);
	}
	
	private void updatePlayerVisibility() {
		_visibility = new boolean[Game._boardWidth][Game._boardLength];
		
		for (Troop troop : _troops) {
			List<Position> positions = troop.visiblePositions();
			for (Position pos : positions) {
				if(pos.isValid()) 
				{
					_visibility[pos.getX()][pos.getY()] = true;
				}
			}
		}
	}
	
	private void updatePlayerDangerTiles() {
		_danger = new boolean[Game._boardWidth][Game._boardLength];
		
		for (Troop troop : _troops) {
			List<Position> positions = troop.dangerPositions();
			for (Position pos : positions) {
				_danger[pos.getX()][pos.getY()] = true;
			}
		}
	}
	
	public void update() {
		//if no more checks are to be done it maybe would be a good idea to merge this two into one function
		updatePlayerVisibility();
		updatePlayerDangerTiles();
	}

	@Override
	public boolean isInDanger(Position pos) {
		if (_danger[pos.getX()][pos.getY()]) {
			_lastTurnKills.put(pos, true);
		}
		return _danger[pos.getX()][pos.getY()];
	}
	
	public boolean getDanger(Position pos) {
		return _dangerMediator.isInDanger(this, pos);
	}
	public  void startOfTurnDeadCheck() 
	{
		for(Troop t: _troops) 
		{
			if(getDanger(t.getPos())) 
			{
				t.onHit();
			}
		}
	}
	@Override
	public String getId() {
		return _id;
	}

	public void clearKills() {
		_lastTurnKills.clear();
	}

	public boolean lastTurnKill(Position pos) {
		if (_lastTurnKills.get(pos) == null) {
			return false;
		}
		return _lastTurnKills.get(pos);
	}
}
