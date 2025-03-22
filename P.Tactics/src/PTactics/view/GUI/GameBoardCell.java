package PTactics.view.GUI;

import javax.swing.JButton;

import PTactics.control.Controller;
import PTactics.model.game.Game;
import PTactics.utils.Position;
import PTactics.view.GameObserver;


public class GameBoardCell extends JButton implements GameObserver{
	private static final long serialVersionUID = 1L;
	
	private Position _pos;
	
	private Controller _cntr;
	public GameBoardCell(Position pos, Controller cntr) 
	{
		this._cntr=cntr;
		this.setText("O");
		_cntr.addObserver(this);
		this._pos=pos;
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
