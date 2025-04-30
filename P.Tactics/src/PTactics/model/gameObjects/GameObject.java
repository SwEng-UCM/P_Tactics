package PTactics.model.gameObjects;

import javax.swing.ImageIcon;

import org.json.JSONObject;

import PTactics.model.game.Board;
import PTactics.utils.Position;

public abstract class GameObject {
	protected String _id;
	protected Position pos;
	protected boolean alive;
	protected boolean active;
	protected boolean solid;
	protected Boolean seeThrough;
	protected Boolean walkable;

	public GameObject(Position pos) {
		this.pos = pos;
		this.active = true;
		this.seeThrough = true;
		this.walkable = false;
	}

	public String getId() {
		return _id;
	}

	public Position getPos() {
		return pos;
	}

	public boolean isSolid() {
		return solid;
	}
	
	public abstract boolean isAlive();

	public boolean isSeeThrough() {
		return seeThrough;
	}
	
	public Boolean isWalkable() {
		return this.walkable;
	}
	
	public void enable() {
		alive = true;
	}
	
	public void disable() {
		alive = false;
	}
	
	public void setPosition(Position setter) {
		Board.getInstance().setPosition(this.pos, setter, this);
		pos = setter;
	}
	
	public abstract String toString();

	public abstract void onHit();

	public void AddToMove(Position pos) {};

	public void CalcNewMove(Position pos) {};

	public abstract void update();

	public abstract void nextTurn();

	public abstract ImageIcon toIcon();

	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("Id", this.getId());
		jo.put("PositionX", this.getPos().getX());
		jo.put("PositionY", this.getPos().getY());
		return jo;
	}
}
