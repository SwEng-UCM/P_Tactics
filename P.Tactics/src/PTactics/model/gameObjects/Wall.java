package PTactics.model.gameObjects;

import PTactics.utils.Position;
import PTactics.utils.Utils;

public class Wall extends GameObject  {

	public Wall(Position pos) {
		super(pos);
		this.alive=false;
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
		return active;
	}

	@Override
	public void onHit() {
	}

	@Override
	public void nextTurn() {
		
	}

}
