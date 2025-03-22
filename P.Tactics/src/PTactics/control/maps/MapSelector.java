package PTactics.control.maps;

import java.util.Arrays;
import java.util.List;

import PTactics.control.Controller;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Position;

public class MapSelector {
	private static List<Map> maps = Arrays.asList(
			  new MapEmpty(),
		      new Map1(),
		      new Map2()
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
