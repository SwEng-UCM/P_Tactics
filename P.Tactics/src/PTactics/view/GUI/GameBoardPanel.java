package PTactics.view.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.control.commands.MoveCommand;
import PTactics.control.commands.SelectTroopCommand;
import PTactics.utils.Position;

public class GameBoardPanel extends JPanel {
    private JButton[][] _buttons;
    
    private ControllerInterface _cntr;
    
    private ControlPanel _cPanel;
    
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
		            	}
		            	else if(this._cPanel.getControlSelection()==1)
		            	{
		            		//aim;
		            	}
		            	else if(this._cPanel.getControlSelection()==2)
		            	{
		            		//ability
		            	}
		            });
		            add(btn);
		        }
		    }
	}
    
    public JButton getButton(int row, int col) {
        return _buttons[row][col];
    }
}