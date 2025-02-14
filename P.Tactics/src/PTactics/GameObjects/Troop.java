package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.List;

import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class Troop extends GameObject{
	List<Position> MoveQueue;
	List<Position> CurrentMove;
	private Direction _dir;
	
	public Troop (Position pos) 
	{
	    super(pos);
	    this.MoveQueue = new ArrayList<>();  // Initialize the lists
        this.CurrentMove = new ArrayList<>();
        this.solid=false;
        this._dir = null;
	}
	public void AddToMove(Position pos) 
	{
		MoveQueue.add(pos);
	}
	public void CalcNewMove(Position pos) 
	{
		//BackTracking to find new sol Only codable when i have the board 
	}
	public void Move() 
	{
		if(!MoveQueue.isEmpty()) 
		{
			if(!CurrentMove.isEmpty()) 
			{
				this.setPosition(this.CurrentMove.getFirst());
				this.CurrentMove.removeFirst();
			}
			else 
			{
				CalcNewMove(MoveQueue.getFirst());
				MoveQueue.removeFirst();
				this.setPosition(this.CurrentMove.getFirst());
				this.CurrentMove.removeFirst();
			}
		}
	}
	
	@Override
	public String toString() {
		if(_dir == Direction.UP) {
			return Utils.TroopUtils.TROOP_FACING_UP;
		}
		else if(_dir == Direction.DOWN) {
			return Utils.TroopUtils.TROOP_FACING_DOWN;
		}
		else if(_dir == Direction.LEFT) {
			return Utils.TroopUtils.TROOP_FACING_LEFT;
		}
		else if(_dir == Direction.RIGHT) {
			return Utils.TroopUtils.TROOP_FACING_RIGHT;
		}
		
		return Utils.TroopUtils.TROOP_ICON;
	}
	
	@Override // TODO: i need this // the move to be boolean and return true if there are moves left in the queue so I can paint it step by step
	public void update() {
		
	}
}
