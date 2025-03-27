package PTactics.model.gameObjects;

import javax.swing.ImageIcon;

import PTactics.model.game.Board;
import PTactics.utils.Position;
import PTactics.view.GUI.Icons;

public class SmokeObject extends GameObject {
	private int turnsLeft;
	public SmokeObject(Position pos) {
		super(pos);
		this.solid=false;
		this.seeThrough=false;
		this.turnsLeft=3;
		this.alive=false;
		this.walkable=true;
	}
	@Override
	public boolean isAlive() {
		return true;
	}
	@Override
	public String toString() {
		return "M";
	}
	@Override
	public void onHit() {
		
	}

	@Override
	public void update() {
		
	}
	@Override
	public void nextTurn() {
		if(this.turnsLeft==0) 
		{
			this.active=false;
		    Board.getInstance().eraseFromPos(this.pos);
		}
		else 
		{
			this.turnsLeft--;
		}
	}
	
	@Override
	public ImageIcon toIcon() {
		return Icons.otherIcons.FOG;
	}

}
