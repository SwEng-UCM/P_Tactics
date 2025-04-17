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
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import javax.swing.Timer;

import PTactics.control.Controller;
import PTactics.view.GUI.Icons.otherIcons;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private Controller _ctrl;
	private JButton[] _animatedButtons;
	private String[] _buttonTexts = { "START", "CONTINUE", "ONLINE", "PLAY VS AI", "EXIT"};
	private JPanel _buttonPanel;

	public MainWindow(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		setTitle("HOLDFIRE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);		// full screen
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
		
		ShadowLabel title = new ShadowLabel("HOLDFIRE", new Font("Algerian", Font.BOLD, 100), Color.WHITE, Color.BLACK, 4);
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
			int result = JOptionPane.showConfirmDialog(
					this, spinner, "Select number of players",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (result == JOptionPane.OK_OPTION) {
				int numPlayers = (Integer) spinner.getValue();
				_ctrl.setPlayerNum(numPlayers);

				String[] names = new String[numPlayers];
				for (int i = 0; i < numPlayers; i++) {
					String name;
					do {
						name = JOptionPane.showInputDialog(this, "Enter name for Player " + (i + 1) + ":");
					} while (name == null || name.trim().isEmpty());
					names[i] = name.trim();
				}

				_ctrl.setPlayerNames(Arrays.asList(names));
				_ctrl.setupPlayers();
				swapToGameWindow();
			}
		});
		
		// continue button
		_animatedButtons[1].addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Load a saved game (.json)");
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				if (!filePath.toLowerCase().endsWith(".json")) {
					JOptionPane.showMessageDialog(this, "Invalid file type. Please select a .json file.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(this, "Selected game: \n" + filePath);
				swapToGameWindow();
			}
		});
		
		// online button
		_animatedButtons[2].addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "ONLINE mode coming soon!");
//			swapToGameWindow();			// not needed for now
		});
				
		// play VS ai button
		_animatedButtons[3].addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "AI mode coming soon!");
			_ctrl.setPlayerNum(2);
			List<String> names= new ArrayList<String>();
			names.add("Player");
			names.add("CPU");
			_ctrl.setPlayerNames(names);
			_ctrl.SetupPlayerWithCPU(0); //make this a COMBO box selector
			swapToGameWindow();			// no needed for now
		});
		// exit button
		_animatedButtons[4].addActionListener(e -> System.exit(0));
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
				if (finished) ((Timer) e.getSource()).stop();
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
