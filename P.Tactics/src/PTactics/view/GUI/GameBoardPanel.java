package PTactics.view.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.control.commands.SelectTroopCommand;
import PTactics.utils.Position;

public class GameBoardPanel extends JPanel {
    private JButton[][] _buttons;
    
    private ControllerInterface _cntr;
    
    public GameBoardPanel(int width,int height,ControllerInterface cntr) {
    	this._cntr=cntr;
    _buttons = new JButton[height][width];
   	 setLayout(new GridLayout(width, height));
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
            	Position pos= new Position(col,row);
                GameBoardCell btn = new GameBoardCell(pos,this._cntr);
                _buttons[row][col] = btn;
                int r = row, c = col;
                btn.addActionListener(e -> {
                    //System.out.println("Cell " + r + ", " + c);
                	if(!this._cntr.isTroopSelected()) 
                	{
                		SelectTroopCommand select= new SelectTroopCommand();
                		select.setX(btn.getPosition().getX());
                		select.setY(btn.getPosition().getY());
                		select.execute(cntr);
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