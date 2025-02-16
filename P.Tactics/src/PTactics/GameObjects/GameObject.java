package PTactics.GameObjects;

import PTactics.Game.BoardInterface;
import PTactics.Utils.Position;

public abstract class GameObject {
	protected Position pos;
	protected boolean enabled;
	protected boolean solid;
	protected BoardInterface BI;

	public GameObject(Position pos) {
		this.pos = pos;
	}

	public void setPosition(Position setter) {
		pos = setter;
	}

	public void disable() {
		enabled = false;
	}

	public void enable() {
		enabled = true;
	}

	public boolean getCheckStatus() {
		return enabled;
	}

	public Position getPos() {
		return pos;
	}

	public boolean isSolid() {
		return solid;
	}

	public abstract void update();
}
