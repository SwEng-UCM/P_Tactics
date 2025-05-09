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

public class Map2 implements Map {
	public static final int SERIAL_ID = 2;
	private int _game_width = 10;
	private int _game_lenght = 10;

	@Override
	public List<Position> listWalls() {
		List<Position> walls = new ArrayList<Position>();
		walls.add(new Position(2, 0));
		walls.add(new Position(4, 1));
		walls.add(new Position(0, 2));
		walls.add(new Position(4, 2));
		walls.add(new Position(7, 4));
		walls.add(new Position(8, 4));
		walls.add(new Position(1, 5));
		walls.add(new Position(2, 5));
		walls.add(new Position(5, 7));
		walls.add(new Position(9, 7));
		walls.add(new Position(5, 8));
		walls.add(new Position(7, 9));

		return walls;
	}

	@Override
	public List<Troop> listTroops(Player player) {
		List<Troop> troops = new ArrayList<Troop>();

		if (player.getId().equals("1")) {
			troops.add(new SniperTroop(new Position(0, 9), player, Direction.DOWN));
			troops.add(new SmokerTroop(new Position(0, 8), player, Direction.DOWN));
			troops.add(new LightTroop(new Position(1, 9), player, Direction.DOWN));
		} else if (player.getId().equals("2")) {
			troops.add(new SniperTroop(new Position(9, 0), player, Direction.UP));
			troops.add(new SmokerTroop(new Position(8, 0), player, Direction.UP));
			troops.add(new LightTroop(new Position(9, 1), player, Direction.UP));
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
		List<Position> winPos = new ArrayList<Position>();

		winPos.add(new Position(2, 2));
		winPos.add(new Position(7, 7));

		return winPos;
	}
}