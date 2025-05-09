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

public class Map1 implements Map {
	public static final int SERIAL_ID = 1;
	private int _game_width = 20;
	private int _game_lenght = 10;

	@Override
	public List<Position> listWalls() {
		List<Position> walls = new ArrayList<Position>();

		// Inner walls forming rooms
		walls.add(new Position(9, 0));
		walls.add(new Position(10, 0));
		walls.add(new Position(6, 1));
		walls.add(new Position(7, 1));
		walls.add(new Position(12, 1));
		walls.add(new Position(13, 1));
		walls.add(new Position(0, 2));
		walls.add(new Position(7, 2));
		walls.add(new Position(8, 2));
		walls.add(new Position(11, 2));
		walls.add(new Position(12, 2));
		walls.add(new Position(19, 2));
		walls.add(new Position(3, 3));
		walls.add(new Position(4, 3));
		walls.add(new Position(7, 3));
		walls.add(new Position(12, 3));
		walls.add(new Position(15, 3));
		walls.add(new Position(16, 3));
		walls.add(new Position(1, 4));
		walls.add(new Position(2, 4));
		walls.add(new Position(3, 4));
		walls.add(new Position(16, 4));
		walls.add(new Position(17, 4));
		walls.add(new Position(18, 4));

		walls.add(new Position(9, 9));
		walls.add(new Position(10, 9));
		walls.add(new Position(6, 8));
		walls.add(new Position(7, 8));
		walls.add(new Position(12, 8));
		walls.add(new Position(13, 8));
		walls.add(new Position(0, 7));
		walls.add(new Position(7, 7));
		walls.add(new Position(8, 7));
		walls.add(new Position(11, 7));
		walls.add(new Position(12, 7));
		walls.add(new Position(19, 7));
		walls.add(new Position(3, 6));
		walls.add(new Position(4, 6));
		walls.add(new Position(7, 6));
		walls.add(new Position(12, 6));
		walls.add(new Position(15, 6));
		walls.add(new Position(16, 6));
		walls.add(new Position(1, 5));
		walls.add(new Position(2, 5));
		walls.add(new Position(3, 5));
		walls.add(new Position(16, 5));
		walls.add(new Position(17, 5));
		walls.add(new Position(18, 5));

		return walls;

	}

	@Override
	public List<Troop> listTroops(Player player) {
		List<Troop> troops = new ArrayList<Troop>();

		if (player.getId().equals("1")) {
			troops.add(new SniperTroop(new Position(0, 9), player, Direction.RIGHT));
			troops.add(new SmokerTroop(new Position(1, 9), player, Direction.RIGHT));
			troops.add(new LightTroop(new Position(0, 8), player, Direction.RIGHT));
		}

		else if (player.getId().equals("2")) {
			troops.add(new SniperTroop(new Position(19, 0), player, Direction.LEFT));
			troops.add(new SmokerTroop(new Position(18, 0), player, Direction.LEFT));
			troops.add(new LightTroop(new Position(19, 1), player, Direction.LEFT));
		}

		else if (player.getId().equals("3")) {
			troops.add(new SniperTroop(new Position(0, 0), player, Direction.RIGHT));
			troops.add(new SmokerTroop(new Position(1, 0), player, Direction.RIGHT));
			troops.add(new LightTroop(new Position(0, 1), player, Direction.RIGHT));
		}

		else if (player.getId().equals("4")) {
			troops.add(new SniperTroop(new Position(19, 9), player, Direction.LEFT));
			troops.add(new SmokerTroop(new Position(18, 9), player, Direction.LEFT));
			troops.add(new LightTroop(new Position(19, 8), player, Direction.LEFT));
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
		List<Position> winPos = new ArrayList<Position>();

		winPos.add(new Position(9, 4));
		winPos.add(new Position(9, 5));
		winPos.add(new Position(10, 4));
		winPos.add(new Position(10, 5));
		return winPos;
	}
}