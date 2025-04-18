package PTactics.utils;

import org.json.JSONObject;

import PTactics.control.ControllerInterface;
import PTactics.model.gameObjects.GameObject;

public interface Factory {
	public abstract GameObject createGameObject(JSONObject j, ControllerInterface CI);
}
