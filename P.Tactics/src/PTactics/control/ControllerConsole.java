package PTactics.control;

import java.util.InputMismatchException;

import javax.swing.Icon;

import PTactics.control.commands.Command;
import PTactics.control.commands.CommandGenerator;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GameConsoleView;

public class ControllerConsole extends Controller {
	private GameConsoleView _gameView;

	public ControllerConsole() {
		super();
		createGame();
		initConsole();
		runConsole();
	}

	public void runConsole() {
		while (!this.isFinish()) { // revisar logica
			startOfTurn();
			while (!_endTurn) {
				String[] userCommand = _gameView.getPrompt();
				Command command = CommandGenerator.parse(userCommand);

				if (command != null) {
					command.execute(this);
					_game.onDeadTroopSelected();

				} else {
					_gameView.showError(Utils.MsgErrors.UNKNOWN_COMMAND);
				}
			}
			nextTurn();
		}
	}

	public void initConsole() {
		boolean correct = false;
		this._gameView = new GameConsoleView(this);
		_gameView.showMessage(Utils.MessageUtils.WELCOME_MSG);
		_gameView.showMessage(Utils.MessageUtils.ASK_NUMBER_PLAYERS);
		while (!correct) {
			try {
				_numPlayers = _gameView.getInt();
				if (_numPlayers < 2 || _numPlayers > 4)
					throw new Exception();
				correct = true;
			} catch (InputMismatchException inputError) {
				_gameView.showMessage(Utils.MsgErrors.INVALID_INPUT);
				_gameView.get(); // Clearing the buffer to avoid infinite loop!
				correct = false;
			} catch (Exception e) {
				_gameView.showMessage(Utils.MsgErrors.INVALID_NUM_PLAYERS);
				correct = false;
			}
		}
		setupPlayers();
	}

	private void startOfTurn() {
		_endTurn = false;
		_gameView.showStartOfTurn(this, _game);
	}

	public void showGame() {
		this._gameView.showGame(_game);
	}

	public String positionToString(Position pos) {
		return this._game.positionToString(pos);
	}

	@Override
	public String[] getPrompt() {
		return _gameView.getPrompt();
	}

	@Override
	public int getInt() {
		return _gameView.getInt();
	}

	@Override
	public void showMessage(String msg) {
		_gameView.showMessage(msg);
	}

	@Override
	public Icon getIcon(Position _pos) {
		return null;
	}

}
