package PTactics.control;

import java.util.Stack;

import PTactics.control.commands.Snapshot;
import PTactics.model.game.Game;
import PTactics.view.GameObserver;

public class Tracker implements GameObserver {
	private static Tracker trakerInstance;
//	private Stack<Snapshot> _allActions;	In case we want to have the entire game stored
	private Stack<Snapshot> _actionGame;
	private Stack<Snapshot> _redoStack;

	private Tracker(ControllerInterface CI) {
		CI.addObserver(this);
		_actionGame = new Stack<>();
//		_allActions = new Stack<>();
		_redoStack = new Stack<>();
	}

	public static Tracker getTracker(ControllerInterface CI) {
		if (trakerInstance == null) {
			trakerInstance = new Tracker(CI);
		}

		return trakerInstance;
	}

	public void saveAction(Snapshot snap) {
//		_allActions.add(Snap);
		_actionGame.add(snap);
	}

	// Use this pop when undoing
	public Snapshot popUndo() {
		if (!_actionGame.isEmpty()) {
			_redoStack.add(_actionGame.peek());
			return _actionGame.pop();
		}

		return null;
	}

	// Use this pop when redoing
	public Snapshot popRedo() {
		if (!_redoStack.isEmpty()) {
//			_actionGame.add(_redoStack.peek());
			return _redoStack.pop();
		}

		return null;
	}

	public void pop() {
		_actionGame.pop();
	}

	@Override
	public void onPlayersUpdate(Game game) {
	}

	@Override
	public void onBoardUpdate(Game game) {
	}

	@Override
	public void onTroopAction(Game game) {
	}

	@Override
	public void onTroopSelection(Game game) {
	}

	@Override
	public void onNextTurn(Game game) {
		_actionGame.clear();
	}

	@Override
	public void onTroopUnSelection(Game game) {
	}
}
