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

	public void Disable() {
		this.enabled = false;
	}

	public void Enable() {
		this.enabled = true;
	}

	public boolean GetCheckStatus() {
		return enabled;
	}

	public Position GetPos() {
		return this.pos;
	}

	public boolean isSolid() {
		return this.solid;
	}

	public abstract void Update();
}
