package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class WinTutorial extends JDialog {

	private static final long serialVersionUID = 1L;


	public WinTutorial() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setVisible(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("In the works");
			lblNewLabel.setBounds(176, 114, 68, 14);
			getContentPane().add(lblNewLabel);
		}
	}

}
