package P.Tactics.Multiplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import P.Tactics.CPU.EasyCPU;
import P.Tactics.CPU.HardCPU;
import P.Tactics.CPU.MediumCPU;
import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.control.TroopInfo;
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
import PTactics.view.GUI.Icons;

import java.net.*; 
import java.io.*;

public class ClientController implements ControllerInterface,Observable<GameObserver> {
	private String Id;
	private Socket socket;
    BufferedReader in;
    PrintWriter out;
    private String address;
    private int port;
    private Consumer<Boolean> connected;
	private List<GameObserver> _observers;
	private volatile boolean isMyTurn;
	private boolean isFinish;
	private BlockingQueue<String> responseQueue;
	Boolean exit;
	// constructor that takes the IP Address and the Port
	public ClientController(String address, int port, String Id, Consumer<Boolean> connected) 
	{ 
		exit = false;
		this.connected = connected;
		Position._gameLength = MapSelector.getLength();
		Position._gameWidth = MapSelector.getWidth();
		this.address = address;
		this.port = port;
		responseQueue = new LinkedBlockingQueue<>();
		isFinish = false;
		isMyTurn = false;
		this.Id = Id;
		_observers = new ArrayList<>();

		
	} 
	
	public void logPlayers() {
		
			try
			{ 
				// creates a socket with the given information
				socket = new Socket(address, port); 
				System.out.println("connected");
				out = new PrintWriter(socket.getOutputStream(), true); 
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out.println(Id);
				listen();
				connected.accept(true);
				
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
			
		
	}
	private void listen() {
		new Thread(() -> {
		    try {
		        String msg;
		        while ((msg = in.readLine()) != null) {
		        	switch (msg) {
			            case "yourTurn":
			                isMyTurn = true;
			                break;
	
			            case "noTurn":
			                isMyTurn = false;
			                break;
	
			            case "isFinish":
			                isFinish = true;
			                exit = true;
			                break;
			            
			             case "updateOnPlayersUpdate":
			            	 updateOnPlayersUpdate();
			            	 break;
					     case "updateOnBoardUpdate":
						     updateOnBoardUpdate();
						     break;
	 					 case "updateOnTroopAction":
	 						 updateOnTroopAction();
	 						 break;
						 case "updateOnTroopSelection":
							 updateOnTroopSelection();
							 break;
						 case "updateOnNextTurn":
							 updateOnNextTurn();
							 break;
						 case "updateOnTroopUnSelection":
							 updateOnTroopUnSelection();
							 break;
			            default:
			                responseQueue.offer(msg); // for methods waiting on responses
			                break;
		        	}
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}).start();
	}
	@Override
	
	
	public String getCurrentPlayerName() {
		out.println("getCurrentPlayerName");
		try {
			String line = responseQueue.take();
			return line;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public int getCurrentPlayerWinZone() {
		out.println("getCurrentPlayerWinZone");
		try {
			String line = responseQueue.take();
			return Integer.parseInt(line);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	
	public boolean isFinish() {
		return isFinish;
	}
	
	public void nextTurn() {
		out.println("nextTurn");
		isMyTurn = false;
	}

	public void update() {
		out.println("update");
	}

	public void updatePlayers() {
		out.println("updatePlayers");
	}

	@Override
	public int getNumPlayer() {
		out.println("getNumPlayer");
		try {
			String line = responseQueue.take();
			return Integer.parseInt(line);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean isTroopSelected() {
		out.println("isTroopSelected");
		try {
			String line = responseQueue.take();
			return line == "true"? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean canMove(Position pos) {
		out.println("canMove " + pos.getX() + " " + pos.getY());
		try {
			String line = responseQueue.take();
			return line == "true"? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Boolean isTroop(Position pos) {
		out.println("isTroop " + pos.getX() + " " + pos.getY());
		try {
			String line = responseQueue.take();
			return line == "true"? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public boolean dangerTile(Position pos) {
		out.println("dangerTile " + pos.getX() + " " + pos.getY());
		try {
			String line = responseQueue.take();
			return line == "true"? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Icon getIcon(Position _pos) { //-----------------------------------------------------------D
		out.println("getIcon"+ _pos.getX() + " " + _pos.getY());
		try {
			String line = responseQueue.take();
			if(line != "Icons/Dead.png")
				return new ImageIcon(new ImageIcon(line).getImage().getScaledInstance(Position.tileSize,
					Position.tileSize, 4));
			
			return Icons.TroopIcons.DEAD;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
	public void executeCommand(String[] args) { // comprobar updates en el thread
		out.println("executeCommand " + args);
	}
	@Override
	public List<Position> getPath(Position pos) {
		out.println("getPath");
		try {
			String line = responseQueue.take();
			JSONArray positionsArray = new JSONArray(line);
			List<Position> positions = new ArrayList<>();
			for (int i = 0; i < positionsArray.length(); i++) {
			    JSONObject posObj = positionsArray.getJSONObject(i);
			    int x = posObj.getInt("x");
			    int y = posObj.getInt("y");
			    positions.add(new Position(x, y));
			}
			return positions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Position> hoverPath(Position pos) {
		out.println("hoverPath");
		try {
			String line = responseQueue.take();
			JSONArray positionsArray = new JSONArray(line);
			List<Position> positions = new ArrayList<>();
			for (int i = 0; i < positionsArray.length(); i++) {
			    JSONObject posObj = positionsArray.getJSONObject(i);
			    int x = posObj.getInt("x");
			    int y = posObj.getInt("y");
			    positions.add(new Position(x, y));
			}
			return positions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TroopInfo getCurrentTroopInfo() {
		out.println("getCurrentTroop");
		try {
			String line = responseQueue.take();
			JSONObject obj = new JSONObject(line);
			String ID = obj.getString("ID");
			int x = obj.getInt("x");
			int y = obj.getInt("y");
			Position pos = new Position(x,y);
			int movesLeft = obj.getInt("movesLeft");
			int abilityLeft = obj.getInt("abilityLeft");
			return new TroopInfo(ID,pos,movesLeft, abilityLeft);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public boolean isOnline() {
		return true;
	}

	@Override
	public boolean isMyTurn() {
		return isMyTurn;
	}
	
//-------------------------------------------------------------------------------------------------------------------

	
	public boolean cpuIsPlaying() {
		//na
		return false;
	}
	@Override
	public Player getPlayer() {
		//na
		return null;
	}

	@Override
	public Player getPlayer(int idx) {
		//na
		return null;
	}
	@Override
	public void endTurn() {
		//na
	}
	public void moveTroop(Position pos) throws IllegalArgumentException {
		//na
	}

	public void troopAbility(Position pos) throws Exception {
		//na
	}

	public void takeAim(Direction _dirToAim) {
		//na
	}
	public List<String> getPlayerNames(){
		// na
		return null;
	}
	public void setTroop(Troop t) {
		//na
	}
	public void setPlayerNum(int playerNum) {
		// na
	}

	public void setPlayerNames(List<String> names) {
		//na
	}
	
	public void setMap(int i) { 
		//na
	}
	
	public void createGame() {
		//na
	}
	
	@Override
	public void selectTroop(Position pos) throws Exception {
		//na
	}
	
	@Override
	public void selectTroop(Troop t) {
		//na
	}

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
	public void onDeadTroopSelected() {
		out.println("onDeadTroopSelected");
	}

	@Override
	public List<Position> getEnemyTroops() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Troop getCurrentTroop() {
		// TODO Auto-generated method stub
		return null;
	}


}
