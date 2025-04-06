package PTactics.control;

import javax.swing.Icon;

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
}
