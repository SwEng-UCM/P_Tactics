package PTactics.Maps;

import java.util.ArrayList;
import java.util.List;

import PTactics.Game.Player;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;

public class MapDebug implements Map {
	public static int SERIAL_ID = 0;
	private int _game_width = 10;
	private int _game_lenght = 10;
	
	@Override
	public List<Position> listWalls(){
		List<Position> walls = new ArrayList<Position>();
		walls.add(new Position(3, 1));
		walls.add(new Position(7, 2));
		walls.add(new Position(2, 3));
		walls.add(new Position(5, 4));
		walls.add(new Position(9, 4));
		walls.add(new Position(4, 6));
		walls.add(new Position(8, 7));
		walls.add(new Position(1, 9));
		walls.add(new Position(6, 10));
		
		return walls;
	}
	
	@Override
	public List<Troop> listTroops(Player player){
		List<Troop> troops = new ArrayList<Troop>();
		
		if(player.getId().equals("1")) {
			troops.add(new Troop(new Position(2,3), player, Direction.DOWN));
			troops.add(new Troop(new Position(3,3), player, Direction.DOWN));
			troops.add(new Troop(new Position(4,3), player, Direction.DOWN));
		}
		else if (player.getId().equals("2")) {
			troops.add(new Troop(new Position(2,8), player, Direction.UP));
			troops.add(new Troop(new Position(6,9), player, Direction.UP));
			troops.add(new Troop(new Position(9,9), player, Direction.UP));
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
		return _game_lenght;
	}

	@Override
	public int getWidth() {
		return _game_width;
	}
}