package PTactics.control;

import PTactics.model.game.Game;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.view.GameObserver;
import PTactics.view.GUI.GameBoardCell;

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
	
	public void addObserver(GameObserver o);
	
	String positionToString(Position _pos);
	
	public Boolean isTroop(Position pos);
	void setPlayerNum(int playerNum);
	
	void setCorrect();
	
	public  void setupPlayers();
	
	public Game getGame();
	
	public void nextTurn();
	public Troop currTroop();
}
