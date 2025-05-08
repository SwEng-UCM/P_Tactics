package P.Tactics.Multiplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

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
	 private final Map<String, BlockingQueue<String>> responseQueues;
	private BlockingQueue<String> messageQueue;
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
		messageQueue = new LinkedBlockingQueue<>();
		isFinish = false;
		isMyTurn = false;
		this.Id = Id;
		_observers = new ArrayList<>();
		responseQueues = new HashMap<>();
        for (String method : List.of("getCurrentPlayerName", "getCurrentPlayerWinZone", "getNumPlayer",
                "isTroopSelected", "canMove", "isTroop", "dangerTile", "getIcon", "getPath",
                "hoverPath", "getCurrentTroopInfo", "isWinPosition")) {
            responseQueues.put(method, new LinkedBlockingQueue<>());
        }
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
				parse();
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
	private void listen() {// for fast reading of messages
		new Thread(() -> {
		    try {
		        String msg;
		        while ((msg = in.readLine()) != null) {	
		        	messageQueue.offer(msg); 
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}).start();
	}
	private void parse() {
		new Thread(() -> {
			 try {
	                String msg;
	                while (!exit) {
	                    msg = messageQueue.take();
	                    JSONObject json = new JSONObject(msg);
	                    String method = json.getString("method");

	                    switch (method) {
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
	                        case "updateOnPlayersUpdate": updateOnPlayersUpdate(); break;
	                        case "updateOnBoardUpdate": updateOnBoardUpdate(); break;
	                        case "updateOnTroopAction": updateOnTroopAction(); break;
	                        case "updateOnTroopSelection": updateOnTroopSelection(); break;
	                        case "updateOnNextTurn": updateOnNextTurn(); break;
	                        case "updateOnTroopUnSelection": updateOnTroopUnSelection(); break;
	                        default:
	                            String data = json.has("msg") ? json.get("msg").toString() : "";
	                            BlockingQueue<String> queue = responseQueues.get(method);
	                            if (queue != null) queue.offer(data);
	                            break;
	                    }
	                }
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
		}).start();
	}
	
	@Override
    public String getCurrentPlayerName() {
        out.println("getCurrentPlayerName");
        try {
            return responseQueues.get("getCurrentPlayerName").take();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	 @Override
	    public int getCurrentPlayerWinZone() {
	        out.println("getCurrentPlayerWinZone");
	        try {
	            return Integer.parseInt(responseQueues.get("getCurrentPlayerWinZone").take());
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
	            return Integer.parseInt(responseQueues.get("getNumPlayer").take());
	        } catch (Exception e) {
	            e.printStackTrace();
	            return -1;
	        }
	    }
	 @Override
	 public boolean isTroopSelected() {
	        out.println("isTroopSelected");
	        try {
	            return responseQueues.get("isTroopSelected").take().equals("true");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	 @Override
	 public boolean canMove(Position pos) {
	        out.println("canMove " + pos.getX() + " " + pos.getY());
	        try {
	            return responseQueues.get("canMove").take().equals("true");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }


	 	@Override
	    public Boolean isTroop(Position pos) {
	        out.println("isTroop " + pos.getX() + " " + pos.getY());
	        try {
	            return responseQueues.get("isTroop").take().equals("true");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }


	 	public boolean dangerTile(Position pos) {
	        out.println("dangerTile " + pos.getX() + " " + pos.getY());
	        try {
	            return responseQueues.get("dangerTile").take().equals("true");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	
	    @Override
	    public Icon getIcon(Position _pos) {
	        out.println("getIcon "+ _pos.getX() + " " + _pos.getY());
	        try {
	            String line = responseQueues.get("getIcon").take();
	            if(line.equals("Icons/Dead.png")) return Icons.TroopIcons.DEAD;
	            return new ImageIcon(new ImageIcon(line).getImage().getScaledInstance(Position.tileSize, Position.tileSize, 4));
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
		if(isMyTurn)
	    SwingUtilities.invokeLater(() -> {
	        for (GameObserver o : _observers) {
	            o.onPlayersUpdate(null);
	        }
	    });
	}

	public void updateOnBoardUpdate() {
		if(isMyTurn)
	    SwingUtilities.invokeLater(() -> {
	        for (GameObserver o : _observers) {
	            o.onBoardUpdate(null);
	        }
	    });
	}

	public void updateOnTroopAction() {
		if(isMyTurn)
	    SwingUtilities.invokeLater(() -> {
	        for (GameObserver o : _observers) {
	            o.onTroopAction(null);
	        }
	    });
	}

	public void updateOnTroopSelection() {
		if(isMyTurn)
	    SwingUtilities.invokeLater(() -> {
	        for (GameObserver o : _observers) {
	            o.onTroopSelection(null);
	        }
	    });
	}

	public void updateOnNextTurn() {
		if(isMyTurn)
	    SwingUtilities.invokeLater(() -> {
	        for (GameObserver o : _observers) {
	            o.onNextTurn(null);
	        }
	    });
	}

	public void updateOnTroopUnSelection() {
		if(isMyTurn)
	    SwingUtilities.invokeLater(() -> {
	        for (GameObserver o : _observers) {
	            o.onTroopUnSelection(null);
	        }
	    });
	}

	@Override
	public void executeCommand(String[] args) { // comprobar updates en el thread
		String a = "";
		for(String s : args)
			a = a + " " + s;
		out.println("executeCommand" + a);
	}
    @Override
    public List<Position> getPath() {
        out.println("getPath");
        try {
            String line = responseQueues.get("getPath").take();
            if(line.equals("null")) return null;
            JSONArray positionsArray = new JSONArray(line);
            List<Position> positions = new ArrayList<>();
            for (int i = 0; i < positionsArray.length(); i++) {
                JSONObject posObj = positionsArray.getJSONObject(i);
                positions.add(new Position(posObj.getInt("x"), posObj.getInt("y")));
            }
            return positions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Position> hoverPath(Position pos) {
        out.println("hoverPath "+ pos.getX() + " " + pos.getY());
        try {
            String line = responseQueues.get("hoverPath").take();
            if(line.equals("null")) return null;
            JSONArray positionsArray = new JSONArray(line);
            List<Position> positions = new ArrayList<>();
            for (int i = 0; i < positionsArray.length(); i++) {
                JSONObject posObj = positionsArray.getJSONObject(i);
                positions.add(new Position(posObj.getInt("x"), posObj.getInt("y")));
            }
            return positions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TroopInfo getCurrentTroopInfo() {
        out.println("getCurrentTroopInfo");
        try {
            String line = responseQueues.get("getCurrentTroopInfo").take();
            if(line.equals("null")) return null;
            JSONObject obj = new JSONObject(line);
            return new TroopInfo(
                obj.getString("ID"),
                new Position(obj.getInt("x"), obj.getInt("y")),
                obj.getInt("movesLeft"),
                obj.getInt("abilityLeft")
            );
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
    public boolean isWinPosition(Position pos) {
        out.println("isWinPosition " + pos.getX() + " " + pos.getY());
        try {
            return responseQueues.get("isWinPosition").take().equals("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
