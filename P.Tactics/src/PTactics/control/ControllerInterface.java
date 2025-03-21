package PTactics.control;

import PTactics.model.GameObjects.Troop;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;

public interface ControllerInterface {
	
	void endTurn();
	public void update();
	public void updatePlayers();
	
	void showGame();

	String[] getPrompt();

	void showMessage(String msg);

	int getInt();

	void selectTroop(Position pos) throws Exception;
	
	boolean isTroopSelected();
	
	boolean canMove(Position pos);
	
	void moveTroop(Position pos) throws IllegalArgumentException;
	
	void troopAbility(Position pos) throws Exception;

	void takeAim(Direction _dirToAim);
	
	void setTroop(Troop t);
	
	int getNumPlayer();
}
