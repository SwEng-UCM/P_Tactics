package PTactics.view.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import PTactics.control.ControllerInterface;
import PTactics.utils.Position;

public class GameWindow {

	private JPanel _gameWindowFrame;
	private ControllerInterface _ctrl;
	private JFrame frame;
	private GameInfoPanel _gameInfo;
	private ControlPanel _control;
	private GameBoardPanel _gameBoard;

	public GameWindow(ControllerInterface ctrl, JFrame frame) {
		this._ctrl = ctrl;
		this.frame = frame;

		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
			}
		});

		initialize();
	}

	public JFrame GetGameWindow() {
		return this.frame;
	}

	private void initialize() {
		BackgroundPanel background = new BackgroundPanel(Icons.otherIcons.BG_BUILDING2.getImage());
		background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));

		_gameInfo = new GameInfoPanel(_ctrl, this);
		_control = new ControlPanel(_ctrl);
		_gameBoard = new GameBoardPanel(PTactics.utils.Position._gameLength, PTactics.utils.Position._gameWidth, _ctrl,
				_control);
		// board is smaller
		Dimension boardSize = new Dimension(Position._gameWidth * Position.tileSize,
				Position._gameLength * Position.tileSize);
		_gameBoard.setMaximumSize(boardSize);
		_gameBoard.setMinimumSize(boardSize);

		_gameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		_gameBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
		_control.setAlignmentX(Component.CENTER_ALIGNMENT);

		background.add(Box.createRigidArea(new Dimension(0, 10)));
		background.add(_gameInfo);
		background.add(Box.createRigidArea(new Dimension(0, 10)));
		background.add(_gameBoard);
		background.add(Box.createRigidArea(new Dimension(0, 10)));
		background.add(_control);

		frame.getContentPane().removeAll();
		frame.setContentPane(background);
		frame.revalidate();
		frame.repaint();

		_ctrl.update();
	}

	public void showWinMessage(String player) {
		JOptionPane.showMessageDialog(_gameWindowFrame, "Player " + player + " won the game.");
	}

	public void removeObservers() {
		_gameInfo.removeObserver();
		_control.removeObserver();
		_gameBoard.removeObserver();
	}
}
