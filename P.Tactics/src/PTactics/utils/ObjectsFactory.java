package PTactics.utils;

import org.json.JSONObject;

import PTactics.control.ControllerInterface;
import PTactics.model.gameObjects.GameObject;
import PTactics.model.gameObjects.SmokeObject;
import PTactics.model.gameObjects.Wall;

public class ObjectsFactory implements Factory {

	private String type;
	private Position pos = null;

	public void getData(JSONObject j) {
		type = (String) j.get("Id");
		pos = new Position(j.getInt("PositionX"), j.getInt("PositionY"));
	}

	@Override
	public GameObject createGameObject(JSONObject j, ControllerInterface CI) {
		getData(j);
		if (type.equals(Utils.WallUtils.WALL)) {
			return new Wall(pos);
		}
		if (type.equals(Utils.WallUtils.SMOKE)) {
			return new SmokeObject(pos);
		}
		return null;
	}

}
