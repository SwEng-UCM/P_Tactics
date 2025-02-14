package PTactics.Game;

import java.util.Map;

import PTactics.GameObjects.GameObject;
import PTactics.Utils.Position;

public interface BoardInterface extends Map<Position, GameObject>{
	public Position getPosition(GameObject o);
	public void setPosition(Position p1, Position p2, GameObject o);
	public GameObject whatInPos(Position p);
}
