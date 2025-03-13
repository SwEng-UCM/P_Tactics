package PTactics.Maps;

import java.util.Arrays;
import java.util.List;

import PTactics.Game.Controller;
import PTactics.Game.Player;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public class MapSelector {
	private static List<Map> maps = Arrays.asList(
		      new MapDebug(),
		      new Map1()
		    );;
	
	public static List<Position> getWalls(){
		return maps.get(Controller.mapSelected).listWalls();
	}
	
	public static List<Troop> getTroops(Player player){
		return maps.get(Controller.mapSelected).listTroops(player);
	}
	
	public static int getLength(){
		return maps.get(Controller.mapSelected).getLength();
	}
	
	public static int getWidth(){
		return maps.get(Controller.mapSelected).getWidth();
	}
}
