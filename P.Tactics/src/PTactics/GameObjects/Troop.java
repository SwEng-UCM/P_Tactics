package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import PTactics.Game.BoardInterface;
import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class Troop extends GameObject{
	List<Position> _moveQueue; // why package protected? unless there is a reason it should be private
	List<Position> _currentMove; // why package protected? unless there is a reason it should be private
	private Direction _dir;
	private boolean _aiming;
	private Player _player;
	private final int _visionRange = 5;
	
	public Troop (Position pos, BoardInterface BI) { // all GO constructors changed to include the board
	    super(pos, BI);
	    this._moveQueue = new ArrayList<>();  // Initialize the lists
        this._currentMove = new ArrayList<>();
        this.solid=false;
        this._dir = Direction.DOWN;
        this._aiming = false;
	}
	@Override
	public void AddToMove(Position pos) 
	{
		_moveQueue.add(pos);
	}
	// this is a weird way that you have to do this for the values of StepCount to be passed by reference
	//RECURSIVE BACKTRACKING IMPLEMENTATATION for troop pathfinding below
	private static class StepCount {
        int value;
    }
	private static class SolArray
	{
		List<Position> Sol = new ArrayList<>();
	}
	public void CalcNewMove(Position pos) {
	    List<List<Integer>> Dirs = Arrays.asList(
	        Arrays.asList(-1, 0),
	        Arrays.asList(1, 0),
	        Arrays.asList(0, -1),
	        Arrays.asList(0, 1)
	    );

	    SolArray bestSol = new SolArray();
	    SolArray curSol = new SolArray();
	    Set<Position> marks = new HashSet<>();
	    StepCount bestSolSteps = new StepCount();
	    StepCount curSolSteps = new StepCount();
	    bestSolSteps.value = Integer.MAX_VALUE;

	    _backTrackPathFinding(pos, curSol, bestSol, marks, Dirs, curSolSteps, bestSolSteps, this.pos);
	    this._currentMove = bestSol.Sol;
	}

	private void _backTrackPathFinding(Position dest, SolArray curSol,
			                          SolArray  bestSol, Set<Position> marks,
	                                   List<List<Integer>> Dirs, StepCount curSolSteps,
	                                   StepCount bestSolSteps, Position it) {
		

	    if (it.equals(dest)) {
	        if (curSolSteps.value < bestSolSteps.value) {
	            bestSol.Sol.clear();
	            bestSol.Sol.addAll(curSol.Sol);
	            bestSolSteps.value = curSolSteps.value;
	        }
	    } else {
	        for (List<Integer> dir : Dirs) {
	        	int x=it.X + dir.get(0);
	        	int y=it.Y + dir.get(1);
	            if(x>=0 && y>=0 && x<10 && y<10) 
	            {
	            	Position movePos = new Position(it.X + dir.get(0), it.Y + dir.get(1));
		            if (!this.BI.isSolid(movePos) && !marks.contains(movePos)) {
		            	curSolSteps.value++;
		        	    curSol.Sol.add(movePos);
		        	    marks.add(movePos);
		                _backTrackPathFinding(dest, curSol, bestSol, marks, Dirs, curSolSteps, bestSolSteps, movePos);
		                curSolSteps.value--;
		        	    curSol.Sol.remove(curSol.Sol.size() - 1);
		        	    //marks.remove(movePos);
		            }
	            }
	        }
	    }
	    
	}

	public void Move() 
	{
		// player has a function getDanger(Position pos) that returns if a troop is in 
		// in danger when stepping in the tile, should be called in each step.
			if(!_currentMove.isEmpty()) 
			{
				this.setPosition(this._currentMove.getFirst());
				this._currentMove.removeFirst();
			}
			else if(!this._moveQueue.isEmpty())
			{
				CalcNewMove(_moveQueue.getFirst());
				_moveQueue.removeFirst();
				this.setPosition(this._currentMove.getFirst());
				this._currentMove.removeFirst();
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
		Move();
	}
	
	public List<Position> visiblePositions() {
		List<Position> visiblePositions = new ArrayList<>();
		
		visiblePositions.add(getPos());
		Position pos = new Position(getPos().getX(), getPos().getY());
		
		
		for (int i = 0; i < _visionRange; i++) {
			pos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
			if (!BI.isValid(pos) || BI.isSolid(pos))
				break;
			visiblePositions.add(pos);
		}
		
		return visiblePositions;	
	}
	
	public void addPlayer(Player p) {
		_player = p;
	}
	
	public List<Position> dangerPositions() {
		List<Position> dangerPositions = new ArrayList<>();
		
		if (!_aiming) {
			return dangerPositions;
		}
		
		for (int i = 0; i < _visionRange; i++) {		// TODO: maybe change vision range
			pos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
			if (!BI.isValid(pos) || BI.isSolid(pos))
				break;
			dangerPositions.add(pos);
		}
		
		return dangerPositions;	
	}
	
	public void takeAim(Direction direction) {
		_dir = direction;
		_aiming = true;
	}
	
	public void stopAiming() {
		_aiming = false;
	}
	
	@Override
	public boolean isAlive() {
		return alive;
	}
	@Override
	public void die() {
		alive = false;
	}
}
