package P.Tactics.undo;

import java.util.Stack;

import org.json.JSONObject;

import PTactics.model.game.Game;
import PTactics.view.GameObserver;

public class Record implements GameObserver {
	private Stack<JSONObject> actionsGame;
	private Stack<JSONObject> undoGame;

	@Override
	public void onPlayersUpdate(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBoardUpdate(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTroopAction(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTroopSelection(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTurn(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub
		
	}
	
}
