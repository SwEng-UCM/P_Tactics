package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class TroopTutorial extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public TroopTutorial() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setVisible(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Light Troop:");
		lblNewLabel.setBounds(53, 108, 68, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblSniper = new JLabel("Sniper:");
		lblSniper.setBounds(191, 108, 39, 14);
		contentPanel.add(lblSniper);
		
		JLabel lblSmoker = new JLabel("Smoker:");
		lblSmoker.setBounds(302, 108, 45, 14);
		contentPanel.add(lblSmoker);
		
		JLabel lblNewLabel_1 = new JLabel("Ability: Dash");
		lblNewLabel_1.setBounds(53, 133, 68, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Invulnerable 3 block move");
		lblNewLabel_2.setBounds(24, 147, 125, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("Ability: Drone");
		lblNewLabel_1_1.setBounds(177, 133, 68, 14);
		contentPanel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("3X3 visible spot");
		lblNewLabel_2_1.setBounds(172, 147, 79, 14);
		contentPanel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Ability: Smoke");
		lblNewLabel_1_1_1.setBounds(289, 133, 68, 14);
		contentPanel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Blocks visibility in 3X3 blocks");
		lblNewLabel_2_1_1.setBounds(261, 147, 140, 14);
		contentPanel.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("to all troops");
		lblNewLabel_2_1_1_1.setBounds(261, 158, 140, 14);
		contentPanel.add(lblNewLabel_2_1_1_1);
	}
}
