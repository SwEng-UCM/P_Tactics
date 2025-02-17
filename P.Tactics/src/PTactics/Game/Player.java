package PTactics.Game;

import java.awt.Dimension;
import java.util.List;

import PTactics.Utils.Position;

public class Player {
	String id;
	int turn;
	boolean [][] visibility;
	boolean [][] danger;
	
	public Player (String id, int xDim, int yDim) {
		this.id = id;
		this.turn = 0;
		visibility = new boolean [xDim][yDim];
		danger = new boolean [xDim][yDim];
		for(int i = 0; i < xDim; i++) {
			for(int j = 0; j < yDim; j++) {
				visibility[i][j] = false;
				danger[i][j] = false;
			}
		}
	}
	public boolean isVisible(int x, int y) {
		return visibility[x][y];
	}
}
