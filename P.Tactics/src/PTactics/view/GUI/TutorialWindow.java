package PTactics.view.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;

public class TutorialWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TroopTutorial tt;
	private ControlsTutorial ct;
	private WinTutorial wt;


	/**
	 * Create the frame.
	 */
	public TutorialWindow() {
		setVisible(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		tt = new TroopTutorial();
		ct = new ControlsTutorial();
		wt = new WinTutorial();
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Troops");
		btnNewButton.addActionListener(
				e -> {
					tt.setVisible(true);
				}
			);
		btnNewButton.setBounds(172, 67, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Controls");
		btnNewButton_1.addActionListener(
			e -> {
				ct.setVisible(true);
			}
		);
		btnNewButton_1.setBounds(172, 119, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("How to win!");
		btnNewButton_2.addActionListener(
				e -> {
					wt.setVisible(true);
				}
			);
		btnNewButton_2.setBounds(172, 171, 89, 23);
		contentPane.add(btnNewButton_2);
	}
	
}
