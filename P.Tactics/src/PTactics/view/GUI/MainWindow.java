package PTactics.view.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import PTactics.control.Controller;
import PTactics.view.GUI.Icons.otherIcons;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		setTitle("HOLDFIRE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);		// full screen
		setLocationRelativeTo(null); // center window
		setBackground(Color.black);

		// panel with background image
		BackgroundPanel backgroundPanel = new BackgroundPanel(otherIcons.BG_BUILDING2.getImage());
		backgroundPanel.setLayout(new GridBagLayout());
		setContentPane(backgroundPanel);
		
		// central panel that contains title and button
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false); // transparent background to see the image
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		// centered title
		ShadowLabel title = new ShadowLabel("HOLDFIRE", new Font("Algerian", Font.BOLD, 100), Color.WHITE, Color.BLACK, 4);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(title);

		// space between title and buttons
		centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
		
		// startGame button
		JButton startGame = new JButton("START");
		startGame.setFont(new Font("Times New Roman", Font.BOLD, 18));
		startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		startGame.setIcon(Icons.otherIcons.LABELBACKGROUND);
		startGame.setContentAreaFilled(false);
		startGame.setBorder(null);
		startGame.setHorizontalTextPosition(0);
		startGame.setForeground(Color.orange);
		centerPanel.add(startGame);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 2)));		// space between buttons
		
		// continueGame button
		JButton continueGame = new JButton("CONTINUE");
		continueGame.setFont(new Font("Times New Roman", Font.BOLD, 18));
		continueGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueGame.setIcon(Icons.otherIcons.LABELBACKGROUND);
		continueGame.setContentAreaFilled(false);
		continueGame.setBorder(null);
		continueGame.setHorizontalTextPosition(0);
		continueGame.setForeground(Color.orange);
		centerPanel.add(continueGame);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 2)));		// space between buttons
		
		// multiplayer button
		JButton multiPlayer = new JButton("ONLINE");
		multiPlayer.setFont(new Font("Times New Roman", Font.BOLD, 18));
		multiPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		multiPlayer.setIcon(Icons.otherIcons.LABELBACKGROUND);
		multiPlayer.setContentAreaFilled(false);
		multiPlayer.setBorder(null);
		multiPlayer.setHorizontalTextPosition(0);
		multiPlayer.setForeground(Color.orange);
		centerPanel.add(multiPlayer);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 2)));		// space between buttons
			
		// AI button
		JButton aiButton = new JButton("PLAY VS AI");
		aiButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		aiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		aiButton.setIcon(Icons.otherIcons.LABELBACKGROUND);
		aiButton.setContentAreaFilled(false);
		aiButton.setBorder(null);
		aiButton.setHorizontalTextPosition(0);
		aiButton.setForeground(Color.orange);
		centerPanel.add(aiButton);	
		centerPanel.add(Box.createRigidArea(new Dimension(0, 2)));		// space between buttons
		
		// exit button
		JButton exitButton = new JButton("EXIT");
		exitButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setIcon(Icons.otherIcons.LABELBACKGROUND);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorder(null);
		exitButton.setHorizontalTextPosition(0);
		exitButton.setForeground(Color.orange);
		centerPanel.add(exitButton);	

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		backgroundPanel.add(centerPanel, gbc);
		
		continueGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Load a saved game (.json)");
				int result = fileChooser.showOpenDialog(MainWindow.this);
				if(result == JFileChooser.APPROVE_OPTION) {
					String filePath = fileChooser.getSelectedFile().getAbsolutePath();
					if(!filePath.toLowerCase().endsWith(".json")) {
						JOptionPane.showMessageDialog(MainWindow.this,  "Invalid file type. Please select a .json file.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(MainWindow.this, "Selected game: \n" + filePath);
				}
				
			}
		});
		
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSpinner spinner = new JSpinner(new SpinnerNumberModel(2, 1, 4, 1));	// default, min, max, step
				int result = JOptionPane.showConfirmDialog(
						MainWindow.this,
						spinner,
						"Select number of players",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.OK_OPTION) {
					int numPlayers = (Integer) spinner.getValue();
					_ctrl.setPlayerNum(numPlayers);
					_ctrl.setupPlayers();

					String[] names = new String[numPlayers];
					for(int i = 0; i < numPlayers; i++) {
						String name;
						do {
							name = JOptionPane.showInputDialog(MainWindow.this, "Enter name for Player " + (i + 1) + ":");
						} while(name == null || name.trim().isEmpty());
						names[i] = name.trim();
					}
					
					_ctrl.setPlayerNames(Arrays.asList(names));
					_ctrl.setupPlayers();
					// replace content of mainwindow for gamewindow
					swapToGameWindow();
				}
			}
		});
		
		
		
		

		setVisible(true);
	}

	private static class ShadowLabel extends JLabel{
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
	
	private void swapToGameWindow() {
		SwingUtilities.invokeLater(() -> {
			getContentPane().removeAll(); // delete everything before
			getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // same as game window
			new GameWindow(_ctrl,this);	
			
			// updates and redraws the components
			revalidate();
			repaint();
			setExtendedState(JFrame.MAXIMIZED_BOTH);	
			
			addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == 0) {
						pack();		// adjusts size of the window with the actual content
					}				
				}
			});
		});
	}
}
