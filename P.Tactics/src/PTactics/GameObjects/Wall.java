package PTactics.GameObjects;

import PTactics.Utils.Position;

public class Wall extends GameObject  {

	public Wall(Position pos) {
		super(pos);
		this.solid=true;
	}

	@Override
	public void update() {
		
	}

}
