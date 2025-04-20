package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import PTactics.control.ControllerInterface;
import PTactics.control.commands.Command;
import PTactics.control.commands.CommandGenerator;
import PTactics.model.game.Game;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;

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
//		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setLayout(new BorderLayout());

		// player panel (top-left)
		playerTurnText = new JLabel("Player: [" + _ctrl.getCurrentPlayerName() + "] turn");
		playerTurnText.setFont(new Font("Times New Roman", Font.BOLD, 18));
		playerTurnText.setForeground(Color.orange);
		playerTurnText.setFocusable(false);
		playerTurnText.setHorizontalAlignment(SwingConstants.CENTER);
		playerTurnText.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));	// padding
//		playerTurnText.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
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
		
		int standardButtonHeight = 60;
		turnPanel.setPreferredSize(new Dimension(playerTurnText.getPreferredSize().width + 40, standardButtonHeight));
//		turnPanel.setMaximumSize(turnPanel.getPreferredSize());
		this.add(turnPanel, BorderLayout.WEST);
		
//		add(Box.createRigidArea(new Dimension(400, 0))); 
		
		// button Panel (top-right)
		JPanel gameInfoButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		gameInfoButtons.setOpaque(false);
		
		//Save button
		JButton save = createButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				// Ensures the user can only select directories
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				// Sets the current directory to the directory where the program is running
				fileChooser.setCurrentDirectory(new java.io.File(".")); 
				fileChooser.setDialogTitle("Select folder");
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String filePath = fileChooser.getSelectedFile().getAbsolutePath();
					//Hardcoding save instruction
					String cmdLine = "save " + filePath;
					String[] cmdArgs = cmdLine.trim().split("\\s+");
					Command command = CommandGenerator.parse(cmdArgs);
					command.execute(_ctrl);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid file type. Please select a valid directory", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		gameInfoButtons.add(save);
		
		
		/*
		JButton save = new JButton("Save");
		save.setIcon(Icons.otherIcons.LABELBACKGROUND);
		save.setContentAreaFilled(false);
		save.setBorder(null);
		save.setHorizontalTextPosition(0);
		save.setFont(new Font("Times New Roman", Font.BOLD, 18));
		save.setForeground(Color.orange);		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				// Ensures the user can only select directories
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				// Sets the current directory to the directory where the program is running
				fileChooser.setCurrentDirectory(new java.io.File(".")); 
				fileChooser.setDialogTitle("Select folder");
				
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String filePath = fileChooser.getSelectedFile().getAbsolutePath();
					//Hardcoding save instruction
					String[] cmdArgs = {Utils.CommandInfo.COMMAND_SAVE_NAME, filePath};
					Command command = CommandGenerator.parse(cmdArgs);
					command.execute(_ctrl);
				}
				else {
					JOptionPane.showMessageDialog(null, "Invalid file type. Please select a valid directory", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		add(save);*/
		
		//End Turn
		JButton endTurn = createButton("End Turn");
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.nextTurn();
				if(_ctrl.isFinish()) {
					gw.showWinMessage(_ctrl.getNumPlayer() - 1);
				}
			}
		});
		gameInfoButtons.add(endTurn);
		
		/*
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
		add(endTurnButton);*/
		
		// tutorial button
		JButton tutorial = createButton("Tutorial");
		tw = new TutorialWindow();
		tutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tw.setVisible(true);
			}
		});
		gameInfoButtons.add(tutorial);
		
		/*
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
		add(tutorial);*/
		
		// undo button
		JButton undo = createButton("Undo");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Hardcoding undo instruction
				String[] cmdArgs = {Utils.CommandInfo.COMMAND_UNDO_NAME};
				Command command = CommandGenerator.parse(cmdArgs);
				command.execute(_ctrl);
			}
		});
		gameInfoButtons.add(undo);
		
		// redo button
		JButton redo = createButton("Redo");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Hardcoding undo instruction
				String[] cmdArgs = {Utils.CommandInfo.COMMAND_REDO_NAME};
				Command command = CommandGenerator.parse(cmdArgs);
				command.execute(_ctrl);
			}
		});
		gameInfoButtons.add(redo);
		
		this.add(gameInfoButtons, BorderLayout.EAST);
		
		/*
		JButton undo = new JButton("Undo");
		undo.setIcon(Icons.otherIcons.LABELBACKGROUND);
		undo.setContentAreaFilled(false);
		undo.setBorder(null);
		undo.setHorizontalTextPosition(0);
		undo.setFont(new Font("Times New Roman", Font.BOLD, 18));
		undo.setForeground(Color.orange);
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Hardcoding undo instruction
				String[] cmdArgs = {Utils.CommandInfo.COMMAND_UNDO_NAME};
				Command command = CommandGenerator.parse(cmdArgs);
				command.execute(_ctrl);
			}
		});
		add(undo);*/
		
		// Redo button
		/*
		JButton redo = new JButton("Redo");
		redo.setIcon(Icons.otherIcons.LABELBACKGROUND);
		redo.setContentAreaFilled(false);
		redo.setBorder(null);
		redo.setHorizontalTextPosition(0);
		redo.setFont(new Font("Times New Roman", Font.BOLD, 18));
		redo.setForeground(Color.orange);
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Hardcoding redo instruction
				String[] cmdArgs = {Utils.CommandInfo.COMMAND_REDO_NAME};
				Command command = CommandGenerator.parse(cmdArgs);
				command.execute(_ctrl);
			}
		});
		add(redo);*/
		
//		add(gameInfoButtons, BorderLayout.EAST);
	}
	// generic method for creation of buttons
	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Times New Roman", Font.BOLD, 18));
		button.setForeground(Color.orange);
		button.setIcon(Icons.otherIcons.LABELBACKGROUND);
		button.setContentAreaFilled(false);
		button.setBorder(null);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		
		return button;
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
//		int standardButtonHeight = 150;
//		turnPanel.setPreferredSize(new Dimension(playerTurnText.getPreferredSize().width + 40, standardButtonHeight));
//		turnPanel.setMaximumSize(turnPanel.getPreferredSize());
		// just to check if Swing does not update the playerName
		/* 	turnPanel.revalidate();
			repaint(); */

	}
	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub
		
	}
}
