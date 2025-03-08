package PTactics.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface MapSelector {
	public static List<Map> maps = Arrays.asList(
		      new Map1()
		    );;
		    
	public interface Map {
		public List<Position> listWalls();
		public List<Position> listTroops(int player);
	}
	
	public class Map1 implements Map {
		public static int SERIAL_ID = 1;
		public static int game_width = 10;
		public static int game_lenght = 10;

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
		
		public List<Position> listTroops(int player){
			List<Position> troops = new ArrayList<Position>();
			
			if(player == 1) {
				troops.add(new Position(2,3));
				troops.add(new Position(3,3));
				troops.add(new Position(4,3));
			}
			else if (player == 2) {
				troops.add(new Position(2,8));
				troops.add(new Position(6,9));
				troops.add(new Position(9,9));
			}
			else if(player == 3) {
				//In case of more players
				
				
			}
			else if(player == 4) {
				//In case of more players
				
				
			}
			
			return troops;
		}
	}
	
	public static List<Position> getWalls(int map){
		return maps.get(map).listWalls();
	}
	
	public static List<Position> getTroops(int map, int player){
		return maps.get(map).listTroops(player);
	}

}
