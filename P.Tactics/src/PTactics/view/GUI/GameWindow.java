package PTactics.view.GUI;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.utils.Position;

public class GameWindow {

	private JFrame _gameWindowFrame;
	private ControllerInterface _cntrl;

	public GameWindow(Controller cntrl) {
		this._cntrl=cntrl;
		initialize();
	}
	public JFrame GetGameWindow() {
		return this._gameWindowFrame;
	}
	
	private void initialize() {
		
		_gameWindowFrame = new JFrame();
		_gameWindowFrame.setTitle("P.Tactics");
		_gameWindowFrame.setBounds(100, 100, 1243, 956);
		_gameWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_gameWindowFrame.getContentPane().setLayout(null);
		_gameWindowFrame.setBackground(Color.orange);
		
		GameInfoPanel gameInfo= new GameInfoPanel(this._cntrl);
		gameInfo.setBounds(0, 0, 1227, 60);
		_gameWindowFrame.getContentPane().add(gameInfo);
		
		ControlPanel control= new ControlPanel(this._cntrl);
		control.setBounds(0, 759, 1227, 158);
		_gameWindowFrame.getContentPane().add(control);
		
		GameBoardPanel gameBoard= new GameBoardPanel(Position._gameLength,Position._gameWidth,this._cntrl, control);
		gameBoard.setBounds(250, 59, 700, 700);
		_gameWindowFrame.getContentPane().add(gameBoard);
		
		_gameWindowFrame.setVisible(true);
	}

}
