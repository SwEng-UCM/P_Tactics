package PTactics.control;

import java.io.InputStream;
import java.util.List;

import javax.swing.Icon;

import org.json.JSONObject;

import PTactics.model.game.Game;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.view.GameObserver;

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

	int getNumPlayer();

	public void addObserver(GameObserver o);
	public void removeObserver(GameObserver o);

	String positionToString(Position _pos);

	public Boolean isTroop(Position pos);

	void setPlayerNum(int playerNum);

	public void setupPlayers();

	public Game getGame();

	public void nextTurn();

	public Troop currTroop();

	Icon getIcon(Position _pos);

	boolean dangerTile(Position _pos);

	List<Position> getPath(Position pos);

	List<Position> hoverPath(Position pos);

	public boolean isFinish();
	
	public void setPlayerNames(List<String> names);
	
	public List<String> getPlayerNames();
	
	public String getCurrentPlayerName();
	
	public void setUpPlayerVsCPU(int levelCPU);

	public void load(InputStream is);
	
	public JSONObject report();

	void selectTroop(Troop t);
	
	void executeCommand(String[] args); // parses and executes command
	
	
	void updateOnPlayersUpdate();
	void updateOnBoardUpdate();
	void updateOnTroopAction();
	void updateOnTroopSelection();
	void updateOnNextTurn();
	void updateOnTroopUnSelection();

	int getCurrentPlayerWinZone();

	boolean cpuIsPlaying();
}
