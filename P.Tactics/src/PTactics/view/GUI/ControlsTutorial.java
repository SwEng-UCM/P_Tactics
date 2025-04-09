package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class ControlsTutorial extends JDialog {

	private static final long serialVersionUID = 1L;


	public ControlsTutorial() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setVisible(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Gameplay:");
			lblNewLabel.setBounds(10, 11, 80, 14);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("General:");
			lblNewLabel_1.setBounds(10, 123, 46, 14);
			getContentPane().add(lblNewLabel_1);
		}
		{
			JLabel lblMove = new JLabel("Save:");
			lblMove.setBounds(60, 173, 68, 14);
			getContentPane().add(lblMove);
		}
		{
			JLabel lblAim = new JLabel("Load:");
			lblAim.setBounds(192, 173, 39, 14);
			getContentPane().add(lblAim);
		}
		{
			JLabel lblAbility = new JLabel("Undo:");
			lblAbility.setBounds(309, 173, 45, 14);
			getContentPane().add(lblAbility);
		}
		
		JButton btnNewButton = new JButton("Move");
		btnNewButton.setBounds(25, 59, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Aim");
		btnNewButton_1.setBounds(124, 59, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Ability");
		btnNewButton_2.setBounds(223, 59, 89, 23);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("End turn");
		btnNewButton_3.setBounds(318, 59, 89, 23);
		getContentPane().add(btnNewButton_3);
	}
}
