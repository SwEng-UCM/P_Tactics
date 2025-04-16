package PTactics.view.GUI;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Game;
import PTactics.view.GameObserver;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

public class GameInfoPanel extends JPanel implements GameObserver{

	private static final long serialVersionUID = 1L;
	private TutorialWindow tw;
	public ControllerInterface _ctrl;
	private JLabel playerTurnText;
	private JPanel turnPanel; 
	
	public GameInfoPanel(ControllerInterface ctrl, GameWindow gw) {
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
		tw = new TutorialWindow();
		this.setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		playerTurnText = new JLabel("Player: [" + _ctrl.getCurrentPlayerName() + "] turn");
		playerTurnText.setFont(new Font("Times New Roman", Font.BOLD, 18));
		playerTurnText.setForeground(Color.orange);
		playerTurnText.setFocusable(false);
		playerTurnText.setHorizontalAlignment(SwingConstants.CENTER);
		playerTurnText.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));	// padding
		
		// panel to hold the label with background
		turnPanel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Icons.otherIcons.LABELBACKGROUND.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		
		turnPanel.setOpaque(false);
		turnPanel.add(playerTurnText, BorderLayout.CENTER);
		
		int standardButtonHeight = 150;
		turnPanel.setPreferredSize(new Dimension(playerTurnText.getPreferredSize().width + 40, standardButtonHeight));
		turnPanel.setMaximumSize(turnPanel.getPreferredSize());
		add(turnPanel);
		
		add(Box.createRigidArea(new Dimension(400, 0))); 
		
		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.setIcon(Icons.otherIcons.LABELBACKGROUND);
		endTurnButton.setContentAreaFilled(false);
		endTurnButton.setBorder(null);
		endTurnButton.setHorizontalTextPosition(0);
		endTurnButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		endTurnButton.setForeground(Color.orange);		
		endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.nextTurn();
				if(_ctrl.isFinish()) 
				{
					gw.showWinMessage(_ctrl.getNumPlayer() - 1);
				}
			}
		});
		add(endTurnButton);
		
		// tutorial button
		JButton tutorial = new JButton("Tutorial");
		tutorial.setIcon(Icons.otherIcons.LABELBACKGROUND);
		tutorial.setContentAreaFilled(false);
		tutorial.setBorder(null);
		tutorial.setHorizontalTextPosition(0);
		tutorial.setFont(new Font("Times New Roman", Font.BOLD, 18));
		tutorial.setForeground(Color.orange);		
		tutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tw.setVisible(true);
			}
		});
		add(tutorial);
		
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
		playerTurnText.setText("Player: ["+ _ctrl.getCurrentPlayerName()+"] turn");
		int standardButtonHeight = 150;
		turnPanel.setPreferredSize(new Dimension(playerTurnText.getPreferredSize().width + 40, standardButtonHeight));
		turnPanel.setMaximumSize(turnPanel.getPreferredSize());
		turnPanel.revalidate();
		repaint();
	}
	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub
		
	}
}
