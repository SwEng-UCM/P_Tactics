package PTactics.view.GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.model.game.Board;
import PTactics.model.game.Game;
import PTactics.utils.Position;
import PTactics.view.GameObserver;


public class GameBoardCell extends JButton{
	private static final long serialVersionUID = 1L;
	
	private Position _pos;
	
	private ControllerInterface _cntr;
	public GameBoardCell(Position pos, ControllerInterface _cntr) 
	{
		this._cntr=_cntr;
		
		this.setBorderPainted(true);
		this._pos=pos;
		this.setContentAreaFilled(false);
		this.setPreferredSize(new Dimension(Controller.tileSize, Controller.tileSize));
		this.setMaximumSize(new Dimension(Controller.tileSize, Controller.tileSize));;
		this.setMinimumSize(new Dimension(Controller.tileSize, Controller.tileSize));;
		this.setBorderPainted(true);
	}
	public Position getPosition() 
	{
		return this._pos;
	}
	
	public void updateCell() 
	{
		this.setIcon(_cntr.getIcon(this._pos));
		if (_cntr.currTroop() != null && _cntr.currTroop().getPos().equals(_pos)) {
			this.setBorderPainted(true);
			this.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
		}
		else if (_cntr.dangerTile(_pos)) {
			this.setBorderPainted(true);
			this.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		} 
		else if (_cntr.getPath(_pos) != null && _cntr.getPath(_pos).contains(_pos)) {
			this.setBorderPainted(true);
			this.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		}
		else if (Board.getInstance().isWinPosition(_pos)) {
			this.setBorderPainted(true);
			this.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		}
		
		else {
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}
		repaint();
	}
}
