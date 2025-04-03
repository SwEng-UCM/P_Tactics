package PTactics.model.gameObjects;

import javax.swing.ImageIcon;

import org.json.JSONObject;

import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GUI.Icons;

public class Wall extends GameObject  {

	public Wall(Position pos) {
		super(pos);
		this.solid=true;
		this.seeThrough=false;
		this._id = Utils.WallUtils.WALL;
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public ImageIcon toIcon() {
		return Icons.otherIcons.WALL;
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

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("Id: ", this.getId());
		jo.put("Position: ", this.getPos());
		return jo;
	}
}
