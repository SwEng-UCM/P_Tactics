package PTactics.Game;

import java.util.ArrayList;
import java.util.List;

import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public class Player {
	// all of this stuff really should be private
	private String _id;
	private int _turn;
	private boolean[][] _visibility;
	private boolean[][] _danger;
	private List<Troop> _troops;

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
	}
	
	public void updatePlayerVisibility() {
		_visibility = new boolean[Game._boardWidth][Game._boardLength];
		
		for (Troop troop : _troops) {
			List<Position> positions = troop.visiblePositions();
			for (Position pos : positions) {
				_visibility[pos.getX()][pos.getY()] = true;
			}
		}
	}
	
	public void updatePlayerDangerTiles() {
		_danger = new boolean[Game._boardWidth][Game._boardLength];
		
		for (Troop troop : _troops) {
			List<Position> positions = troop.dangerPositions();
			for (Position pos : positions) {
				_danger[pos.getX()][pos.getY()] = true;
			}
		}
	}
}
