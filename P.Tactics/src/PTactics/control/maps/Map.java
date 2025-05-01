package PTactics.control.maps;

import java.util.List;

import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Position;

public interface Map {
	public List<Position> listWalls();
	public List<Troop> listTroops(Player player);
	public int getLength();
	public int getWidth();
	public List<Position> listWinPositions();
}