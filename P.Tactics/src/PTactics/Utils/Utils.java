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
		public final String PROMPT = "Command > ";
		public final String DEBUG = "[DEBUG] Executing: %s%n";
		public final String ERROR = "[ERROR] Error: %s%n";
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
		public static final String UNKNOWN_COMMAND = "Unknown command";
		public static final String COMMAND_PARAMETERS_MISSING = "Missing parameters";
		public static final String COMMAND_INCORRECT_PARAMETER_NUMBER = "Incorrect parameter number";
	}
	
	public static interface Data {
		public final int STARTING_SOLDIERS = 3;
	}
	
	public static interface CommandInfo {
		//Help command
		public static final String COMMAND_HELP_NAME = "help";
		public static final String COMMAND_HELP_SHORTCUT = "h";
		public static final String COMMAND_HELP_DETAILS = "[h]elp";
		public static final String COMMAND_HELP_HELP = "shows this help";
		//Move command
		public static final String COMMAND_MOVE_NAME = "move";
		public static final String COMMAND_MOVE_SHORTCUT = "m";
		public static final String COMMAND_MOVE_DETAILS = "[m]ove";
		public static final String COMMAND_MOVE_HELP = "given a valid position, the troop will move there";
		//Aim command
		public static final String COMMAND_AIM_NAME = "aim";
		public static final String COMMAND_AIM_SHORTCUT = "a";
		public static final String COMMAND_AIM_DETAILS = "[a]im";
		public static final String COMMAND_AIM_HELP = "given a valid direction, the troop will aim in that direction";
		//Exit command (end the turn)
		public static final String COMMAND_EXIT_NAME = "exit";
		public static final String COMMAND_EXIT_SHORTCUT = "e";
		public static final String COMMAND_EXIT_DETAILS = "[e]xit";
		public static final String COMMAND_EXIT_HELP = "ends the turn";
		//Select command
		public static final String COMMAND_SELECT_NAME = "select";
		public static final String COMMAND_SELECT_SHORTCUT = "s";
		public static final String COMMAND_SELECT_DETAILS = "[s]elect";
		public static final String COMMAND_SELECT_HELP = "selects a soldier to move, aim or do special action";
	}
}
