package PTactics.view.GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Game;
import PTactics.utils.Position;
import PTactics.view.GameObserver;


public class GameBoardCell extends JButton implements GameObserver{
	private static final long serialVersionUID = 1L;
	
	private Position _pos;
	
	private ControllerInterface _cntr;
	public GameBoardCell(Position pos, ControllerInterface _cntr) 
	{
		this._cntr=_cntr;
		_cntr.addObserver(this);
		this.setBorderPainted(true);
		this._pos=pos;
		this.setContentAreaFilled(false);
		this.setPreferredSize(new Dimension(69, 69));
		this.setMaximumSize(new Dimension(69, 69));;
		this.setMinimumSize(new Dimension(69, 69));;
		this.setBorderPainted(true);
	}
	public Position getPosition() 
	{
		return this._pos;
	}
	@Override
	public void onPlayersUpdate(Game game) {
		// TODO Auto-generated method stub
		updateCell();
	}

	@Override
	public void onBoardUpdate(Game game) {
		// TODO Auto-generated method stub
		updateCell(); 
	}

	@Override
	public void onTroopAction(Game game) {
		// TODO Auto-generated method stub
		updateCell();
	}

	@Override
	public void onTroopSelection(Game game) {
		updateCell();
		
	}

	@Override
	public void onNextTurn(Game game) {
		updateCell();
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
		
		else {
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}
		repaint();
	}
	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub
		
	}
}
