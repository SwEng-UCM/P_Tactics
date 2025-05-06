package PTactics.control.maps;

import java.util.Arrays;
import java.util.List;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Position;

public class MapSelector {
	public static int mapSelected;
	private static List<Map> maps = Arrays.asList(
			  new MapEmpty(),
		      new Map1(),
		      new Map2()
		    );;
	
	public static List<Position> getWalls(){
		return maps.get(mapSelected).listWalls();
	}
	
	public static List<Troop> getTroops(Player player){
		return maps.get(mapSelected).listTroops(player);
	}
	
	public static int getLength(){
		return maps.get(mapSelected).getLength();
	}
	
	public static int getWidth(){
		return maps.get(mapSelected).getWidth();
	}

	public static List<Position> getWinZone() {
		return maps.get(mapSelected).listWinPositions();
	}
}
