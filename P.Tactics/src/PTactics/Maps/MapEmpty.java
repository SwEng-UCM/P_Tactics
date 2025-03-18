package PTactics.Maps;

import java.util.ArrayList;
import java.util.List;

import PTactics.Game.Player;
import PTactics.GameObjects.LightTroop;
import PTactics.GameObjects.SmokerTroop;
import PTactics.GameObjects.SniperTroop;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;

public class MapEmpty implements Map{
	public static int SERIAL_ID = 0;
	private int _game_width = 10;
	private int _game_lenght = 10;
	
	@Override
	public List<Position> listWalls() {
		List<Position> walls = new ArrayList<Position>();
		
		return walls;
	}

	@Override
	public List<Troop> listTroops(Player player) {
		List<Troop> troops = new ArrayList<Troop>();
		
		if(player.getId().equals("1")) {
			troops.add(new SniperTroop(new Position(1,2), player, Direction.DOWN));
			troops.add(new SmokerTroop(new Position(5,2), player, Direction.DOWN));
			troops.add(new LightTroop(new Position(8,2), player, Direction.DOWN)); 
		}
		else if (player.getId().equals("2")) {
			troops.add(new SniperTroop(new Position(1,9), player, Direction.UP));
			troops.add(new SmokerTroop(new Position(5,7), player, Direction.UP));
			troops.add(new LightTroop(new Position(8,6), player, Direction.UP));
			troops.add(new SniperTroop(new Position(8,9), player, Direction.UP));
		}
		else if(player.getId().equals("3")) {
			//In case of more players
			
			
		}
		else if(player.getId().equals("4")) {
			//In case of more players
			
			
		}
		
		return troops;
	}

	@Override
	public int getLength() {
		return _game_width;
	}

	@Override
	public int getWidth() {
		return _game_lenght;
	}

}
