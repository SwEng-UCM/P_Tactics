package PTactics.view.GUI;

import java.awt.Component;
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
	private ControllerInterface _ctrl;
	private JFrame frame;

	public GameWindow(Controller ctrl, JFrame frame) {
		this._ctrl = ctrl;
		this.frame = frame;
		initialize();
	}

	public JFrame GetGameWindow() {
		return this.frame;
	}

	private void initialize() {
		BackgroundPanel background = new BackgroundPanel(Icons.otherIcons.BG_BUILDING2.getImage());
		background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
		
		GameInfoPanel gameInfo = new GameInfoPanel(_ctrl, this);
		ControlPanel control = new ControlPanel(_ctrl);
		GameBoardPanel gameBoard = new GameBoardPanel(
				PTactics.utils.Position._gameLength,
				PTactics.utils.Position._gameWidth, _ctrl, control);

		// board is smaller
		Dimension boardSize = new Dimension(Game._boardWidth * 50, Game._boardLength * 50);
		gameBoard.setMaximumSize(boardSize);
		gameBoard.setMinimumSize(boardSize);
		
		gameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		gameBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
		control.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		background.add(Box.createRigidArea(new Dimension(0, 10)));
		background.add(gameInfo);
		background.add(Box.createRigidArea(new Dimension(0, 10)));
		background.add(gameBoard);
		background.add(Box.createRigidArea(new Dimension(0, 10)));
		background.add(control);
		
		frame.getContentPane().removeAll();
		frame.setContentPane(background);
		frame.revalidate();
		frame.repaint();

		_ctrl.update();	
	}

	public void showWinMessage(int PlayerNumber) {
		JOptionPane.showMessageDialog(_gameWindowFrame, "Player " + Integer.toString(PlayerNumber) + " won the game.");
	}
}
