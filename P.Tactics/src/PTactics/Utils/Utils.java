package PTactics.Utils;

//Dedicated to store constants in our program such as messages, icons, etc..
public interface Utils {
	
	//Dedicated to troops
	public static interface TroopUtils {
		public final String TROOP_FACING_UP = "▲" ;
		public final String TROOP_FACING_DOWN = "▼" ;
		public final String TROOP_FACING_LEFT = "◀" ;
		public final String TROOP_FACING_RIGHT = "▶" ;
		public final String TROOP_ICON = "▲";
		public final String TROOP_DEAD = "💀";
	}
	
	//Dedicated to walls
	public static interface WallUtils {
		public final String WALL_ICON = "🧱";
	}
	
	//Dedicated to messages to the players of the game
	public static interface MessageUtils {
		public final String WELCOME_MSG = "Welcome to P.Tactics!";
		public final String ASK_NUMBER_PLAYERS = "Please input the number of players: ";
		public final String ASK_SELECT_SOLDIER = "Select a troop: ";
		public final String START_TURN = "Press \"ENTER\" to start the turn";
	}
	
	//Dedicated to messages when errors occur
	public static interface MsgErrors {
		public final String INVALID_NUM_PLAYERS = "That is a invalid number of players";
		public final String INVALID_INPUT = "Incorrect input";
		public final String INVALID_COORDINATES = "Invalid coordinates";
		public final String INVALID_SELECTION = "Invalid troop selection";
	}
	
	public static interface Data {
		public final int STARTING_SOLDIERS = 3;
	}
}
