package PTactics.control.Maps;

import java.util.ArrayList;
import java.util.List;

import PTactics.model.Game.Player;
import PTactics.model.GameObjects.LightTroop;
import PTactics.model.GameObjects.SmokerTroop;
import PTactics.model.GameObjects.SniperTroop;
import PTactics.model.GameObjects.Troop;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;

public class Map1 implements Map {
	public static int SERIAL_ID = 1;
	private int _game_width = 10;
	private int _game_lenght = 10;

	@Override
	public List<Position> listWalls() {
		List<Position> walls = new ArrayList<Position>();

		// Inner walls forming rooms
		walls.add(new Position(3, 3));
		walls.add(new Position(3, 4));
		walls.add(new Position(3, 6));
		walls.add(new Position(3, 7));
		walls.add(new Position(5, 2));
		walls.add(new Position(5, 8));
		walls.add(new Position(6, 3));
		walls.add(new Position(6, 4));
		walls.add(new Position(6, 6));
		walls.add(new Position(6, 7));
		walls.add(new Position(8, 5));
		
		return walls;


	}

	@Override
	public List<Troop> listTroops(Player player) {
		List<Troop> troops = new ArrayList<Troop>(); 

		if (player.getId().equals("1")) {
		    troops.add(new SniperTroop(new Position(1, 2), player, Direction.DOWN));
		    troops.add(new SmokerTroop(new Position(7, 9), player, Direction.DOWN));
		    troops.add(new LightTroop(new Position(9, 3), player, Direction.DOWN));
		}
		else if (player.getId().equals("2")) {
		    troops.add(new SniperTroop(new Position(1, 9), player, Direction.UP));
		    troops.add(new SmokerTroop(new Position(7, 2), player, Direction.UP));
		    troops.add(new LightTroop(new Position(9, 8), player, Direction.UP));
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
}