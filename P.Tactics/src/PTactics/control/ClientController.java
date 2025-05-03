package PTactics.control;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.json.JSONObject;
import org.json.JSONTokener;

import P.Tactics.CPU.EasyCPU;
import P.Tactics.CPU.HardCPU;
import P.Tactics.CPU.MediumCPU;
import PTactics.control.maps.MapSelector;
import PTactics.model.game.Board;
import PTactics.model.game.DangerMediator;
import PTactics.model.game.Game;
import PTactics.model.game.Observable;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.GameObjectCreator;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;


import java.net.*; 
import java.io.*;

public class ClientController implements ControllerInterface,Observable<GameObserver> {
	private String Id;
	private Socket socket;
    BufferedReader in;
    PrintWriter out;
	private List<GameObserver> _observers;
	// constructor that takes the IP Address and the Port
	public ClientController(String address, int port, String Id) 
	{ 
		this.Id = Id;
		_observers = new ArrayList<>();
		// we try to establish a connection 
		try
		{ 
			// creates a socket with the given information
			socket = new Socket(address, port); 
			out = new PrintWriter(socket.getOutputStream(), true); 
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println(Id);
			
		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
			// error messager in gui
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 

		// close the connection 
		// setup players
	} 

	@Override
	
	public void setPlayerNum(int playerNum) {
		// no hace na
	}

	public void setPlayerNames(List<String> names) {
		//no hace na
	}
	
	public List<String> getPlayerNames(){
		//no hace na
		return null;
	}
	
	public String getCurrentPlayerName() {
			return Id; 
	}
	
	@Override
	public int getCurrentPlayerWinZone() {
		return 0;
	}
	
	public boolean cpuIsPlaying() {
		return false;
	}
	
	// In principle, we do like player 0 turn --> check if player 1 has alive
	// troops...
	public boolean isFinish() {
		//communicate in
		return false;
	}
	

	@Override
	public void endTurn() {
		out.println("endTurn");
	}

	public void nextTurn() {
		out.println("nextTurn");
	}


	public void setTroop(Troop t) {
		//na
	}

	public void update() {
		out.println("update");
	}

	public void updatePlayers() {
		//communicate out
	}

	@Override
	public int getNumPlayer() {
		out.println("getNumPlayer");
		String i = "-1";
		try {
			i = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(i);
	}

	@Override
	public void selectTroop(Position pos) throws Exception {
		//na
	}
	
	@Override
	public void selectTroop(Troop t) {
		//na
	}

	public boolean isTroopSelected() {
		out.println("getNumPlayer");
		String i = "false";
		try {
			i = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i == "true"? true : false;
	}

	public boolean canMove(Position pos) {
		//communicate inout
		return false;
	}

	public void moveTroop(Position pos) throws IllegalArgumentException {
		//communicate out
	}

	public void troopAbility(Position pos) throws Exception {
		//communicate out
	}

	public void takeAim(Direction _dirToAim) {
		//communicate out
	}

	public Boolean isTroop(Position pos) {
		//communicate in
		return false;
	}

	public Game getGame() {
		//restructure
		return null;
	}

	public Troop currTroop() {
		return null;
		//communicate in
	}

	public boolean dangerTile(Position pos) {
		return false;
		//communicate in
	}

	@Override
	public List<Position> getPath(Position pos) {
		return null;
		//communicate in
	}

	public List<Position> hoverPath(Position pos) {
		return null;
		//communicate in
	}
	
	@Override
	public Icon getIcon(Position _pos) {
		//communicate in
		return null;
	}
	
	public void addObserver(GameObserver o) {
		_observers.add(o);
	}
	public void removeObserver(GameObserver o) {
		_observers.remove(o);
	}
	
	public void updateOnPlayersUpdate() {
		for (GameObserver o : _observers) {
			o.onPlayersUpdate(null);
		}
	}
	public void updateOnBoardUpdate() {
		for (GameObserver o : _observers) {
			o.onBoardUpdate(null);
		}
	}
	public void updateOnTroopAction() {
		for (GameObserver o : _observers) {
			o.onTroopAction(null);
		}
	}
	public void updateOnTroopSelection() {
		for (GameObserver o : _observers) {
			o.onTroopSelection(null);
		}	
	}
	public void updateOnNextTurn() {
		for (GameObserver o : _observers) {
			o.onNextTurn(null);
		}	
	}
	public void updateOnTroopUnSelection() {
		for (GameObserver o : _observers) {
			o.onTroopUnSelection(null);
		}	
	}

	@Override
	public void executeCommand(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
//-------------------------------------------------------------------------------------------------------------------
	@Override
	public void showGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getPrompt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String positionToString(Position _pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setupPlayers() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setUpPlayerVsCPU(int levelCPU) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(InputStream is) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayer(int idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Troop getCurrentTroop() {
		//connection inout
		return null;
	}

	@Override
	public void onDeadTroopSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Position> getEnemyTroops() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
