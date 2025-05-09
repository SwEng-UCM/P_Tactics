package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Board;
import PTactics.multiplayer.ClientController;
import PTactics.multiplayer.HostController;
import PTactics.utils.Utils;
import PTactics.view.GUI.Icons.otherIcons;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private ControllerInterface _ctrl;
	private JButton[] _animatedButtons;
	private String[] _buttonTexts = { "START", "CONTINUE", "ONLINE", "PLAY VS CPU", "EXIT" };
	private JPanel _buttonPanel;
	private ImageIcon _icon;

	public MainWindow(ControllerInterface ctrl) {
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		_icon = new ImageIcon(Icons.otherIcons.HOLDFIRE_ICON5.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH));

		javax.swing.UIManager.put("OptionPane.informationIcon", _icon);
		javax.swing.UIManager.put("OptionPane.questionIcon", _icon);
		javax.swing.UIManager.put("OptionPane.warningIcon", _icon);
		javax.swing.UIManager.put("OptionPane.errorIcon", _icon);

		setTitle("HOLDFIRE");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); // full screen
		setLocationRelativeTo(null); // center window

		// panel with background image
		BackgroundPanel backgroundPanel = new BackgroundPanel(otherIcons.BG_BUILDING2.getImage());
		backgroundPanel.setLayout(new GridBagLayout());
		setContentPane(backgroundPanel);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);

		ShadowLabel title = new ShadowLabel("HOLDFIRE", new Font("Algerian", Font.BOLD, 100), Color.orange, Color.BLACK,
				4);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(title);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		// buttonPanel
		_buttonPanel = new JPanel();
		_buttonPanel.setLayout(null);
		_buttonPanel.setPreferredSize(new Dimension(400, 400));
		_buttonPanel.setOpaque(false);
		centerPanel.add(_buttonPanel);

		backgroundPanel.add(centerPanel, gbc);

		// creation of buttons
		_animatedButtons = new JButton[_buttonTexts.length];
		for (int i = 0; i < _buttonTexts.length; i++) {
			JButton btn = new JButton(_buttonTexts[i]);
			btn.setFont(new Font("Times New Roman", Font.BOLD, 18));
			btn.setIcon(Icons.otherIcons.LABELBACKGROUND);
			btn.setContentAreaFilled(false);
			btn.setBorder(null);
			btn.setHorizontalTextPosition(0);
			btn.setForeground(Color.orange);
			btn.setBounds(-300, i * 60, 200, 50);
			_animatedButtons[i] = btn;
			_buttonPanel.add(btn);
		}

		assignButtonActions();
		animatedSlide();
		setVisible(true);
	}

	private void assignButtonActions() {
		// startButton
		_animatedButtons[0].addActionListener(e -> {
			JSpinner spinner = new JSpinner(new SpinnerNumberModel(2, 1, 4, 1));
			int result = JOptionPane.showConfirmDialog(this, spinner, "Select number of players",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result != JOptionPane.OK_OPTION)
				return; // closes dialog

			int numPlayers = (Integer) spinner.getValue();
			_ctrl.setPlayerNum(numPlayers);

			String[] names = new String[numPlayers];
			for (int i = 0; i < numPlayers; i++) {
				String name;
				do {
					name = (String) JOptionPane.showInputDialog(this, "Enter name for Player " + (i + 1) + ":",
							"Name of the players", JOptionPane.PLAIN_MESSAGE, _icon, null, "");
					if (name == null)
						return; // closes dialog if cancel is pressed
				} while (name.trim().isEmpty());
				names[i] = name.trim();
			}

			if (numPlayers == 2) {
				String[] startOptions = { "Map 1", "Map 2" };

				int answer = JOptionPane.showOptionDialog(null, "Map Selection", "Select a map",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, _icon, startOptions, null);
				if (answer == 0) {
					_ctrl.setMap(0);
				} else if (answer == 1) {
					_ctrl.setMap(1); // this is kinda weird but since we are not expanding it its fine
				}
			}

			else {
				_ctrl.setMap(0);
			}

			Board.getInstance().eraseAll();
			_ctrl.createGame();
			_ctrl.setPlayerNames(Arrays.asList(names));
			_ctrl.setupPlayers();
			swapToGameWindow();
		});

		// continue button
		_animatedButtons[1].addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			// Ensures the user can only select directories
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// Sets the current directory to the directory where the program is running
			fileChooser.setCurrentDirectory(new java.io.File("."));
			fileChooser.setDialogTitle("Load a saved game (.json)");
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				if (!filePath.toLowerCase().endsWith(".json")) {
					JOptionPane.showMessageDialog(this, "Invalid file type. Please select a .json file.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(this, "Selected game: \n" + filePath);

				// Hardcoding load instruction
				String[] cmdArgs = { Utils.CommandInfo.COMMAND_LOAD_NAME, filePath };
				_ctrl.executeCommand(cmdArgs);

				swapToGameWindow();
			}
		});

		// online button
		_animatedButtons[2].addActionListener(e -> {
			Object[] options = { "Host", "Client" };
			int choice = JOptionPane.showOptionDialog(null, "Do you want to host or join a game?", "Choose Role",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (choice == 0) { // Host selected
				String name = JOptionPane.showInputDialog(null, "Enter nametag", "", JOptionPane.QUESTION_MESSAGE);
				if (name == null)
					return;
				Boolean loop = true;
				int port = 0;
				while (loop) {
					loop = false;
					String portStr = (String) JOptionPane.showInputDialog(null,
							"Enter port to host on (default: 5000):", "Port", JOptionPane.QUESTION_MESSAGE, null, null,
							"5000");
					if (portStr == null)
						return; // Cancelled
					try {
						port = Integer.parseInt(portStr);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid port number.", "Error", JOptionPane.ERROR_MESSAGE);
						loop = true;
					}
				}
				final int portFinal = port;// the thread requires its variable to be final or effectively final so I
											// have to do this.
				// Ask for number of players (with spinner)
				SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 2, 8, 1); // default 2, min 2, max 8
				JSpinner spinner = new JSpinner(spinnerModel);
				int result = JOptionPane.showConfirmDialog(null, spinner, "Enter number of players (including you):",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.OK_OPTION) {
					int numPlayers = (Integer) spinner.getValue();

					if (numPlayers == 2) {
						String[] startOptions = { "Map 1", "Map 2" };

						int answer = JOptionPane.showOptionDialog(null, "Map Selection", "Select a map",
								JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, _icon, startOptions, null);

						if (answer == 0) {
							_ctrl.setMap(0);
						} else if (answer == 1) {
							_ctrl.setMap(1); // this is kinda weird but since we are not expanding it its fine
						}
					}

					else {
						_ctrl.setMap(0);
					}

					System.out.println("Hosting game on port " + port + " for " + numPlayers + " players.");

					JDialog waitingDialog = new JDialog(this, "Waiting for players...", false);
					JLabel statusLabel = new JLabel("Waiting for 0 of " + numPlayers + " players...");
					waitingDialog.setLayout(new BorderLayout());
					waitingDialog.add(statusLabel, BorderLayout.CENTER);
					waitingDialog.setSize(300, 100);
					waitingDialog.setLocationRelativeTo(this);
					waitingDialog.setVisible(true);

					// weird callback function for connected status
					new Thread(() -> {
						this._ctrl = new HostController(portFinal, numPlayers, name, (connectedCount) -> {
							SwingUtilities.invokeLater(() -> {
								statusLabel
										.setText("Waiting for " + connectedCount + " of " + numPlayers + " players...");
								if (connectedCount >= numPlayers) {
									waitingDialog.dispose(); // All players connected
									_ctrl.createGame();
									swapToGameWindow();
								}
							});
						});
						_ctrl.logPlayers();
					}).start();

				}

			} else if (choice == 1) { // Client selected
				// Ask for host IP address
				String playerName = JOptionPane.showInputDialog(null, "Enter your name / player ID:", "Player Name",
						JOptionPane.QUESTION_MESSAGE);
				if (playerName == null)
					return;
				while (playerName.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid name.", "Error", JOptionPane.ERROR_MESSAGE);
					playerName = JOptionPane.showInputDialog(null, "Enter your name / player ID:", "Player Name",
							JOptionPane.QUESTION_MESSAGE);
				}

				String hostIP = JOptionPane.showInputDialog(null, "Enter host IP address:", "Connect to Host",
						JOptionPane.QUESTION_MESSAGE);
				if (hostIP == null)
					return;
				while (hostIP.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid host IP", "Error", JOptionPane.ERROR_MESSAGE);
					hostIP = JOptionPane.showInputDialog(null, "Enter host IP address:", "Connect to Host",
							JOptionPane.QUESTION_MESSAGE);
				}

				String portStr = "";
				int port = 0;
				Boolean loop = true;
				while (loop) {
					portStr = (String) JOptionPane.showInputDialog(null, "Enter port to host on (default: 5000):",
							"Port", JOptionPane.QUESTION_MESSAGE, null, null, "5000");
					if (portStr == null)
						return;
					loop = false;
					try {
						port = Integer.parseInt(portStr);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid port number.", "Error", JOptionPane.ERROR_MESSAGE);
						loop = true;
					}
				}
				final String hostIPFinal = hostIP;
				final String playerNameFinal = playerName;
				final int portFinal = port;
				// workaround for thread finality issue. If you wanna understand delete these
				// lines and set it to normal vars.
				System.out.println("Connecting as " + playerName + " to " + hostIP + ":" + port);

				// Start client connection
				new Thread(() -> {
					this._ctrl = new ClientController(hostIPFinal, portFinal, playerNameFinal, (connected) -> {

						if (connected)
							swapToGameWindow();

					});
					_ctrl.logPlayers();
				}).start();
			}

		});

		// play VS CPU button
		_animatedButtons[3].addActionListener(e -> {
			String playerName = null;

			do {
				playerName = (String) JOptionPane.showInputDialog(this, "Enter name of the player: ",
						"Name of the player", JOptionPane.PLAIN_MESSAGE, _icon, null, "");
				if (playerName == null)
					return; // cancel if there is no name written
			} while (playerName.trim().isEmpty());

			JOptionPane.showMessageDialog(this, "Player [" + playerName + "] VS CPU");

			String[] difficulties = { "Easy", "Medium", "Hard" };
			int levelDifficulty = JOptionPane.showOptionDialog(this, "Choose CPU difficulty: ", "CPU Difficulty",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficulties, difficulties[0]);

			String[] startOptions = { "Map 1", "Map 2" };

			int answer = JOptionPane.showOptionDialog(null, "Map Selection", "Select a map", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, _icon, startOptions, null);

			if (answer == 0) {
				_ctrl.setMap(0);
			} else if (answer == 1) {
				_ctrl.setMap(1); // this is kinda weird but since we are not expanding it its fine
			}

			if (levelDifficulty != -1) {
				List<String> playerNames = new ArrayList<>();
				playerNames.add(playerName);
				playerNames.add("CPU");
				Board.getInstance().eraseAll();
				_ctrl.createGame();
				_ctrl.setPlayerNames(playerNames);
				_ctrl.setPlayerNum(2); // realPlayer + CPU
				_ctrl.setUpPlayerVsCPU(levelDifficulty);
				swapToGameWindow();
			}
		});

		// exit button
		_animatedButtons[4].addActionListener(e -> System.exit(0));

		// mouse click
		for (JButton button : _animatedButtons) {
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					button.setIcon(Icons.otherIcons.LABELBACKGROUND_DARK);
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					button.setIcon(Icons.otherIcons.LABELBACKGROUND);
				}
			});
		}
	}

	private void animatedSlide() {
		Timer slideIn = new Timer(5, null);
		slideIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean finished = true;
				for (int i = 0; i < _animatedButtons.length; i++) {
					Point p = _animatedButtons[i].getLocation();
					int targetX = 100;
					if (p.x < targetX) {
						_animatedButtons[i].setLocation(p.x + 20, p.y);
						finished = false;
					}
				}
				if (finished)
					((Timer) e.getSource()).stop();
			}
		});
		slideIn.start();
	}

	private void swapToGameWindow() {
		SwingUtilities.invokeLater(() -> {
			getContentPane().removeAll();
			getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			new GameWindow(_ctrl, this);
			revalidate();
			repaint();
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		});
	}

	private static class ShadowLabel extends JLabel {
		private final Font _font;
		private final Color _textColor;
		private final Color _shadowColor;
		private final int _shadowOffset;

		public ShadowLabel(String txt, Font font, Color txtColor, Color shadowColor, int shadowOffset) {
			super(txt);
			_font = font;
			_textColor = txtColor;
			_shadowColor = shadowColor;
			_shadowOffset = shadowOffset;
			setFont(font);
			setOpaque(false);
			setForeground(txtColor);
			setHorizontalAlignment(CENTER);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setFont(_font);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			FontMetrics fm = g2.getFontMetrics();
			int x = (getWidth() - fm.stringWidth(getText())) / 2;
			int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

			// shadow
			g2.setColor(_shadowColor);
			g2.drawString(getText(), x + _shadowOffset, y + _shadowOffset);

			// principal text
			g2.setColor(_textColor);
			g2.drawString(getText(), x, y);

			g2.dispose();
		}
	}

}
