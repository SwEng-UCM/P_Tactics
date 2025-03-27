package PTactics.model.game;

import javax.swing.ImageIcon;

import PTactics.model.gameObjects.GameObject;
import PTactics.utils.Position;

public interface BoardInterface{
	public Position getPosition(GameObject o);
	public void setPosition(Position p1, Position p2, GameObject o);
	public GameObject getGameObject(Position p);
	public boolean isSolid(Position p);
	public boolean isSeeThrough(Position p);
	public void addObj(Position p, GameObject o);
	public void eraseFromPos(Position p);// erases GO in pos false if no pos
	public void eraseFromGO(GameObject o);// erases GO and assigned pos from map false if no GO
	public void update();
	public String toString(Position p);
	public void smoke(Position pos);
	public void nextTurn();
	public ImageIcon toIcon(Position p);
}
