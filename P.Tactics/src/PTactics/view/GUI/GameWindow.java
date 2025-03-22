package PTactics.view.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import PTactics.model.game.Game;

public class GameWindow {

	private JFrame _gameWindowFrame;
	private Game _game;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public GameWindow(Game game) {
		this._game=game;
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
		
		GameBoardPanel gameBoard= new GameBoardPanel(_game.getBoardLength(),_game.getBoardWidth());
		gameBoard.setBounds(0, 65, 1227, 686);
		_gameWindowFrame.getContentPane().add(gameBoard);
		
		ControlPanel control= new ControlPanel();
		control.setBounds(0, 750, 1227, 167);
		_gameWindowFrame.getContentPane().add(control);
	}

}
