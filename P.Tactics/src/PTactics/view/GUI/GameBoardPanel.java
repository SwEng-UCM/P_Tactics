package PTactics.view.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import PTactics.control.Controller;
import PTactics.utils.Position;

public class GameBoardPanel extends JPanel {
    private JButton[][] _buttons;
    
    private Controller _cntr;
    
    public GameBoardPanel(int width,int height,Controller cntr) {
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
                    System.out.println("Cell " + r + ", " + c);
                    btn.setBackground(Color.RED);
                });
                add(btn);
            }
        }
    }
    public JButton getButton(int row, int col) {
        return _buttons[row][col];
    }
}