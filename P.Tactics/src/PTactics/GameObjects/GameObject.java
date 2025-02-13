package PTactics.GameObjects;

import PTactics.Utils.Position;

public abstract class GameObject {
	protected Position pos;
	protected boolean enabled;
	protected boolean solid;

	public GameObject(Position pos) {
		this.pos = pos;
	}

	public void setPosition(Position setter) {
		this.pos = setter;
	}

	public void disable() {
		this.enabled = false;
	}

	public void enable() {
		this.enabled = true;
	}

	public boolean getCheckStatus() {
		return enabled;
	}

	public Position getPos() {
		return this.pos;
	}

	public boolean isSolid() {
		return this.solid;
	}

	public abstract void update();
}
