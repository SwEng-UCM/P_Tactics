package PTactics.GameObjects;

import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class Wall extends GameObject  {

	public Wall(Position pos) {
		super(pos);
		this.solid=true;
		this.seeThrough=false;
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public String toString() {
		return Utils.WallUtils.WALL_ICON;
	}

	@Override
	public boolean isAlive() {
		return false;
	}

	@Override
	public void onHit() {
	}

}
