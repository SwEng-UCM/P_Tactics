package PTactics.Game;

import PTactics.GameObjects.GameObject;
import PTactics.GameObjects.Troop;
import PTactics.Utils.Position;

public interface ControllerInterface {
	
	void endTurn();
	public void update();
	public void updatePlayers();
	
	void showGame();

	String[] getPrompt();

	void showMessage(String msg);

	int getInt();

	GameObject getGameObject(Position pos);

	void setTroop(Troop t);
	
	int getNumPlayer();
}
