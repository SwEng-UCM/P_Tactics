package PTactics.view.GUI;

import javax.swing.JPanel;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Game;
import PTactics.view.GameObserver;

import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class GameInfoPanel extends JPanel implements GameObserver{

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ControllerInterface _cntr;
	JLabel playerTurnText;
	public GameInfoPanel(ControllerInterface cntr,GameWindow gw) {
		this._cntr=cntr;
		this._cntr.addObserver(this);
		this.setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		playerTurnText = new JLabel(Icons.otherIcons.LABELBACKGROUND);

	//	playerTurnText.setBounds(23, 22, 148, 29);
		playerTurnText.setText("Player: "+ this._cntr.getNumPlayer()+" turn");
		playerTurnText.setFont(new Font("Times New Roman", Font.BOLD, 20));
		playerTurnText.setForeground(Color.getHSBColor(0, 0, 0));
		playerTurnText.setAlignmentX(CENTER_ALIGNMENT);
		playerTurnText.setHorizontalTextPosition(0);
		playerTurnText.setFocusable(false);  // disables focus (no caret or selection)
		add(playerTurnText);
		
		add(Box.createRigidArea(new Dimension(400, 0))); 
		
		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.setIcon(Icons.otherIcons.LABELBACKGROUND);
		endTurnButton.setContentAreaFilled(false);
		endTurnButton.setBorder(null);
		endTurnButton.setHorizontalTextPosition(0);
		endTurnButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		endTurnButton.setForeground(Color.orange);		
		endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_cntr.nextTurn();
				if(_cntr.isFinish()) 
				{
					gw.showWinMessage(_cntr.getNumPlayer()-1);
				}
			}
		});
	//	endTurnButton.setBounds(679, 6, 156, 56);
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
