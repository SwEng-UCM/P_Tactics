package PTactics.view.GUI;

import javax.swing.JPanel;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Game;
import PTactics.view.GameObserver;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameInfoPanel extends JPanel implements GameObserver{

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ControllerInterface _cntr;
	JTextArea playerTurnText;
	public GameInfoPanel(ControllerInterface cntr) {
		this._cntr=cntr;
		this._cntr.addObserver(this);
		setLayout(null);
		
		playerTurnText = new JTextArea();
		playerTurnText.setEditable(false);
		playerTurnText.setBounds(23, 22, 148, 29);
		playerTurnText.setText("Player: "+ this._cntr.getNumPlayer()+" turn");
		playerTurnText.setHighlighter(null); // disables text highlighting
		playerTurnText.setFocusable(false);  // disables focus (no caret or selection)
		add(playerTurnText);
		
		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_cntr.nextTurn();
				if(_cntr.isFinish()) 
				{
					
				}
			}
		});
		endTurnButton.setBounds(679, 6, 156, 56);
		add(endTurnButton);
		
	}
	@Override
	public void onPlayersUpdate(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBoardUpdate(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTroopAction(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTroopSelection(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNextTurn(Game game) {
		// TODO Auto-generated method stub
		playerTurnText.setText("Player: "+ this._cntr.getNumPlayer()+" turn");
	}
	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub
		
	}
}
