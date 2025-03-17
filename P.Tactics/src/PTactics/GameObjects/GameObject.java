package PTactics.GameObjects;

import PTactics.Game.Board;
import PTactics.Utils.Position;

public abstract class GameObject {
	protected Position pos;
	protected boolean enabled;
	protected boolean alive;
	protected boolean solid;
	protected String icon;
	protected Boolean seeThrough=true;
	public GameObject(Position pos) {
		this.pos = pos;
		this.alive = true;
	}

	public void setPosition(Position setter) {
		Board.getInstance().setPosition(this.pos, setter, this);
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
	public boolean isSeeThrough() {
		return seeThrough;
	}
	public String toString() 
	{
		return icon;
	}
	public abstract boolean isAlive();
	public abstract void onHit();
	public void AddToMove(Position pos) {};
	public void CalcNewMove(Position pos) {};
	public abstract void update();
	public abstract void nextTurn();
}
