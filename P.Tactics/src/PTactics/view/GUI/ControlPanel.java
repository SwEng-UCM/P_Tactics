package PTactics.view.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import PTactics.control.ControllerInterface;
import PTactics.control.commands.AbilityCommand;
import PTactics.model.game.Game;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;

public class ControlPanel extends JPanel implements GameObserver {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	
	private JLabel txtrCurrentSelectedTroop;
	JToggleButton moveButton;
	JToggleButton aimButton;
	JToggleButton abilityButton;
	private ButtonGroup toggleGroup;

	private ControllerInterface _cntr;

	public ControlPanel(ControllerInterface cntr) {
		this._cntr = cntr;
		this._cntr.addObserver(this);
		this.setMaximumSize(new Dimension(1000, 300));
		setLayout(new FlowLayout());
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.setOpaque(false);
		
		txtrCurrentSelectedTroop = new JLabel(Icons.otherIcons.TEXTAREABACKGROUND);
		txtrCurrentSelectedTroop.setBackground(new Color(1,1,1, (float) 0.01));
		txtrCurrentSelectedTroop.setText("Current Selected Troop:");
		txtrCurrentSelectedTroop.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtrCurrentSelectedTroop.setForeground(Color.getHSBColor(0, 0, 0));
		txtrCurrentSelectedTroop.setMaximumSize(new Dimension(175, 100));
		txtrCurrentSelectedTroop.setMinimumSize(new Dimension(175, 100));
		txtrCurrentSelectedTroop.setBorder(null);
		txtrCurrentSelectedTroop.setAlignmentX(CENTER_ALIGNMENT);
		txtrCurrentSelectedTroop.setHorizontalTextPosition(0);
		add(txtrCurrentSelectedTroop);

		add(Box.createRigidArea(new Dimension(30, 0))); 		
		
		moveButton = new JToggleButton("Move");
//		moveButton.setBounds(270, 26, 148, 82);
		moveButton.setAlignmentX(CENTER_ALIGNMENT);
		moveButton.setIcon(Icons.otherIcons.LABELBACKGROUND);
		moveButton.setContentAreaFilled(false);
		moveButton.setBorder(null);
		moveButton.setHorizontalTextPosition(0);
		moveButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		moveButton.setForeground(Color.orange);
		add(moveButton);

		add(Box.createRigidArea(new Dimension(30, 0))); 
		
		aimButton = new JToggleButton("Aim");
		// aimButton.setBounds(446, 25, 148, 83);
		aimButton.setAlignmentX(CENTER_ALIGNMENT);
		aimButton.setIcon(Icons.otherIcons.LABELBACKGROUND);
		aimButton.setContentAreaFilled(false);
		aimButton.setBorder(null);
		aimButton.setHorizontalTextPosition(0);
		aimButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		aimButton.setForeground(Color.orange);
		add(aimButton);

		add(Box.createRigidArea(new Dimension(30, 0))); 
		
		abilityButton = new JToggleButton("Ability");
		// abilityButton.setBounds(624, 25, 148, 83);
		abilityButton.setAlignmentX(CENTER_ALIGNMENT);
		abilityButton.setIcon(Icons.otherIcons.LABELBACKGROUND);
		abilityButton.setContentAreaFilled(false);
		abilityButton.setBorder(null);
		abilityButton.setHorizontalTextPosition(0);
		abilityButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		abilityButton.setForeground(Color.orange);
		add(abilityButton);

		toggleGroup = new ButtonGroup(); // makes the buttons mutually exclusive
		toggleGroup.add(moveButton);
		toggleGroup.add(aimButton);
		toggleGroup.add(abilityButton);
	}

	public int getControlSelection() { // 0 move 1 aim 2 ability : in order from left to right
		if (moveButton.isSelected())
			return 0;
		else if (aimButton.isSelected())
			return 1;
		else if (abilityButton.isSelected()) {
			if (_cntr.isTroopSelected() && _cntr.getGame().getTroop().getId() == Utils.TroopUtils.LIGHT_TROOP_ID) {
				AbilityCommand ability = new AbilityCommand(_cntr.getGame().getTroop().getPos().getX(),
						_cntr.getGame().getTroop().getPos().getY());
				ability.execute(_cntr); 
			} else {
				return 2;
			}
		}
		return -1; // none selected or light
	}

	@Override
	public void onPlayersUpdate(Game game) {
		updateText();

	}

	@Override
	public void onBoardUpdate(Game game) {
		updateText();
	}

	@Override
	public void onTroopAction(Game game) {

		updateText();
	}

	@Override
	public void onTroopSelection(Game game) {
		updateText();
	}

	@Override
	public void onNextTurn(Game game) {
		txtrCurrentSelectedTroop.setText("Current Selected Troop:");
	}

	public void updateText() {
		if (!_cntr.isTroopSelected()) {
			txtrCurrentSelectedTroop.setText("Current Selected Troop:");
			return;
		}
		
		String selectedTroopInfo = "Current Troop Selected" + "<br>" + this._cntr.getGame().getTroop().getId().toUpperCase()
				+ "<br>" + "Moves Left: " + this._cntr.getGame().getTroop().getMovesLeft() 
				+ "<br>" + "Ability Uses Left: " + this._cntr.getGame().getTroop().abilityUsesLeft();
		
		this.txtrCurrentSelectedTroop.setText("<html>" + selectedTroopInfo + "</html>");
	}

	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub

	}

	public void resetControlSelection() {
		toggleGroup.clearSelection();
	}
}
