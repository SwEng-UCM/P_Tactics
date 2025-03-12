package PTactics.GameObjects;

import PTactics.Utils.Position;

public class SmokeObject extends GameObject {

	public SmokeObject(Position pos) {
		super(pos);
		this.solid=false;
		this.seeThrough=false;
	}
	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
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

}
