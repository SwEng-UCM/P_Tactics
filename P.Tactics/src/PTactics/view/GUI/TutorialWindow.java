package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class TutorialWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public TutorialWindow() {
		setVisible(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setSize(500,500);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(createHeaderLabel("CONTROLS"));

        contentPanel.add(Box.createVerticalStrut(10));

        contentPanel.add(createBoldLabel("- Select:"));
        contentPanel.add(createIndentedLabel("shortcut -> none"));
        contentPanel.add(createIndentedLabel("Left click on an ally troop to select said troop."));


        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JLabel(new ImageIcon("Icons/move_icon.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("- Move:"));
        contentPanel.add(createIndentedLabel("shortcut -> m"));
        contentPanel.add(createIndentedLabel("Shows the path the selected troop will take and executes when destination is selected."));


        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JLabel(new ImageIcon("Icons/aim_icon.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("- Aim:"));
        contentPanel.add(createIndentedLabel("shortcut -> a"));
        contentPanel.add(createIndentedLabel("Press to enter aiming mode and select a board cell to aim in that direction"));
        contentPanel.add(createIndentedLabel("(No diagonal aiming)."));


        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JLabel(new ImageIcon("Icons/ability_icon.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("- Ability:"));
        contentPanel.add(createIndentedLabel("shortcut -> b"));
        contentPanel.add(createIndentedLabel("Press to enter ability mode, then select a cell to deploy the ability in."));

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JLabel(new ImageIcon("Icons/endTurn_icon.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createIndentedLabel("- End turn: Gives control of the board to the next player."));

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createHeaderLabel("TROOPS & ABILITIES"));

        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createIndentedLabel("- LightTroop: Dash -> Advance 3 blocks in an invulnerable state"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(new JLabel(new ImageIcon("Icons/LightTroop_down.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createIndentedLabel("- Sniper: Drone -> Deploy a 3x3 visibility zone"));
        contentPanel.add(Box.createVerticalStrut(10));
        JLabel sniperIcon = new JLabel(new ImageIcon("Icons/sniper_down.png"));
        //sniperIcon.setAlignmentX(Component.LEFT_ALIGNMENT); // does not work 
        contentPanel.add(sniperIcon);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createIndentedLabel("- Smoker: Smoke -> deploy a 3x3 smoke zone"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(new JLabel(new ImageIcon("Icons/Smoker_down.png")));
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null); // Optional: removes border

        getContentPane().add(scrollPane);
    
	}
	private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createBoldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createIndentedLabel(String text) {
        JLabel label = new JLabel("    " + text); // Indentation via spaces
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
	
}
