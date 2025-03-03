package PTactics.Game;

import java.util.ArrayList;
import java.util.List;

import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public class Player implements DangerObject{
	private String _id;
	private int _turn;
	private boolean[][] _visibility;
	private boolean[][] _danger;
	private List<Troop> _troops;
	private DangerMediator _dangerMediator;

	public Player(String id) {
		this._id = id;
		this._turn = 0;
		_visibility = new boolean[Game._boardWidth][Game._boardLength];
		_danger = new boolean[Game._boardWidth][Game._boardLength];
		this._troops = new ArrayList<>();
	}

	public boolean isVisible(int x, int y) {
		return _visibility[x][y];
	}

	public List<Troop> getTroops() {
		// getters are observers, return a copy
		return new ArrayList<>(_troops);
	}

	public void addTroops(Troop t) {
		this._troops.add(t);
		t.addPlayer(this);
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
		return _danger[pos.getX()][pos.getY()];
	}
	
	public boolean getDanger(Position pos) {
		return _dangerMediator.isInDanger(this, pos);
	}

	@Override
	public String getId() {
		return _id;
	}
}
