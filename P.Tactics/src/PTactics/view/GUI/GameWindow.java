package PTactics.view.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import PTactics.control.Controller;
import PTactics.utils.Position;

public class GameWindow {

	private JFrame _gameWindowFrame;
	private Controller _cntrl;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public GameWindow(Controller cntrl) {
		this._cntrl=cntrl;
		initialize();
	}
	public JFrame GetGameWindow() 
	{
		return this._gameWindowFrame;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		_gameWindowFrame = new JFrame();
		_gameWindowFrame.setTitle("P.Tactics");
		_gameWindowFrame.setBounds(100, 100, 1243, 956);
		_gameWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_gameWindowFrame.getContentPane().setLayout(null);
		
		GameInfoPanel gameInfo= new GameInfoPanel();
		gameInfo.setBounds(0, 0, 1227, 60);
		_gameWindowFrame.getContentPane().add(gameInfo);
		
		ControlPanel control= new ControlPanel();
		control.setBounds(0, 759, 1227, 158);
		_gameWindowFrame.getContentPane().add(control);
		
		GameBoardPanel gameBoard= new GameBoardPanel(Position._gameLength,Position._gameWidth,this._cntrl, control);
		gameBoard.setBounds(250, 59, 700, 700);
		_gameWindowFrame.getContentPane().add(gameBoard);
	}

}
