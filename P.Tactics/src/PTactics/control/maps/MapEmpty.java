package PTactics.control.maps;

import java.util.ArrayList;
import java.util.List;

import PTactics.model.game.Player;
import PTactics.model.gameObjects.LightTroop;
import PTactics.model.gameObjects.SmokerTroop;
import PTactics.model.gameObjects.SniperTroop;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;

public class MapEmpty implements Map {
	public static final int SERIAL_ID = 0;
	private int _game_width = 20;
	private int _game_lenght = 10;

	@Override
	public List<Position> listWalls() {
		List<Position> walls = new ArrayList<Position>();

		return walls;
	}

	@Override
	public List<Troop> listTroops(Player player) {
		List<Troop> troops = new ArrayList<Troop>();

		if (player.getId().equals("1")) {
			troops.add(new SniperTroop(new Position(1, 2), player, Direction.DOWN));
			troops.add(new SmokerTroop(new Position(5, 2), player, Direction.DOWN));
			troops.add(new LightTroop(new Position(8, 2), player, Direction.DOWN));
		} else if (player.getId().equals("2")) {
			troops.add(new SniperTroop(new Position(1, 9), player, Direction.UP));
			troops.add(new SmokerTroop(new Position(5, 7), player, Direction.UP));
			troops.add(new LightTroop(new Position(8, 6), player, Direction.UP));
			troops.add(new SniperTroop(new Position(8, 9), player, Direction.UP));
		} else if (player.getId().equals("3")) {
			// In case of more players

		} else if (player.getId().equals("4")) {
			// In case of more players

		}

		return troops;
	}

	@Override
	public int getLength() {
		return _game_lenght;
	}

	@Override
	public int getWidth() {
		return _game_width;
	}

	@Override
	public List<Position> listWinPositions() {
		return null;
	}

}
