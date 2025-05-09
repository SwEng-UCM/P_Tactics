package PTactics.model.game;

import java.util.List;

import javax.swing.ImageIcon;

import org.json.JSONArray;

import PTactics.model.gameObjects.GameObject;
import PTactics.utils.Position;

public interface BoardInterface {
	public int pointsToWin();

	public Position getPosition(GameObject o);

	public GameObject getGameObject(Position p);

	public boolean isSolid(Position p);

	public boolean isSeeThrough(Position p);

	public void addObj(Position p, GameObject o);

	public void addSmoke(Position pos);

	public void setPosition(Position p1, Position p2, GameObject o);

	public void eraseFromPos(Position p);

	public void eraseAll();

	public void eraseFromGO(GameObject o);

	public void eraseSmoke(Position _abilityPos);

	public void update();

	public void nextTurn();

	public List<Position> shootablePositions(Position target, int aimRange);

	public String toString(Position p);

	public ImageIcon toIcon(Position p);

	public JSONArray report();

	public boolean isWinPosition(Position _pos);

}
