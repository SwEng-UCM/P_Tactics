package PTactics.view.GUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class GameInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public GameInfoPanel() {
		setLayout(null);
		
		JTextArea txtrGameInfoPanel = new JTextArea();
		txtrGameInfoPanel.setBounds(23, 22, 148, 29);
		txtrGameInfoPanel.setText("Game Info Panel");
		add(txtrGameInfoPanel);

	}

}
