package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class Troop extends GameObject{
	List<Position> moveQueue;
	List<Position> currentMove;
	private Direction _dir;
	
	public Troop (Position pos) 
	{
	    super(pos);
	    this.moveQueue = new ArrayList<>();  // Initialize the lists
        this.currentMove = new ArrayList<>();
        this.solid=false;
        this._dir = Direction.NONE;
	}
	public void AddToMove(Position pos) 
	{
		moveQueue.add(pos);
	}
	// this is a weird way that you have to do this for the values of StepCount to be passed by reference
	private static class StepCount {
        int value;
    }
	public void CalcNewMove(Position pos) {
	    List<List<Integer>> Dirs = Arrays.asList(
	        Arrays.asList(-1, 0),
	        Arrays.asList(1, 0),
	        Arrays.asList(0, -1),
	        Arrays.asList(0, 1)
	    );

	    List<Position> bestSol = new ArrayList<>();
	    List<Position> curSol = new ArrayList<>();
	    Set<Position> marks = new HashSet<>();
	    StepCount bestSolSteps = new StepCount();
	    bestSolSteps.value = Integer.MAX_VALUE;

	    _backTrackPathFinding(pos, curSol, bestSol, marks, Dirs, 0, bestSolSteps, pos);
	    this.currentMove = bestSol;
	}

	private void _backTrackPathFinding(Position dest, List<Position> curSol,
	                                   List<Position> bestSol, Set<Position> marks,
	                                   List<List<Integer>> Dirs, int curSolSteps,
	                                   StepCount bestSolSteps, Position it) {
	    curSol.add(it);
	    marks.add(it);

	    if (it.equals(dest)) {
	        if (curSolSteps < bestSolSteps.value) {
	            bestSol.clear();
	            bestSol.addAll(curSol);
	            bestSolSteps.value = curSolSteps;
	        }
	    } else {
	        for (List<Integer> dir : Dirs) {
	            Position movePos = new Position(it.X + dir.get(0), it.Y + dir.get(1));
	            if (!this.BI.isSolid(movePos) && !marks.contains(movePos)) {
	                _backTrackPathFinding(dest, curSol, bestSol, marks, Dirs, curSolSteps + 1, bestSolSteps, movePos);
	            }
	        }
	    }

	    curSol.remove(curSol.size() - 1);
	    marks.remove(it);
	}

	public void Move() 
	{
		if(!moveQueue.isEmpty()) 
		{
			if(!currentMove.isEmpty()) 
			{
				this.setPosition(this.currentMove.getFirst());
				this.currentMove.removeFirst();
			}
			else 
			{
				CalcNewMove(moveQueue.getFirst());
				moveQueue.removeFirst();
				this.setPosition(this.currentMove.getFirst());
				this.currentMove.removeFirst();
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
