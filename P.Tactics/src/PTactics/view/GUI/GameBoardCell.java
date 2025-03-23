package PTactics.view.GUI;

import javax.swing.JButton;

import PTactics.control.Controller;
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
		this.setText("O");
		_cntr.addObserver(this);
		this._pos=pos;
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
		
	}

	@Override
	public void onTroopSelection(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTurn(Game game) {
		// TODO Auto-generated method stub
	}
	public void updateCell() 
	{
		this.setText(this._cntr.positionToString(this._pos));
	}
}
