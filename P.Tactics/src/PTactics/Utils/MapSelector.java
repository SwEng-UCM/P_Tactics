package PTactics.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import PTactics.Game.Controller;
import PTactics.Game.Player;
import PTactics.GameObjects.Troop;

public interface MapSelector {
	public static List<Map> maps = Arrays.asList(
		      new Map1()
		    );;
		    
	public interface Map {
		public List<Position> listWalls();
		public List<Troop> listTroops(Player player);
	}
	
	public class Map1 implements Map {
		public static int SERIAL_ID = 1;
		public static int game_width = 10;
		public static int game_lenght = 10;
		
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
	}
	
	public static List<Position> getWalls(){
		return maps.get(Controller.mapSelected).listWalls();
	}
	
	public static List<Troop> getTroops(Player player){
		return maps.get(Controller.mapSelected).listTroops(player);
	}

}
