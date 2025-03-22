package PTactics.view.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


public class GameBoardPanel extends JPanel {
    private JButton[][] buttons;
    public GameBoardPanel(int width,int height) {
    	buttons = new JButton[height][width];
   	 setLayout(new GridLayout(width, height));
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                GameBoardCell btn = new GameBoardCell();
                buttons[row][col] = btn;
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
        return buttons[row][col];
    }
}