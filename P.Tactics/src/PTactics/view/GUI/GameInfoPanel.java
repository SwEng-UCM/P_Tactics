package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.model.game.Board;
import PTactics.model.game.Game;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;

public class GameInfoPanel extends JPanel implements GameObserver {

	private static final long serialVersionUID = 1L;
	private TutorialWindow tw;
	public ControllerInterface _ctrl;
	private JLabel playerTurnText;
	private JPanel turnPanel;
	private JButton _exit;
	private JButton _undo;
	private JButton _redo;
	private JButton _endTurn;
	private JButton _instructions;
	private JButton _save;

	public GameInfoPanel(ControllerInterface ctrl, GameWindow gw) {
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
		tw = new TutorialWindow();
		this.setOpaque(false);
		this.setLayout(new BorderLayout());

		// player panel (top-left)
		String playerText = "<html> Player: [" + _ctrl.getCurrentPlayerName() + "] turn <br>";
		playerText += "</html>";

		playerTurnText = new JLabel(playerText);
		playerTurnText.setFont(new Font("Times New Roman", Font.BOLD, 18));
		playerTurnText.setForeground(Color.orange);
		playerTurnText.setFocusable(false);
		playerTurnText.setHorizontalAlignment(SwingConstants.CENTER);
		playerTurnText.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20)); // padding

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
		this.add(turnPanel, BorderLayout.WEST);

//		 button Panel (top-right)
		JPanel gameInfoButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		gameInfoButtons.setOpaque(false);

		// exit button
		_exit = createButton("Exit");
		_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(
						null,
						"Are you sure you want to exit without saving the game?",
						"Exit Game",
						JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_NO_OPTION) {
					gw.GetGameWindow().setVisible(false);
					gw.GetGameWindow().dispose();
					new MainWindow((Controller) _ctrl);
				}
			}
		});
		gameInfoButtons.add(_exit);
		
		// undo button
		_undo = createButton("Undo");
		_undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Hardcoding undo instruction
				String[] cmdArgs = { Utils.CommandInfo.COMMAND_UNDO_NAME };
				/*Command command = CommandGenerator.parse(cmdArgs);
				command.execute(_ctrl);*/
				_ctrl.executeCommand(cmdArgs);
			}
		});
		gameInfoButtons.add(_undo);
		
		// redo button
		_redo = createButton("Redo");
		_redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Hardcoding undo instruction
				String[] cmdArgs = { Utils.CommandInfo.COMMAND_REDO_NAME };
				/*Command command = CommandGenerator.parse(cmdArgs);
				command.execute(_ctrl);*/
				_ctrl.executeCommand(cmdArgs);
			}
		});
		gameInfoButtons.add(_redo);

		// End Turn
		_endTurn = createButton("End Turn");
		_endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_ctrl.nextTurn();
				if (_ctrl.isFinish()) {
					gw.showWinMessage(_ctrl.getCurrentPlayerName() );
					gw.GetGameWindow().getContentPane().removeAll();
					gw.GetGameWindow().revalidate();
					gw.GetGameWindow().repaint();
					new MainWindow((Controller) _ctrl); 
				}
			}
		});
		gameInfoButtons.add(_endTurn);

		// tutorial button
		_instructions = createButton("Instructions");
		tw = new TutorialWindow();
		_instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tw.setVisible(true);
				System.out.println("IM ENABLED");
			}
		});
		gameInfoButtons.add(_instructions);

		// Save button
		if (!_ctrl.isOnline()) {
			_save = createButton("Save");
			_save.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					// Ensures the user can only select directories
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					// Sets the current directory to the directory where the program is running
					fileChooser.setCurrentDirectory(new java.io.File("."));
					fileChooser.setDialogTitle("Select folder");
					if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						String filePath = fileChooser.getSelectedFile().getAbsolutePath();
						// Hardcoding save instruction
						String[] cmdArgs = { Utils.CommandInfo.COMMAND_SAVE_NAME, filePath };
						/*Command command = CommandGenerator.parse(cmdArgs);
						command.execute(_ctrl);*/
						_ctrl.executeCommand(cmdArgs);
					} else {
						JOptionPane.showMessageDialog(null, "Invalid file type. Please select a valid directory", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			});
			gameInfoButtons.add(_save);
		}

		this.add(gameInfoButtons, BorderLayout.EAST);
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
		
		// clicking effect on buttons
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				button.setIcon(Icons.otherIcons.LABELBACKGROUND_DARK);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				button.setIcon(Icons.otherIcons.LABELBACKGROUND);
			}
		});;
		
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
		String playerText = "<html> Player: [" + _ctrl.getCurrentPlayerName() + "] turn <br>";
		if (_ctrl.getCurrentPlayerWinZone() < Board.getInstance().pointsToWin() && _ctrl.getCurrentPlayerWinZone() != 0) {
			playerText += "Points to win: " + _ctrl.getCurrentPlayerWinZone();
		}
		else if (_ctrl.getCurrentPlayerWinZone() <= 0) {
			playerText += "WIN";
		}
		playerText += "</html>";
		
		playerTurnText.setText(playerText);
		
		if (_ctrl.cpuIsPlaying() || !_ctrl.isMyTurn()) {
			disableAll();
		}
		
		else {
			SwingUtilities.invokeLater(() -> enableAll());
		}
	}

	private void disableAll() {
		_exit.setEnabled(false);
		_undo.setEnabled(false);
		_redo.setEnabled(false);
		_endTurn.setEnabled(false);
		_instructions.setEnabled(false);
		if (!_ctrl.isOnline()) {
			_save.setEnabled(false);			
		}
	}
	
	private void enableAll() {
		_exit.setEnabled(true);
		_undo.setEnabled(true);
		_redo.setEnabled(true);
		_endTurn.setEnabled(true);
		_instructions.setEnabled(true);
		if (!_ctrl.isOnline()) {
			_save.setEnabled(true);			
		}
	}

	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub
	}
}
