package PTactics.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.GameObject;
import PTactics.model.gameObjects.LightTroop;
import PTactics.model.gameObjects.SmokerTroop;
import PTactics.model.gameObjects.SniperTroop;
import PTactics.model.gameObjects.Troop;

public class TroopFactory implements Factory {

	private String type;
	private Player p = null;
	private Position pos = null;
	private Direction dir = null;
	private boolean aim;

	private boolean getData(JSONObject j, ControllerInterface CI) {
		if (!j.has("Player")) {
			return false;
		} else {
			type = (String) j.get("Id");
			p = CI.getPlayer(j.getInt("Player"));
			dir = Direction.toDir((String) j.get("Direction"));
			pos = new Position(j.getInt("PositionX"), j.getInt("PositionY"));
			aim = j.getBoolean("Aim");
			return true;
		}
	}

	@Override
	public GameObject createGameObject(JSONObject j, ControllerInterface CI) {
		Troop t = null;
		if (getData(j, CI)) {
			if (type.equals(Utils.TroopUtils.LIGHT_TROOP_ID)) {
				if (j.has("iFrames")) {
					t = new LightTroop(pos, p, dir, j.getInt("iFrames"));
				} else {
					t = new LightTroop(pos, p, dir);
				}

			}
			if (type.equals(Utils.TroopUtils.SMOKER_TROOP_ID)) {
				t = new SmokerTroop(pos, p, dir);
			}
			if (type.equals(Utils.TroopUtils.SNIPER_TROOP_ID)) {
				if (j.has("DroneArea")) {
					List<Position> drone = new ArrayList<>();
					for (int i1 = 0; i1 < j.getJSONArray("DroneArea").length(); i1++) {
						JSONObject jo = (JSONObject) j.getJSONArray("DroneArea").get(i1);
						drone.add(new Position(jo.getInt("PositionX"), jo.getInt("PositionY")));
					}
					t = new SniperTroop(pos, p, dir, drone);
				} else {
					t = new SniperTroop(pos, p, dir);
				}
			}
			if (aim)
				t.takeAim(dir);

			return t;
		}
		return null;
	}

}
