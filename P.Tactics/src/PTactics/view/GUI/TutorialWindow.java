package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TutorialWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final Color BG_COLOR = new Color(60, 40, 20);		// dark brown

	public TutorialWindow() {
		setTitle("HOLDFIRE");
		setVisible(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);		// to center
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // header welcome message
        contentPanel.add(createHeaderWelcomeLabel("WELCOME TO HOLDFIRE INSTRUCTIONS"));
        contentPanel.add(Box.createVerticalStrut(5));
        
        // header
        contentPanel.add(createHeaderLabel("CONTROLS"));

        // select command
        contentPanel.add(createSection(
        		"Select command:",
        		" -Shortcut: none",
				" -Description: Left click on an ally troop to select said troop.",
				null
        ));
        
        // move command
        contentPanel.add(createSection(
				"Move command:",
				" -Shortcut: m",
				" -Description: Shows the path the selected troop will take and executes when destination is selected.",
				"Icons/move_icon.png"
		));
        
        // aim command
        contentPanel.add(createSection(
				"Aim:",
				" -Shortcut: a",
				" -Description: Press to enter aiming mode and select a board cell to aim in that direction",
				"Icons/aim_icon.png"
		));
        
        // ability command
        contentPanel.add(createSection(
				"Ability:",
				" -Shortcut: b",
				" -Description: Press to enter ability mode, then select a cell to deploy the ability in.",
				"Icons/ability_icon.png"
		));
        
        // endTurn command
        contentPanel.add(createSection(
				"End Turn:",
				" -No shortcut",
				" -Gives control of the board to the next player.",
				"Icons/endTurn_icon.png"
		));
        
        contentPanel.add(Box.createVerticalStrut(35));
        contentPanel.add(createHeaderLabel("TROOPS & ABILITIES"));
        
        contentPanel.add(createTroopInfo(
				"LightTroop (dash):",
				" -Description: Advance 3 blocks in an invulnerable state",
				"Icons/LightTroop_down.png"
		));

		contentPanel.add(createTroopInfo(
				"Sniper (drone):",
				" -Description: Deploy a 3x3 visibility zone",
				"Icons/sniper_down.png"
		));

		contentPanel.add(createTroopInfo(
				"Smoker (smoke):",
				" -Description: Deploy a 3x3 smoke zone",
				"Icons/Smoker_down.png"
		));
		
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);		// scroll faster
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	
	private Component createHeaderLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Times New Roman", Font.BOLD, 25));
		label.setForeground(Color.WHITE);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		return label;
	}
	
	private Component createHeaderWelcomeLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Times New Roman", Font.BOLD, 30));
		label.setForeground(Color.WHITE);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		return label;
	}

	private Component createSection(String title, String line1, String line2, String icon) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(BG_COLOR);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		panel.add(createBoldLabel("➙ " + title));
		panel.add(createIndentedLabel(line1));
		panel.add(createIndentedLabel(line2));
		
		if (icon != null) {
			panel.add(Box.createVerticalStrut(10));
			JLabel iconLabel = new JLabel(resizeIcon(icon, 130, 45));
			iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel.add(iconLabel);
		}
		
		panel.add(Box.createVerticalStrut(20));
		
		return panel;
	}
	
	private JLabel createIndentedLabel(String text) {
		JLabel label = new JLabel("    " + text);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		label.setForeground(Color.LIGHT_GRAY);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		return label;
	}

	private JLabel createBoldLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Times New Roman", Font.BOLD, 19));
		label.setForeground(Color.WHITE);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		return label;
	}

	private Component createTroopInfo(String title, String description, String imagePath) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(BG_COLOR);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		panel.add(createBoldLabel("➙ " + title));
		panel.add(createIndentedLabel(description));
		panel.add(Box.createVerticalStrut(8));
		
		JLabel img = new JLabel(resizeIcon(imagePath, 64, 64));
		img.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(img);
		panel.add(Box.createVerticalStrut(15));
		
		return panel;
	}
	
	private ImageIcon resizeIcon(String path, int width, int height) {
		ImageIcon originalIcon = new ImageIcon(path);
		Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}
}
