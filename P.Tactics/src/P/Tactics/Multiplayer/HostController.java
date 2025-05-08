package P.Tactics.Multiplayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import javax.swing.Icon;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import PTactics.control.ControllerInterface;
import PTactics.control.TroopInfo;
import PTactics.control.commands.Command;
import PTactics.control.commands.CommandGenerator;
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
import PTactics.view.GameObserver;


import java.net.*; 
import java.io.*;

public class HostController implements ControllerInterface,Observable<GameObserver> {
	private ServerSocket server;
	private List<Client> _clients;
	private List<GameObserver> _observers;
	private Client currentClient;
    private BlockingQueue<GameMessage> messageQueue;
    protected OnlineGame _game;
	protected boolean _endTurn;
	public static int mapSelected = 1;
	public static int tileSize = 50;
	protected int _numPlayers;
	int connected;
	Player hostPlayer;
	String name;
	volatile int currClientIndex;
	protected List<String> _playerNames;
	Consumer<Integer> onPlayerConnected;
	boolean exit;
	// constructor that takes the IP Address and the Port
	public HostController(int port, int numPlayers, String name, Consumer<Integer> onPlayerConnected) 
	{ 
		this.onPlayerConnected = onPlayerConnected;
		_numPlayers = numPlayers;
		currClientIndex = 0;
		this.name = name;
		connected = 1;
		_endTurn = false;
		messageQueue = new LinkedBlockingQueue<>();
		_observers = new ArrayList<>();
		_clients = new ArrayList<>();
		_playerNames = new ArrayList<>();
		exit = false;
		try
		{ 
			// we start our server
			server = new ServerSocket(port); 
			System.out.println("Server started"); 
			

			
			if (this.onPlayerConnected != null) {
                this.onPlayerConnected.accept(connected);
            }
			DangerMediator dangerMediatorh = new DangerMediator(); // add host to the list of clients with null handler
            Player ph = new Player(Integer.toString(connected), dangerMediatorh);
            for (Troop t : MapSelector.getTroops(ph)) {
                Board.getInstance().addObj(t.getPos(), t);
            }
            hostPlayer = ph;
            _clients.add(new Client(ph, null));
            _playerNames.add(name);
            currentClient = _clients.get(currClientIndex);
	
		}
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 
	public void logPlayers() {
		try {
			listen();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (connected < _numPlayers) { // will keep going until all players are connected (except host)
			
			Socket clientSocket;
			try {
				clientSocket = server.accept();

		    System.out.println("Client connected: " + clientSocket.getInetAddress());

		    // Hand off the rest to a separate thread

            connected++;
		    new Thread(() -> {
		        try {
		            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

		            String playerID = in.readLine(); 
		            DangerMediator dangerMediator = new DangerMediator();
		            Player p = new Player(Integer.toString(connected), dangerMediator);

		            synchronized (Board.getInstance()) {
		                for (Troop t : MapSelector.getTroops(p)) {
		                    Board.getInstance().addObj(t.getPos(), t);
		                }
		            }

		            ClientHandler handler = new ClientHandler(in, out, messageQueue);
		            _clients.add(new Client(p, handler));
		            _playerNames.add(playerID);
		            new Thread(handler).start();

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }).start();

		     // increment here so we stop after the expected number of players
		    if (onPlayerConnected != null) {
                onPlayerConnected.accept(connected);
            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Accept immediately
        }
	}
	private void listen() throws InterruptedException {
		new Thread (()->{
			while (!exit) {
				GameMessage msg;
				try {
					msg = messageQueue.take();
			    //yes the current player :)
					exeParse(msg.msg, msg.sender);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // blocks until there's a message
	
			}
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
	
	private void exeParse(String input, ClientHandler handler) {
	    String[] tokens = input.trim().split("\\s+");
	    String command = tokens[0];

	    switch (command) {
	        case "nextTurn":
	            this.nextTurn(); break;
	        case "getIcon":
	            this.getIcon(handler, new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))); break;
	        case "executeCommand":
	            this.executeCommand(Arrays.copyOfRange(tokens, 1, tokens.length)); break;
	        case "update":
	            this.update(); break;
	        case "getCurrentPlayerName":
	            this.getCurrentPlayerName(handler); break;
	        case "updatePlayers":
	            this.updatePlayers(); break;
	        case "getNumPlayer":
	            this.getNumPlayer(handler); break;
	        case "isTroopSelected":
	            this.isTroopSelected(handler); break;
	        case "canMove":
	            this.canMove(handler, new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))); break;
	        case "isTroop":
	            this.isTroop(handler, new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))); break;
	        case "dangerTile":
	            this.dangerTile(handler, new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))); break;
	        case "getPath":
	            this.getPath(handler, new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))); break;
	        case "hoverPath":
	            this.hoverPath(handler, new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))); break;
	        case "getCurrentTroop":
	            this.getCurrentTroopInfo(handler); break;
	        case "onDeadTroopSelected":
	            this.onDeadTroopSelected(); break;
	        case "isWinPosition":
	            this.isWinPosition(handler, new Position(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]))); break;
	        case "getCurrentPlayerWinZone":
	        	this.getCurrentPlayerWinZone(); break;
	    }
	}


	@Override
	public void showGame() {
		// na
		
	}

	@Override
	public String[] getPrompt() {
		// na
		return null;
	}

	@Override
	public void showMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInt() {
		// na
		return 0;
	}


	@Override
	public String positionToString(Position _pos) {
		// na
		return null;
	}


	@Override
	public Icon getIcon(Position _pos) {
	    return _game.positionToIcon(_pos);
	}
	public void getIcon(ClientHandler handler, Position _pos) {
	    sendJsonMessage(handler,"getIcon",_game.positionToIcon(_pos).toString());
	}



	@Override
	public void setUpPlayerVsCPU(int levelCPU) {
		// TODO Auto-generated method stub
		
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
		for(Client c : _clients)
			if(c.handler != null)sendJsonMessage(c.handler,"updateOnPlayersUpdate" , "a");
	}
	public void updateOnBoardUpdate() {
		for (GameObserver o : _observers) {
			o.onBoardUpdate(null);
		}
		for(Client c : _clients)
			if(c.handler != null)sendJsonMessage(c.handler,"updateOnBoardUpdate" , "a");
	}
	public void updateOnTroopAction() {
		for (GameObserver o : _observers) {
			o.onTroopAction(null);
		}
		for(Client c : _clients)
			if(c.handler != null) sendJsonMessage(c.handler,"updateOnTroopAction" , "a");
	}
	public void updateOnTroopSelection() {
		for (GameObserver o : _observers) {
			o.onTroopSelection(null);
		}
		for(Client c : _clients)
			if(c.handler != null)sendJsonMessage(c.handler,"updateOnTroopSelection" , "a");
	}
	public void updateOnNextTurn() {
		for (GameObserver o : _observers) {
			o.onNextTurn(null);
		}
		for(Client c : _clients)
			if(c.handler != null)sendJsonMessage(c.handler,"updateOnNextTurn" , "a");
	}
	public void updateOnTroopUnSelection() {
		for (GameObserver o : _observers) {
			o.onTroopUnSelection(null);
		}
		for(Client c : _clients)
			if(c.handler != null)sendJsonMessage(c.handler,"updateOnTroopUnSelection" , "a");
	}

	@Override
	public void executeCommand(String[] args) {
		Command command = CommandGenerator.parse(args);
		command.execute(this);
	}
	
	/* IMPORTANTE AÑADIR
	 try
		{ 
			out.close(); 
			socket.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	 */
