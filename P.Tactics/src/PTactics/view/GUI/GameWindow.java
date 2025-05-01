package PTactics.view.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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

	public GameWindow(ControllerInterface ctrl, JFrame frame) {
		this._ctrl = ctrl;
		this.frame = frame;
		
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				ImageIcon icon = new ImageIcon(Icons.otherIcons.HOLDFIRE_ICON5.getImage()
						.getScaledInstance(48, 48, Image.SCALE_SMOOTH));
				int choice = JOptionPane.showConfirmDialog(
					frame,
					"Do you want to return to the main menu without saving?",
					"Exit to Main Menu",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					icon
				);

				if (choice == JOptionPane.YES_OPTION) {
					// go to the mainWindow
					frame.getContentPane().removeAll();
					frame.revalidate();
					frame.repaint();
					new MainWindow((Controller) _ctrl);  
				}
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
		
		GameInfoPanel gameInfo = new GameInfoPanel(_ctrl, this);
		ControlPanel control = new ControlPanel(_ctrl);
		GameBoardPanel gameBoard = new GameBoardPanel(
				PTactics.utils.Position._gameLength, 
				PTactics.utils.Position._gameWidth, _ctrl, control);

		// board is smaller
		Dimension boardSize = new Dimension(Game._boardWidth * Controller.tileSize, Game._boardLength * Controller.tileSize);
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
