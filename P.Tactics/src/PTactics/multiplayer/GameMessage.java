package PTactics.multiplayer;

public class GameMessage { // done with the help of chatGPT
	public final ClientHandler sender;
	public final String msg;

	public GameMessage(ClientHandler clientHandler, String input) {
		sender = clientHandler;
		msg = input;
	}

}
