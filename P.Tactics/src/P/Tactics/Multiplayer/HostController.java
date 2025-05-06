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
	boolean online;
	protected List<String> _playerNames;
	Consumer<Integer> onPlayerConnected;
	// constructor that takes the IP Address and the Port
	public HostController(int port, int numPlayers, String name, Consumer<Integer> onPlayerConnected) 
	{ 
		this.onPlayerConnected = onPlayerConnected;
		online = false;
		_numPlayers = numPlayers;
		currClientIndex = 0;
		this.name = name;
		connected = 1;
		_endTurn = false;
		messageQueue = new LinkedBlockingQueue<>();
		_observers = new ArrayList<>();
		_clients = new ArrayList<>();
		_playerNames = new ArrayList<>();
		
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
		try {
			listen();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void listen() throws InterruptedException {
		new Thread (()->{
			while (!this.isFinish()) {
				GameMessage msg;
				try {
					msg = messageQueue.take();

			    if (msg.sender != currentClient.handler) { // not the current player
			        msg.sender.sendMessage("noTurn");
			        continue; // ignore message
			    }
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
	
	private void exeParse(String input, ClientHandler handler) { // parses a message and executes it
		String[] tokens = input.trim().split("\\s+");
	    String command = tokens[0];

	    switch (command) {
	        case "nextTurn":
	            this.nextTurn();  // Call your logic
	            break;
	        case "getIcon":
	        	this.getIcon(new Position(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
	        	break;
	        case "executeCommand":
	        	String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);	        
	        	this.executeCommand(args);
	        case "getCurrentPlayerName":
	        	this.getCurrentPlayerName();
	        	break;
	        case "update":
	        	this.update();
	        	break;
	        case "updatePlayers":
	        	this.updatePlayers();
	        	break;
	        case "getNumPlayer":
	        	this.getNumPlayer();
	        	break;
	        case "isTroopSelected":
	        	this.isTroopSelected();
	        	break;
	        case"canMove":     	
	        	this.canMove(new Position(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
	        	break;
	        case"isTroop":
	        	this.isTroop(new Position(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
	        	break;
	        case"dangerTile":
	        	this.dangerTile(new Position(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
	        	break;
	        case"getPath":
	        	this.getPath(new Position(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
	        	break;
	        case"hoverPath":
	        	this.hoverPath(new Position(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
	        	break;
	        case"getCurrentTroopInfo":
	        	this.getCurrentTroopInfo();
	        	break;
	        case"onDeadTroopSelected":
	        	this.onDeadTroopSelected();
	        	break;
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
		String direction = _game.positionToIcon(_pos).toString();
		if(online) currentClient.handler.sendMessage(direction);
		return _game.positionToIcon(_pos);
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
		if(online)currentClient.handler.sendMessage("updateOnPlayersUpdate");
	}
	public void updateOnBoardUpdate() {
		for (GameObserver o : _observers) {
			o.onBoardUpdate(null);
		}
		if(online)currentClient.handler.sendMessage("updateOnBoardUpdate");
	}
	public void updateOnTroopAction() {
		for (GameObserver o : _observers) {
			o.onTroopAction(null);
		}
		if(online)currentClient.handler.sendMessage("updateOnTroopAction");
	}
	public void updateOnTroopSelection() {
		for (GameObserver o : _observers) {
			o.onTroopSelection(null);
		}
		if(online)currentClient.handler.sendMessage("updateOnTroopSelection");
	}
	public void updateOnNextTurn() {
		for (GameObserver o : _observers) {
			o.onNextTurn(null);
		}
		if(online)currentClient.handler.sendMessage("updateOnNextTurn");
	}
	public void updateOnTroopUnSelection() {
		for (GameObserver o : _observers) {
			o.onTroopUnSelection(null);
		}
		if(online)currentClient.handler.sendMessage("updateOnTroopUnSelection");
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
	public int getCurrentPlayerWinZone() {
		if(online)currentClient.handler.sendMessage(String.valueOf(Board.getInstance().pointsToWin() - currentClient.player.winPoints()));
		return Board.getInstance().pointsToWin() - currentClient.player.winPoints();
	}
	
	public String getCurrentPlayerName() {
		if (online) currentClient.handler.sendMessage(currentClient.player.getId());
		return currentClient.player.getId();	
	}
	

	public boolean isFinish() {
		
		boolean is = (currentClient.player.winPoints() >=  Board.getInstance().pointsToWin()) || isLastPlayerStanding();
		
		if(online)currentClient.handler.sendMessage("isFinish");
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
			if(currentClient.handler == null) online = false;
			else online = true;
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
		return connected ; // clients + host
	}

	@Override
	public void selectTroop(Position pos) throws Exception {
		_game.selectTroop(pos);
	}
	
	@Override
	public void selectTroop(Troop t) {
		_game.selectTroop(t);
	}

	public boolean isTroopSelected() {
		if (online) currentClient.handler.sendMessage(_game.isTroopSelected()? "true" : "false");
		return _game.isTroopSelected();
	}

	public boolean canMove(Position pos) {
		if (online) currentClient.handler.sendMessage(_game.canMove(pos)? "true" : "false");
		return _game.canMove(pos);
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
		if (online) currentClient.handler.sendMessage(_game.isTroop(pos)? "true" : "false");
		return this._game.isTroop(pos);
	}

//	public Game getGame() { 
//		return this._game;
//	}

	public Troop getCurrentTroop() {
		return _game.getCurrentTroop();
	}

	public boolean dangerTile(Position pos) {
		if (online)currentClient.handler.sendMessage(_game.dangerTile(pos)? "true" : "false");
		return _game.dangerTile(pos);
	}

	@Override
	public List<Position> getPath(Position pos) { //sending a JSON array through the socket because it was revealed to me in a dream
		if(online) {
			JSONArray positionsArray = new JSONArray();
			for (Position p : _game.getPath(pos)) {
			    JSONObject posObj = new JSONObject();
			    posObj.put("x", p.getX());
			    posObj.put("y", p.getY());
			    positionsArray.put(posObj);
			}
			currentClient.handler.sendMessage(positionsArray.toString());
		}
		return _game.getPath(pos);
	}

	public List<Position> hoverPath(Position pos) {
		if(online) {
			JSONArray positionsArray = new JSONArray();
			for (Position p : _game.hoverPath(pos)) {
			    JSONObject posObj = new JSONObject();
			    posObj.put("x", p.getX());
			    posObj.put("y", p.getY());
			    positionsArray.put(posObj);
			}
			currentClient.handler.sendMessage(positionsArray.toString());
		}
		return _game.hoverPath(pos);
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
	public TroopInfo getCurrentTroopInfo(){
		boolean a = _game.getCurrentTroop() != null;
		TroopInfo info = a? new TroopInfo(_game.getCurrentTroop().getId(),_game.getCurrentTroop().getPos(),_game.getCurrentTroop().getMovesLeft(), _game.getCurrentTroop().abilityUsesLeft()):null;
		if(online) { 
			if(a) currentClient.handler.sendMessage(info.report().toString());
			else currentClient.handler.sendMessage(null);
		}
		return info;
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
}