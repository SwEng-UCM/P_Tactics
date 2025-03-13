package PTactics.GameObjects;

import PTactics.Game.Board;
import PTactics.Utils.Position;

public class SmokeObject extends GameObject {
	private int turnsLeft;
	public SmokeObject(Position pos) {
		super(pos);
		this.solid=false;
		this.seeThrough=false;
		this.turnsLeft=3;
	}
	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String toString() {
		return "M";
	}
	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void nextTurn() {
		if(this.turnsLeft==0) 
		{
			this.alive=false;
		    Board.getInstance().eraseFromPos(this.pos);
		}
		else 
		{
			this.turnsLeft--;
		}
	}

}
