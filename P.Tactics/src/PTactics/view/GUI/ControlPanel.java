package PTactics.view.GUI;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Game;
import PTactics.view.GameObserver;

public class ControlPanel extends JPanel implements GameObserver{

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	private JTextArea txtrCurrentSelectedTroop;
	private JToggleButton moveButton;
	private JToggleButton aimButton;
	private JToggleButton abilityButton;
	
	private ControllerInterface _cntr;
	public ControlPanel(ControllerInterface cntr) {
		this._cntr=cntr;
		this._cntr.addObserver(this);
		setLayout(null);
		
		txtrCurrentSelectedTroop = new JTextArea();
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
	@Override
	public void onPlayersUpdate(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBoardUpdate(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTroopAction(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTroopSelection(Game game) {
		this.txtrCurrentSelectedTroop.setText("Current Troop Selected \n "+this._cntr.getGame().getTroop().getId()+"\n Moves Left: "+ this._cntr.getGame().getTroop().getMovesLeft()+"\n Ability Uses Left: "+this._cntr.getGame().getTroop().abilityUsesLeft());
	}
	@Override
	public void onNextTurn(Game game) {
		// TODO Auto-generated method stub
		
	}
}
