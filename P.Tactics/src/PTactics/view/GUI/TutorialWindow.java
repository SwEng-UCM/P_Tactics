package PTactics.view.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

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
	private JPanel contentPane;


	public TutorialWindow() {
		setVisible(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setSize(500,500);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(createHeaderLabel("CONTROLS"));

        // select command
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("➙ Select command:"));
        contentPanel.add(createIndentedLabel(" -Shortcut: none"));
        contentPanel.add(createIndentedLabel(" -Description: Left click on an ally troop to select said troop."));

        // move command
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JLabel(new ImageIcon("Icons/move_icon.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("➙ Move command:"));
        contentPanel.add(createIndentedLabel(" -Shortcut: m"));
        contentPanel.add(createIndentedLabel(" -Description: Shows the path the selected troop will take and executes when destination is selected."));

        // aim command
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JLabel(new ImageIcon("Icons/aim_icon.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("➙ Aim:"));
        contentPanel.add(createIndentedLabel(" -Shortcut: a"));
        contentPanel.add(createIndentedLabel(" -Description: Press to enter aiming mode and select a board cell to aim in that direction"));
        contentPanel.add(createIndentedLabel(" -Warning!: No diagonal aiming."));

        // ability command
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JLabel(new ImageIcon("Icons/ability_icon.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("➙ Ability:"));
        contentPanel.add(createIndentedLabel(" -Shortcut: b"));
        contentPanel.add(createIndentedLabel(" -Description: Press to enter ability mode, then select a cell to deploy the ability in."));

        // endTurn command
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JLabel(new ImageIcon("Icons/endTurn_icon.png")));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("➙ End turn:"));
        contentPanel.add(createIndentedLabel( "Gives control of the board to the next player."));

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(createHeaderLabel("TROOPS & ABILITIES"));

        // lightTroop description
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("➙ LightTroop (dash):"));
        contentPanel.add(createIndentedLabel(" -Description: Advance 3 blocks in an invulnerable state"));
        contentPanel.add(Box.createVerticalStrut(10));
        JLabel lightTroopIcon = new JLabel(new ImageIcon("Icons/LightTroop_down.png"));
        contentPanel.add(lightTroopIcon);
     
        
        // sniperTroop description
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("➙ Sniper (drone):"));
        contentPanel.add(createIndentedLabel(" -Description: Deploy a 3x3 visibility zone"));
        contentPanel.add(Box.createVerticalStrut(10));
        JLabel sniperIcon = new JLabel(new ImageIcon("Icons/sniper_down.png"));
        contentPanel.add(sniperIcon);
        
        // smokerTroop description
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createBoldLabel("➙ Smoker (smoke):"));
        contentPanel.add(createIndentedLabel(" -Description: Deploy a 3x3 smoke zone"));
        contentPanel.add(Box.createVerticalStrut(10));
        JLabel smokerTroopIcon = new JLabel(new ImageIcon("Icons/Smoker_down.png"));
        contentPanel.add(smokerTroopIcon);
      
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null); // Optional: removes border

        getContentPane().add(scrollPane);
    
	}
	private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createBoldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, 18));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createIndentedLabel(String text) {
        JLabel label = new JLabel("    " + text); // Indentation via spaces
        label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
	
}
