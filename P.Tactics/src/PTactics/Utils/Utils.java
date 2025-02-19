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
		
	}
}
