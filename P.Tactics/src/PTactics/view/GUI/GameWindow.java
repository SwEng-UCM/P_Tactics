package PTactics.view.GUI;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.model.game.Game;

public class GameWindow {

	private JPanel _gameWindowFrame;
	private ControllerInterface _cntrl;
	private JFrame frame;

	public GameWindow(Controller cntrl, JFrame frame) {
		this._cntrl = cntrl;
		this.frame = frame;
		initialize();
	}

	public JFrame GetGameWindow() {
		return this.frame;
	}

	private void initialize() {

		_gameWindowFrame = new JPanel(new BoxLayout(_gameWindowFrame, BoxLayout.Y_AXIS));
		
		GameInfoPanel gameInfo = new GameInfoPanel(_cntrl, this);
		// gameInfo.setBounds(0, 0, 1227, 60);
		frame.add(gameInfo);
		
		frame.add(Box.createRigidArea(new Dimension(10, 0)));
		ControlPanel control = new ControlPanel(_cntrl);
		// control.setBounds(0, 759, 1227, 158);

		GameBoardPanel gameBoard = new GameBoardPanel(PTactics.utils.Position._gameLength,
				PTactics.utils.Position._gameWidth, _cntrl, control);

		gameBoard.setMaximumSize(new Dimension(Game._boardWidth * 69, Game._boardLength * 69));
		gameBoard.setMinimumSize(new Dimension(Game._boardWidth * 69, Game._boardLength * 69));
		// gameBoard.setBounds(250, 59, 700, 700);
		frame.add(gameBoard);
		frame.add(control);
		gameInfo.setAlignmentX((float) 0.5);
		gameBoard.setAlignmentX((float) 0.5);
		control.setAlignmentX((float) 0.5);
		_cntrl.update();

		// frame.add(_gameWindowFrame);
		// frame.setSize(1243, 956);
		frame.setLocationRelativeTo(null); // center again
		
	}

	public void showWinMessage(int PlayerNumber) {
		JOptionPane.showMessageDialog(_gameWindowFrame, "Player " + Integer.toString(PlayerNumber) + " won the game.");
	}
}
