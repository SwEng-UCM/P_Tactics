package PTactics.model.gameObjects;

import PTactics.model.game.Board;
import PTactics.utils.Position;

public abstract class GameObject {
	protected Position pos;
	protected boolean alive;
	protected boolean active;
	protected boolean solid;
	protected String icon;
	protected Boolean seeThrough=true;
	public GameObject(Position pos) {
		this.pos = pos;
		this.active = true;
	}

	public void setPosition(Position setter) {
		Board.getInstance().setPosition(this.pos, setter, this);
		pos = setter;
	}

	public void disable() {
		alive = false;
	}

	public void enable() {
		alive = true;
	}

	public boolean getCheckStatus() {
		return alive;
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
