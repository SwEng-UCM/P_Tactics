package PTactics.view.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.control.TroopInfo;
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
		this.setPreferredSize(new Dimension(Position.tileSize, Position.tileSize));
		this.setMaximumSize(new Dimension(Position.tileSize, Position.tileSize));;
		this.setMinimumSize(new Dimension(Position.tileSize, Position.tileSize));;
		this.setBorderPainted(true);
	}
	public Position getPosition() 
	{
		return this._pos;
	}
	
	public void updateCell() 
	{
		this.setIcon(_cntr.getIcon(this._pos));
		TroopInfo tInfo = _cntr.getCurrentTroopInfo();
		List<Position> path = _cntr.getPath(_pos);
		if (tInfo != null && tInfo.getPos().equals(_pos)) {
			this.setBorderPainted(true);
			this.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
		}
		else if (_cntr.dangerTile(_pos)) {
			this.setBorderPainted(true);
			this.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		} 
		else if (path != null && path.contains(_pos)) {
			this.setBorderPainted(true);
			this.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		}
		else if (_cntr.isWinPosition(_pos)) {
			this.setBorderPainted(true);
			this.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		}
		
		else {
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}
		repaint();
	}
}
