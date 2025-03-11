package PTactics.GameObjects;

import PTactics.Game.BoardInterface;
import PTactics.Utils.Position;

public class SmokeObject extends GameObject {

	public SmokeObject(Position pos, BoardInterface BI) {
		super(pos, BI);
		this.solid=false;
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
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
