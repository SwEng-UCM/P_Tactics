package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.List;

import PTactics.Utils.Position;

public class Troop extends GameObject{
	private String icon = "🪖";
	List<Position> MoveQueue;
	List<Position> CurrentMove;
	
	public Troop (Position pos) 
	{
	    super(pos);
	    this.MoveQueue = new ArrayList<>();  // Initialize the lists
        this.CurrentMove = new ArrayList<>();
        this.solid=false;
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
		return "▲";
	}
	@Override
	public void update() {
		
	}
}
