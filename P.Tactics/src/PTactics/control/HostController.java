package PTactics.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.Icon;

import org.json.JSONObject;

import PTactics.model.game.Game;
import PTactics.model.game.Observable;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.view.GameObserver;


import java.net.*; 
import java.io.*;

public class HostController implements ControllerInterface,Observable<GameObserver> {
	private Socket socket;
	private ServerSocket server;
	private DataInputStream in;
	private DataOutputStream out;
	private List<Client> _clients;
	private List<GameObserver> _observers;
	private int numPlayers;
	private ClientHandler currentClient;
    private BlockingQueue<GameMessage> messageQueue;

	// constructor that takes the IP Address and the Port
	public HostController(int port, int numPlayers) 
	{ 
		messageQueue = new LinkedBlockingQueue<>();
		this.numPlayers = numPlayers;
		_observers = new ArrayList<>();
		_clients = new ArrayList<>();
		try
		{ 
			// we start our server
			server = new ServerSocket(port); 
			System.out.println("Server started"); 
			
			int i = 0;
			while (i < numPlayers) { // will keep going until all players are connected
	            Socket clientSocket = server.accept();
	            System.out.println("Client connected: " + clientSocket.getInetAddress());
	            
	            ClientHandler handler = new ClientHandler(clientSocket);
	            Player player = new Player(null, null);
	            _clients.add(new Client(player,handler));
	            new Thread(handler).start();
	        }
			while (!this.isFinish()) {
				GameMessage msg = messageQueue.take(); // blocks until there's a message

			    if (msg.sender != currentClient) {
			        msg.sender.sendMessage("noTurn");
			        continue; // ignore message
			    }

			    // Now it's the right player's turn. Parse and react:
			    
			}
	
		} 
		catch(IOException | InterruptedException i) 
		{ 
			System.out.println(i); 
		} 
	} 
	
	private void exeParse(String input, ClientHandler handler) { // parses a message and executes it
		
	}
	

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayers() {
		// TODO Auto-generated method stub
		
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
	public void selectTroop(Position pos) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTroopSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMove(Position pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveTroop(Position pos) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void troopAbility(Position pos) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeAim(Direction _dirToAim) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectTroop(Troop t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String positionToString(Position _pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isTroop(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlayerNum(int playerNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupPlayers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Troop currTroop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getIcon(Position _pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dangerTile(Position _pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Position> getPath(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Position> hoverPath(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPlayerNames(List<String> names) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getPlayerNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentPlayerName() {
		// TODO Auto-generated method stub
		return null;
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

}