//_____________________________________________________________________________________________________________

	
	public JSONObject report() {
		return null;
	}
	
	public void setPlayerNum(int playerNum) {
		this._numPlayers = playerNum;
	}

	public void setPlayerNames(List<String> names) {
		//na
	}
	
	public void setMap(int i) { 
		MapSelector.mapSelected = i + 1;
	}
	
	public void createGame() {
		_game = new OnlineGame(this);
	}
	
	public List<String> getPlayerNames(){
		List<String> _playerNames = new ArrayList<>();
		for(Client c: _clients) {
			_playerNames.add(c.player.getId());
		}
		return _playerNames;
	}
	
	@Override
	public int getCurrentPlayerWinZone() {//-------
		return Board.getInstance().pointsToWin() - currentClient.player.winPoints();
	}
	public void getCurrentPlayerWinZone(ClientHandler h) {//-------
		sendJsonMessage(h, "getCurrentPlayerWinZone", String.valueOf(Board.getInstance().pointsToWin() - currentClient.player.winPoints()));
	}
	
	public String getCurrentPlayerName() {
		return currentClient.player.getId();	
	}
	private void getCurrentPlayerName(ClientHandler h) {
		sendJsonMessage(h,"getCurrentPlayerName",currentClient.player.getId());	
	}

	public boolean isFinish() {
		
		boolean is = (currentClient.player.winPoints() >=  Board.getInstance().pointsToWin()) || isLastPlayerStanding();
		
		if(is) {
			exit = true;
			for(Client c : _clients) if(c.handler != null) c.handler.sendMessage("isFinish"); // shut down others
		}
		return is;
	}
	
	public boolean isLastPlayerStanding() {
		for (Client c: _clients) {
			if (!c.player.equals(getPlayer())) {	
				for (Troop troop: c.player.getTroops()) {
					if (troop.isAlive()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	public void setupPlayers() {
		//na
	}

	@Override
	public void endTurn() {
		_endTurn = true;
	}

	public void nextTurn() {
		Board.getInstance().nextTurn();
		_game.nextTurn();
		getPlayer().endTurn();
		getPlayer().clearKills();
		getPlayer().update();
		update();
		
		do {
			currClientIndex++;
			if (currClientIndex >= _clients.size()) {
				currClientIndex = 0;
			}
			currentClient = _clients.get(currClientIndex);
			if(currentClient.handler != null) {
				currentClient.handler.sendMessage("yourTurn");
			}
		} while (getPlayer().hasNoTroopsLeft());
		
		getPlayer().startOfTurnDeadCheck();
		getPlayer().startTurn();
		updateOnNextTurn();
		getPlayer().ComputeTurn();
	}

	@Override
	public void update() {
		this._game.update();
	}

	public void updatePlayers() {
		for (Client p : _clients) {
			p.player.update();
		}
		updateOnPlayersUpdate();
	}

	@Override
	public int getNumPlayer() {
	    return connected;
	}
	public void getNumPlayer(ClientHandler handler) {
		sendJsonMessage(handler, "getNumPlayer",String.valueOf(connected));
	}


	@Override
	public void selectTroop(Position pos) throws Exception {
		_game.selectTroop(pos);
	}
	
	@Override
	public void selectTroop(Troop t) {
		_game.selectTroop(t);
	}
	
	@Override
	public boolean isTroopSelected() {
	    return _game.isTroopSelected();
	}
	public void isTroopSelected(ClientHandler handler) {
		sendJsonMessage(handler,"isTroopSelected", _game.isTroopSelected() ? "true" : "false");
	}

	@Override
	public boolean canMove(Position pos) {
	    return _game.canMove(pos);
	}
	public void canMove(ClientHandler handler, Position pos) {
		sendJsonMessage(handler,"canMove",_game.canMove(pos) ? "true" : "false");
	}


	public void moveTroop(Position pos) throws IllegalArgumentException {
		_game.moveTroop(pos);
	}

	public void troopAbility(Position pos) throws Exception {
		_game.troopAbility(pos);
	}

	public void takeAim(Direction _dirToAim) {
		_game.takeAim(_dirToAim);
	}

	public Boolean isTroop(Position pos) {
	    return this._game.isTroop(pos);
	}
	public void isTroop(ClientHandler handler, Position pos) {
		sendJsonMessage(handler,"isTroop",_game.isTroop(pos) ? "true" : "false");
	}


//	public Game getGame() { 
//		return this._game;
//	}

	public Troop getCurrentTroop() {
		return _game.getCurrentTroop();
	}

	public boolean dangerTile(Position pos) {
	    return _game.dangerTile(pos);
	}
	public void dangerTile(ClientHandler handler, Position pos) {
		sendJsonMessage(handler, "dangerTile",_game.dangerTile(pos) ? "true" : "false");
	}


	@Override
	public List<Position> getPath(Position pos) {
	    return _game.getPath(pos);
	}
	public void getPath(ClientHandler handler, Position pos) {
	    List<Position> path = _game.getPath(pos);
	    if (path != null) {
	        JSONArray positionsArray = new JSONArray();
	        for (Position p : path) {
	            JSONObject posObj = new JSONObject();
	            posObj.put("x", p.getX());
	            posObj.put("y", p.getY());
	            positionsArray.put(posObj);
	        }
	        sendJsonMessage(handler, "getPath", positionsArray.toString());
	    } else {
	    	sendJsonMessage(handler, "getPath", null);
	    }
	}

	@Override
	public List<Position> hoverPath(Position pos) {
	    return _game.hoverPath(pos);
	}
	public void hoverPath(ClientHandler handler, Position pos) {
	    List<Position> path = _game.hoverPath(pos);
	    if (path != null) {
	        JSONArray positionsArray = new JSONArray();
	        for (Position p : path) {
	            JSONObject posObj = new JSONObject();
	            posObj.put("x", p.getX());
	            posObj.put("y", p.getY());
	            positionsArray.put(posObj);
	        }
	        sendJsonMessage(handler, "hoverPath",positionsArray.toString());
	    } else {
	    	sendJsonMessage(handler, "hoverPath", null);
	    }
	}

	
	@Override
	public void load(InputStream is) {
		//na
	}

	private void _loadPlayers(JSONObject gameState) {
	 //na
	}
	
	private void _loadBoard(JSONObject gameState) {
		//na
	}
	
	protected void _loadController(JSONObject gameState) {
		//na
	}

	


	 //Player Management
	public void inicialize() { // total update, only called on the setup
		InicializeTurns();
		Board.getInstance().update();
		inicializePlayers();
	}
	
	private void InicializeTurns() {
		_clients.get(0).player.startTurn();
	}

	private void inicializePlayers() {
		for (Client p : _clients) {
			p.player.update();
		}
	}

	public Player getPlayer() {
		return this.currentClient.player;
	}

	public Player getPlayer(int idx) {
		return this._clients.get(idx - 1).player;
	}
	@Override
	public TroopInfo getCurrentTroopInfo(){
	    boolean exists = _game.getCurrentTroop() != null;
	    return exists ? new TroopInfo(_game.getCurrentTroop().getId(), _game.getCurrentTroop().getPos(), _game.getCurrentTroop().getMovesLeft(), _game.getCurrentTroop().abilityUsesLeft()) : null;
	}
	public void getCurrentTroopInfo(ClientHandler handler) {
	    Troop troop = _game.getCurrentTroop();
	    if (troop != null) {
	        TroopInfo info = new TroopInfo(troop.getId(), troop.getPos(), troop.getMovesLeft(), troop.abilityUsesLeft());
	        sendJsonMessage(handler, "getCurrentTroopInfo", info.report().toString());
	    } else {
	    	sendJsonMessage(handler, "getCurrentTroopInfo", null);
	    }
	}

	public void onDeadTroopSelected() {
		_game.onDeadTroopSelected();
	}

	@Override
	public boolean cpuIsPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Position> getEnemyTroops() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isOnline() {
		return true;
	}
	

	@Override
	public boolean isMyTurn() {
		return hostPlayer.getId() == currentClient.player.getId()? true : false;
	}
	public void exit() {
		exit = true;
	}
	@Override
	public boolean isWinPosition(Position pos) {
	    return Board.getInstance().isWinPosition(pos);
	}
	public void isWinPosition(ClientHandler handler, Position pos) {
		sendJsonMessage(handler, "isWinPosition",Board.getInstance().isWinPosition(pos) ? "true" : "false");
	}
	private void sendJsonMessage(ClientHandler handler, String methodName, String msgContent) {
        JSONObject message = new JSONObject();
        message.put("method", methodName);
        message.put("msg", msgContent);
        handler.sendMessage(message.toString());
    }

}