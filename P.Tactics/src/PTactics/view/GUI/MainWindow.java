package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
		setTitle("P.TACTICS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);		// full screen
		setLocationRelativeTo(null); // center window

		// panel with background image
		BackgroundPanel backgroundPanel = new BackgroundPanel(otherIcons.BACKGROUND2.getImage());
		backgroundPanel.setLayout(new BorderLayout());
		setContentPane(backgroundPanel);
		setBackground(Color.black);

		// wrapper centered with flowlayout
		JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
		wrapper.setOpaque(false);
		
		// central panel that contains title and button
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false); // transparent background to see the image
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		// centered title
//		JLabel title = new JLabel("P.Tactics");
//		title.setFont(new Font("Algerian", Font.BOLD, 58));
//		title.setForeground(Color.WHITE);
		ShadowLabel title = new ShadowLabel("P.TACTICS", new Font("Algerian", Font.BOLD, 58), Color.WHITE, Color.BLACK, 3);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(title);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

		// start button
		JButton start = new JButton("START GAME");
		start.setFont(new Font("Times New Roman", Font.BOLD, 18));
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.setIcon(Icons.otherIcons.LABELBACKGROUND);
		start.setContentAreaFilled(false);
		start.setBorder(null);
		start.setHorizontalTextPosition(0);
		start.setForeground(Color.orange);
		centerPanel.add(start);
		
		// add background
		wrapper.add(centerPanel);
		backgroundPanel.add(wrapper, BorderLayout.CENTER);
		
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		start.addActionListener(new ActionListener() {
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
					_ctrl.setCorrect();
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
