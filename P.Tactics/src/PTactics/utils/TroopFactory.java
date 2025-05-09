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
	private int movesLeft = 0;

	private boolean getData(JSONObject j, ControllerInterface CI) {
		if (!j.has("Player")) {
			return false;
		} else {
			type = (String) j.get("Id");
			p = CI.getPlayer(j.getInt("Player"));
			dir = Direction.toDir((String) j.get("Direction"));
			pos = new Position(j.getInt("PositionX"), j.getInt("PositionY"));
			aim = j.getBoolean("Aim");
			movesLeft = j.getInt("MoveLeft");
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
					List<List<Position>> droneArea = new ArrayList<>();
					for (int i1 = 0; i1 < j.getJSONArray("DroneArea").length(); i1++) {
						List<Position> drone = new ArrayList<>();
						for (int i2 = 0; i2 < j.getJSONArray("DroneArea").getJSONArray(i1).length(); i2++) {
							JSONObject jo = (JSONObject) j.getJSONArray("DroneArea").getJSONArray(i1).getJSONObject(i2);
							drone.add(new Position(jo.getInt("PositionX"), jo.getInt("PositionY")));
						}
						droneArea.add(drone);
					}
					t = new SniperTroop(pos, p, dir, droneArea);
				} else {
					t = new SniperTroop(pos, p, dir);
				}
			}
			
			if (aim)
				t.takeAim(dir);
			
			t.setMovesLeft(movesLeft);

			return t;
		}
		return null;
	}

}
