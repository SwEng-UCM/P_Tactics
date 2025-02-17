package PTactics.Game;

import java.util.Map;

import PTactics.GameObjects.GameObject;
import PTactics.Utils.Position;

public interface BoardInterface{
	public Position getPosition(GameObject o);
	public void setPosition(Position p1, Position p2, GameObject o);
	public GameObject getGameObject(Position p);
	public boolean isSolid(Position p);
	public void addObj(Position p, GameObject o);
	public void erraseFromPos(Position p);// erases GO in pos false if no pos
	public void erraseFromGO(GameObject o);// erases GO and assigned pos from map false if no GO
	public void update();
	public String toString(Position p);
}
