package PTactics.control;

import java.util.ArrayList;

import javax.swing.Icon;

import org.json.JSONArray;
import org.json.JSONObject;

import PTactics.utils.Position;
import PTactics.view.GUI.MainWindow;

public class ControllerGUI extends Controller {

	public ControllerGUI() {
		initMainWindow();
	}

	public void runGUI() {
		update();
	}

	private void initMainWindow() {
		try {
			new MainWindow(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Icon getIcon(Position _pos) {
		return _game.positionToIcon(_pos);
	}

	@Override
	public void showGame() {}

	@Override
	public String[] getPrompt() {
		return null;
	}

	@Override
	public void showMessage(String msg) {}

	@Override
	public int getInt() {
		return 0;
	}

	@Override
	public String positionToString(Position _pos) {
		return null;
	}
	
	@Override 
	protected void _loadController(JSONObject gameState) {
		super._loadController(gameState);
		_playerNames = new ArrayList<>();
		 
		if(gameState.has("PlayerNames")) {
			for (int i1 = 0; i1 < gameState.getJSONArray("PlayerNames").length(); i1++) {
				_playerNames.add( gameState.getJSONArray("PlayerNames").getString(i1));
			}
		}
		else {
			for (int i1 = 0; i1 < this._numPlayers; i1++) {
				_playerNames.add("Player " + String.valueOf(i1));
			}
		}
		
	}
	
	@Override
	public JSONObject report() {
		JSONObject jo = super.report();
		JSONArray names = new JSONArray();
		
		for(String name : _playerNames) {
			names.put(name);
		}
		jo.put("Names", names);
		
		return jo;
	}
}
