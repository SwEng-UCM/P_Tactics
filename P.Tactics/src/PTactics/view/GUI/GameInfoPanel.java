package PTactics.view.GUI;

import javax.swing.DropMode;
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
		
		JTextArea placeHolderText = new JTextArea();
		placeHolderText.setEditable(false);
		placeHolderText.setBounds(23, 22, 148, 29);
		placeHolderText.setText("Game Info Panel");
		placeHolderText.setHighlighter(null); // disables text highlighting
		placeHolderText.setFocusable(false);  // disables focus (no caret or selection)
		add(placeHolderText);

	}

}
