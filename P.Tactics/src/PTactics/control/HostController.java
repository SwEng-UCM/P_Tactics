package PTactics.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import javax.swing.Icon;

import org.json.JSONObject;
import org.json.JSONTokener;

import PTactics.control.commands.Command;
import PTactics.control.commands.CommandGenerator;
import PTactics.control.maps.MapSelector;
import PTactics.model.game.Board;
import PTactics.model.game.DangerMediator;
import PTactics.model.game.Game;
import PTactics.model.game.Observable;
import PTactics.model.game.OnlineGame;
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
	int currClientIndex;
	boolean online;
	protected List<String> _playerNames;
	// constructor that takes the IP Address and the Port
	public HostController(int port, int numPlayers, String name, Consumer<Integer> onPlayerConnected) 
	{ 
		online = true;
		_numPlayers = numPlayers;
		currClientIndex = 0;
		this.name = name;
		connected = 1;
		_game = new OnlineGame(this);
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
			

			
			if (onPlayerConnected != null) {
                onPlayerConnected.accept(connected);
            }
			DangerMediator dangerMediatorh = new DangerMediator(); // add host to the list of clients with null handler
            Player ph = new Player(Integer.toString(connected), dangerMediatorh);
            _clients.add(new Client(ph, null));
            _playerNames.add(name);
            
			while (connected < numPlayers) { // will keep going until all players are connected (except host)
					
				Socket clientSocket = server.accept(); // Accept immediately
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
	        }

            
            run();
	
		} 
		catch(IOException | InterruptedException i) 
		{ 
			System.out.println(i); 
		} 
	} 
	private void run() throws InterruptedException {
		while (!this.isFinish()) {
			GameMessage msg = messageQueue.take(); // blocks until there's a message

		    if (msg.sender != currentClient.handler) { // not the current player
		        msg.sender.sendMessage("noTurn");
		        continue; // ignore message
		    }
		    //yes the current player :)
		    exeParse(msg.msg, msg.sender);
		}
	}
	
	private void exeParse(String input, ClientHandler handler) { // parses a message and executes it
		
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
		// TODO Auto-generated method stub
		return null;
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
	
	public List<String> getPlayerNames(){
		List<String> _playerNames = new ArrayList<>();
		for(Client c: _clients) {
			_playerNames.add(c.player.getId());
		}
		return _playerNames;
	}
	
	@Override
	public int getCurrentPlayerWinZone() {
		return Board.getInstance().pointsToWin() - currentClient.player.winPoints();
	}
	
	public String getCurrentPlayerName() {
		return currentClient.player.getId();
		
	}
	

	public boolean isFinish() {
		for (Troop t : currentClient.player.getTroops()) {
			if (t.isAlive())
				return false;
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
		return 0 ; // clients + host
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
		return _game.isTroopSelected();
	}

	public boolean canMove(Position pos) {
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
		return this._game.isTroop(pos);
	}

//	public Game getGame() { 
//		return this._game;
//	}

	public Troop currTroop() {
		return _game.getCurrentTroop();
	}

	public boolean dangerTile(Position pos) {
		return _game.dangerTile(pos);
	}

	@Override
	public List<Position> getPath(Position pos) {
		return _game.getPath(pos);
	}

	public List<Position> hoverPath(Position pos) {
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
	public Troop getCurrentTroop(){
		return _game.getCurrentTroop();
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