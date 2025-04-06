package PTactics.control;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import org.json.JSONObject;

import PTactics.model.game.Game;
import PTactics.view.GameObserver;

public class Tracker implements GameObserver {
	private Stack<JSONObject> actionsGame;
	private Stack<JSONObject> undoGame;
	private static Tracker _record;
	
	private Tracker (ControllerInterface _cntr) {
		actionsGame = new Stack<>();
		undoGame = new Stack<>();
		_cntr.addObserver(this);
	}
	
	public static Tracker getInstance(ControllerInterface CI) {
		if(_record == null) {
			_record = new Tracker(CI);
		}
		
		return _record;
	}
	
	//For testing
	public void foo(Game game) {
		undoGame = new Stack<>();
		actionsGame.push(game.report());
	}
	
	//TODO: Needs to be given a file direction
	public void save() {
		JSONObject gameState = actionsGame.pop();
		try {
            FileWriter myWriter = new FileWriter("examples/JSONtest.json");
            myWriter.write(gameState.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
	}
	
	public void undo() {
		JSONObject gameState = actionsGame.pop();
		undoGame.push(gameState);
		//load(gameState);
	}
	
	public void redo() {
		JSONObject gameState = undoGame.pop();
		actionsGame.push(gameState);
		//load(gameState);
	}
	
	@Override
	public void onPlayersUpdate(Game game) {}

	@Override
	public void onBoardUpdate(Game game) {}

	@Override
	public void onTroopAction(Game game) {
		undoGame = new Stack<>();
		actionsGame.push(game.report());
	}

	@Override
	public void onTroopSelection(Game game) {}

	@Override
	public void onNextTurn(Game game) {
		undoGame = new Stack<>();
		actionsGame.push(game.report());
	}

	@Override
	public void onTroopUnSelection(Game game) {}
	
}
