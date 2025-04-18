package PTactics.control;

public class Tracker {
	private static Tracker trakerInstance;
	//private Stack<Snapshot> _actionGame;
	//private Stack<Snapshot> _redoStack;
	//private ControllerInterface _ctrl;
	
	private Tracker(ControllerInterface CI) {
		//_ctrl = CI;
		//_actionGame = new Stack<>();
		//_redoStack = new Stack<>();
	}
	
	public Tracker getTracker(ControllerInterface CI) {
		if(trakerInstance == null) {
			trakerInstance = new Tracker(CI);
		}
		
		return trakerInstance;
	}
}
