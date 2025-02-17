package PTactics.GameObjects;

import PTactics.Game.BoardInterface;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class Wall extends GameObject  {

	public Wall(Position pos, BoardInterface BI) {
		super(pos, BI);
		this.solid=true;
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public String toString() {
		return Utils.WallUtils.WALL_ICON;
	}

}
