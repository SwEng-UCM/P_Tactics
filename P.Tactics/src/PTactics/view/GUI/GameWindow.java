package PTactics.view.GUI;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.utils.Position;

public class GameWindow {

	private JPanel _gameWindowFrame;
	private ControllerInterface _cntrl;
	private JFrame frame;

	public GameWindow(Controller cntrl,JFrame frame) {
		this._cntrl=cntrl;
		this.frame=frame;
		initialize();
	}
	public JFrame GetGameWindow() {
		return this.frame;
	}
	
	private void initialize() {
		
		//_gameWindowFrame = new JPanel();
		GameInfoPanel gameInfo = new GameInfoPanel(_cntrl,this);
		gameInfo.setBounds(0, 0, 1227, 60);
		frame.getContentPane().add(gameInfo);

		ControlPanel control = new ControlPanel(_cntrl);
		control.setBounds(0, 759, 1227, 158);
		frame.getContentPane().add(control);

		GameBoardPanel gameBoard = new GameBoardPanel(
				PTactics.utils.Position._gameLength,
				PTactics.utils.Position._gameWidth,
				_cntrl,
				control
		);
		gameBoard.setBounds(250, 59, 700, 700);
		frame.getContentPane().add(gameBoard);
		_cntrl.update();

		frame.setSize(1243, 956);
		frame.setLocationRelativeTo(null); // center again
		frame.repaint();
	}
	public  void showWinMessage(int PlayerNumber) 
	{
		JOptionPane.showMessageDialog(_gameWindowFrame, "Player "+Integer.toString(PlayerNumber)+" won the game.");
	}
}
