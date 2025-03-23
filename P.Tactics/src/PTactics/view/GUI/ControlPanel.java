package PTactics.view.GUI;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	JToggleButton moveButton;
	JToggleButton aimButton;
	JToggleButton abilityButton;
	public ControlPanel() {
		setLayout(null);
		
		JTextArea txtrCurrentSelectedTroop = new JTextArea();
		txtrCurrentSelectedTroop.setText("Current Selected Troop:");
		txtrCurrentSelectedTroop.setBounds(10, 11, 236, 83);
		add(txtrCurrentSelectedTroop);
		
		moveButton = new JToggleButton("Move");
		moveButton.setBounds(270, 12, 148, 82);
		add(moveButton);
		
		aimButton = new JToggleButton("Aim");
		aimButton.setBounds(446, 11, 148, 83);
		add(aimButton);
		
		abilityButton = new JToggleButton("Ability");
		abilityButton.setBounds(624, 11, 148, 83);
		add(abilityButton);
		
		ButtonGroup toggleGroup = new ButtonGroup(); //makes the buttons mutually exclusive
		toggleGroup.add(moveButton);
		toggleGroup.add(aimButton);
		toggleGroup.add(abilityButton);
	}
	public int getControlSelection() { //0 move 1 aim 2 ability : in order from left to right
	    if (moveButton.isSelected()) return 0;
	    if (aimButton.isSelected()) return 1;
	    if (abilityButton.isSelected()) return 2;
	    return -1; // none selected
	}
}
