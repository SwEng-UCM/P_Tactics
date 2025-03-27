package PTactics.view.GUI;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import PTactics.control.ControllerInterface;
import PTactics.control.commands.AbilityCommand;
import PTactics.control.commands.AimCommand;
import PTactics.control.commands.MoveCommand;
import PTactics.control.commands.SelectTroopCommand;
import PTactics.utils.Direction;
import PTactics.utils.Position;

public class GameBoardPanel extends JPanel {
    private JButton[][] _buttons;
    
    private ControllerInterface _cntr;
    
    private ControlPanel _cPanel;
    private char _keyChar;
    
    public GameBoardPanel(int width,int height,ControllerInterface cntr, ControlPanel cPanel) {
    	this._cntr=cntr;
    	this._cPanel=cPanel;
    	
		_buttons = new JButton[height][width];
		 setLayout(new GridLayout(width, height));
		    for (int row = 0; row < height; row++) {
		        for (int col = 0; col < width; col++) {
		        	Position pos= new Position(col,row);
		            GameBoardCell btn = new GameBoardCell(pos,this._cntr);
		            _buttons[row][col] = btn;
		            
		            btn.addKeyListener(new KeyListener() {

		    			@Override
		    			public void keyTyped(KeyEvent e) {
		    				System.out.print(e.getKeyChar());
		    				
		    			}

		    			@Override
		    			public void keyPressed(KeyEvent e) {
		    				_keyChar = e.getKeyChar();
		    				if (_keyChar == 'm' || _keyChar == 'M') {
		    					_cPanel.resetControlSelection();
		    					_cPanel.moveButton.setSelected(true);
		    				}
		    				else if (_keyChar == 'a' || _keyChar == 'A') {
		    					_cPanel.resetControlSelection();
		    					_cPanel.aimButton.setSelected(true);
		    				}
		    				else if (_keyChar == 'b' || _keyChar == 'B') {
		    					_cPanel.resetControlSelection();
		    					_cPanel.abilityButton.setSelected(true);
		    				}
		    				_cPanel.getControlSelection();
		    			}

		    			@Override
		    			public void keyReleased(KeyEvent e) {
		    				System.out.print(e.getKeyChar());
		    			}
		    			
		    		});
		            
		            
		            btn.addActionListener(e -> {
		                //System.out.println("Cell " + r + ", " + c);
		            	//chaneg this to isTroop;
		            	if(this._cntr.isTroop(btn.getPosition())) 
		            	{
		            		SelectTroopCommand select= new SelectTroopCommand(btn.getPosition().getX(),btn.getPosition().getY());
		            		select.execute(cntr);
		            	}
		            	else if(this._cPanel.getControlSelection()==0)
		            	{
		            		MoveCommand move = new MoveCommand(btn.getPosition().getX(),btn.getPosition().getY());
		            		move.execute(cntr);
		            		//_cPanel.resetControlSelection();
		            	}
		            	else if(this._cPanel.getControlSelection()==1)
		            	{
		            		
		            		AimCommand aim = new AimCommand(posToDir(btn.getPosition().getX(), btn.getPosition().getY()));
		            		aim.execute(cntr);
		            		//_cPanel.resetControlSelection();

		            	}
		            	else if(this._cPanel.getControlSelection()==2)
		            	{
		            		AbilityCommand ability= new AbilityCommand(btn.getPosition().getX(),btn.getPosition().getY());
		            		ability.execute(cntr);
		            		//_cPanel.resetControlSelection();
		            	}
		            });
		            add(btn);
		        }
		    }
	}
    
    public JButton getButton(int row, int col) {
        return _buttons[row][col];
    }
    private Direction posToDir(int x, int y) {
    	int X = x - _cntr.currTroop().getPos().getX(); // given pos minus troop position (setting 0,0 at troop)
    	int Y = y - _cntr.currTroop().getPos().getY();
    	int abx = Math.abs(X); // see which axis is more prominent
    	int aby = Math.abs(Y);
    	if(abx < aby) {
    		return Y < 0? Direction.UP : Direction.DOWN;
    	}
    	else {
    		return X < 0? Direction.LEFT : Direction.RIGHT;
    	}
    	
    }
}