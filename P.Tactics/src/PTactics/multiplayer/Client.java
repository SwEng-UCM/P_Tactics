package PTactics.multiplayer;

import PTactics.model.game.Player;

public class Client {
	public final Player player;
	public final ClientHandler handler;
	
	public Client(Player player, ClientHandler handler) {
		this.player = player;
		this.handler = handler;
	}
}
