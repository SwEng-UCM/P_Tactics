package PTactics.Game;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public class Player {
	String id;
	int turn;
	boolean [][] visibility;
	boolean [][] danger;
	private List<Troop> _troops;
	
	public Player (String id, int xDim, int yDim) {
		this.id = id;
		this.turn = 0;
		visibility = new boolean [xDim][yDim];
		danger = new boolean [xDim][yDim];
		this._troops = new ArrayList<>();
		for(int i = 0; i < xDim; i++) {
			for(int j = 0; j < yDim; j++) {
				visibility[i][j] = true;		//CHANGE
				danger[i][j] = true;
			}
		}
	}
	
	public Player (String id) {
		this(id, 10, 10);
	}
	
	public boolean isVisible(int x, int y) {
		return visibility[x][y];
	}

	public List<Troop> getTroops() {
		//Should be untouchable
		return _troops;
	}

	public void addTroops(Troop t) {
		this._troops.add(t);
	}
}
