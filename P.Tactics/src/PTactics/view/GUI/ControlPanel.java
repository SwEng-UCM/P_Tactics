package PTactics.view.GUI;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ControlPanel() {
		setLayout(null);
		
		JTextArea txtrControlPanel = new JTextArea();
		txtrControlPanel.setText("Control Panel");
		txtrControlPanel.setBounds(164, 43, 150, 22);
		add(txtrControlPanel);

	}

}
