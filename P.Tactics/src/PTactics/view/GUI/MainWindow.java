package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import PTactics.control.Controller;
import PTactics.view.GUI.Icons.otherIcons;

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

		// wrapper centered with flowlayout
		JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
		wrapper.setOpaque(false);
		
		// central panel that contains title and button
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false); // transparent background to see the image
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		// centered title
		JLabel title = new JLabel("P.Tactics");
		title.setFont(new Font("Algerian", Font.BOLD, 58));
		title.setForeground(Color.WHITE);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(title);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

		// start button
		JButton start = new JButton("START GAME");
		start.setFont(new Font("Times New Roman", Font.BOLD, 20));
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(start);
		
		// add background
		wrapper.add(centerPanel);
		backgroundPanel.add(wrapper, BorderLayout.CENTER);
		
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

	private void swapToGameWindow() {
		getContentPane().removeAll(); // delete everything before
		getContentPane().setLayout(null); // same as game window

		GameInfoPanel gameInfo = new GameInfoPanel(_ctrl);
		gameInfo.setBounds(0, 0, 1227, 60);
		getContentPane().add(gameInfo);

		ControlPanel control = new ControlPanel(_ctrl);
		control.setBounds(0, 759, 1227, 158);
		getContentPane().add(control);

		GameBoardPanel gameBoard = new GameBoardPanel(
				PTactics.utils.Position._gameLength,
				PTactics.utils.Position._gameWidth,
				_ctrl,
				control
		);
		gameBoard.setBounds(250, 59, 700, 700);
		getContentPane().add(gameBoard);

		setSize(1243, 956);
		setLocationRelativeTo(null); // center again
		repaint();
	}
}
