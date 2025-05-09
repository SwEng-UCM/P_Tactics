package PTactics.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;

import PTactics.control.ControllerInterface;
import PTactics.model.gameObjects.GameObject;

public class GameObjectCreator {
	private static List<Factory> _factories = Arrays.asList(new TroopFactory(), new ObjectsFactory());;

	public static GameObject createGameObject(JSONObject jo, ControllerInterface CI) {
		GameObject p = null;

		for (Factory f : _factories) {
			p = f.createGameObject(jo, CI);
			if (!Objects.isNull(p)) {
				return p;
			}
		}

		return p;
	}
}
