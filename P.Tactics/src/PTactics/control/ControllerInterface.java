package PTactics.control;

import PTactics.model.GameObjects.GameObject;
import PTactics.model.GameObjects.Troop;
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
