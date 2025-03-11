package PTactics.Maps;

import java.util.List;

import PTactics.Game.Player;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public interface Map {
	public List<Position> listWalls();
	public List<Troop> listTroops(Player player);
	public int getLength();
	public int getWidth();
}