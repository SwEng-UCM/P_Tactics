package PTactics.view.GUI;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import PTactics.control.ControllerInterface;
import PTactics.control.commands.AbilityCommand;
import PTactics.model.game.Game;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;

public class ControlPanel extends JPanel implements GameObserver{

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	private JTextArea txtrCurrentSelectedTroop;
	JToggleButton moveButton;
	JToggleButton aimButton;
	JToggleButton abilityButton;
	private ButtonGroup toggleGroup;
	
	
	private ControllerInterface _cntr;
	public ControlPanel(ControllerInterface cntr) {
		this._cntr=cntr;
		this._cntr.addObserver(this);
		setLayout(null);
		
		txtrCurrentSelectedTroop = new JTextArea();
		txtrCurrentSelectedTroop.setBackground(UIManager.getColor("Button.background"));
		txtrCurrentSelectedTroop.setText("Current Selected Troop:");
		txtrCurrentSelectedTroop.setBounds(10, 0, 250, 156);
		txtrCurrentSelectedTroop.setBorder(null);
		txtrCurrentSelectedTroop.setEditable(false);
		add(txtrCurrentSelectedTroop);
		
		moveButton = new JToggleButton("Move");
		moveButton.setBounds(270, 26, 148, 82);
		add(moveButton);
		
		aimButton = new JToggleButton("Aim");
		aimButton.setBounds(446, 25, 148, 83);
		add(aimButton);
		
		abilityButton = new JToggleButton("Ability");
		abilityButton.setBounds(624, 25, 148, 83);
		add(abilityButton);
		
		toggleGroup = new ButtonGroup(); //makes the buttons mutually exclusive
		toggleGroup.add(moveButton);
		toggleGroup.add(aimButton);
		toggleGroup.add(abilityButton);		
	}
	
	public int getControlSelection() { //0 move 1 aim 2 ability : in order from left to right
	    if (moveButton.isSelected()) return 0;
	    else if (aimButton.isSelected()) return 1;
	    else if (abilityButton.isSelected()) {
	    	if (_cntr.isTroopSelected() && _cntr.getGame().getTroop().getId() == Utils.TroopUtils.LIGHT_TROOP_ID) {
	    		AbilityCommand ability= new AbilityCommand(_cntr.getGame().getTroop().getPos().getX(), _cntr.getGame().getTroop().getPos().getY());
        		ability.execute(_cntr);
	    	}
	    	else {
	    		return 2;
	    	}
	    }
	    return -1; // none selected or light 
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
		this.txtrCurrentSelectedTroop.setText("Current Troop Selected \n "+this._cntr.getGame().getTroop().getId()+"\n Moves Left: "+ this._cntr.getGame().getTroop().getMovesLeft()+"\n Ability Uses Left: "+this._cntr.getGame().getTroop().abilityUsesLeft());
	}
	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub
		
	}
	public void resetControlSelection() {
		toggleGroup.clearSelection();
	}
}
