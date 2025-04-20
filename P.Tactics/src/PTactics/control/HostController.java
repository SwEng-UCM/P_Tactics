package PTactics.control;

import java.util.List;

import javax.swing.Icon;

import org.json.JSONObject;

import PTactics.model.game.Game;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.view.GameObserver;


import java.net.*; 
import java.io.*;

public class HostController implements ControllerInterface {
	private Socket socket;
	private ServerSocket server;
	private DataInputStream in;

	// constructor that takes the IP Address and the Port
	public HostController(String address, int port) 
	{ 
		try
		{ 
			// we start our server
			server = new ServerSocket(port); 
			System.out.println("Server started"); 

			System.out.println("Waiting for a client ..."); 

			// we accept the client in the given port
			// and create a socket
			// we now have an established connection between our client and our server on the 
			// given socket
			socket = server.accept(); 
			System.out.println("Client accepted"); 

			// takes input from the client socket 
			in = new DataInputStream( 
				new BufferedInputStream(socket.getInputStream())); 

			String line = ""; 

			// reads message from client until "Stop" is sent 
			while (!line.equals("Stop")) 
			{ 
				try
				{ 
					line = in.readUTF(); 
					System.out.println(line); 

				} 
				catch(IOException i) 
				{ 
					System.out.println(i); 
				} 
			} 
			System.out.println("Closing connection"); 

			// close connection 
			socket.close(); 
			in.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
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
	public void setTroop(Troop t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addObserver(GameObserver o) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void selectTroop(Troop t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(GameObserver o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOnPlayersUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOnBoardUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOnTroopAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOnTroopSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOnNextTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOnTroopUnSelection() {
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