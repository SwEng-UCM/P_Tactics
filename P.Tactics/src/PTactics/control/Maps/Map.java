package PTactics.control.Maps;

import java.util.List;

import PTactics.model.Game.Player;
import PTactics.model.GameObjects.Troop;
import PTactics.Utils.Position;

public interface Map {
	public List<Position> listWalls();
	public List<Troop> listTroops(Player player);
	public int getLength();
	public int getWidth();
}