package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import PTactics.Game.Board;
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
	
	public Troop (Position pos, Player p) { // all GO constructors changed to include the board
	    super(pos);
	    this._moveQueue = new ArrayList<>();  // Initialize the lists
        this._currentMove = new ArrayList<>();
        this.solid=false;
        this._dir = Direction.DOWN;
        this._aiming = false;
        this._player = p;
        _player.addTroops(this);
	}
	
	//If want to personalize direction
	public Troop (Position pos, Player p, Direction dir) { 
	    super(pos);
	    this._moveQueue = new ArrayList<>(); 
        this._currentMove = new ArrayList<>();
        this.solid=false;
        this._dir = dir;
        this._aiming = false;
        this._player = p;
        _player.addTroops(this);
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
	public void CalcNewMove(Position dest) {
	    List<int[]> Dirs = Arrays.asList(
	        new int[]{-1, 0}, new int[]{1, 0}, new int[]{0, -1}, new int[]{0, 1}
	    );

	    SolArray bestSol = new SolArray();
	    SolArray curSol = new SolArray();
	    Set<Position> marks = new HashSet<>();
	    StepCount bestSolSteps = new StepCount();
	    StepCount curSolSteps = new StepCount();
	    bestSolSteps.value = Integer.MAX_VALUE;  // Ensure it starts at max possible value

	    Map<Position, Integer> minSteps = new HashMap<>(); // Track shortest path per position

	    _backTrackPathFinding(dest, curSol, bestSol, marks, Dirs, curSolSteps, bestSolSteps, this.pos, minSteps);

	    this._currentMove = bestSol.Sol; // Store the best found solution
	}


	private void _backTrackPathFinding(Position dest, SolArray curSol,
            SolArray bestSol, Set<Position> marks,
            List<int[]> Dirs, StepCount curSolSteps,
            StepCount bestSolSteps, Position it, Map<Position, Integer> minSteps) {

    //System.out.println("Current Position: " + it.Y + " " + it.X + " Steps: " + curSolSteps.value);

    if (dest.equals(it)) {
        if (curSolSteps.value < bestSolSteps.value) {
            bestSolSteps.value = curSolSteps.value;
            bestSol.Sol = new ArrayList<>(curSol.Sol);
            //System.out.println("Found new best solution with steps: " + bestSolSteps.value);
        }
        return;
    }

    for (int[] curDir : Dirs) {
        Position movePos = new Position(it.X + curDir[0], it.Y + curDir[1]);

        //System.out.println("Trying move to: " + movePos.Y + " " + movePos.X);

        if (movePos.isValid() && Board.getInstance().getGameObject(movePos)==null &&  !marks.contains(movePos)) {

            // Heuristic pruning: if this path is already worse, skip it
            if (minSteps.containsKey(movePos) && minSteps.get(movePos) <= curSolSteps.value) {
                //System.out.println("Skipping move due to worse step count: " + movePos.Y + " " + movePos.X);
                continue;
            }
            minSteps.put(movePos, curSolSteps.value);

            curSolSteps.value++;
            marks.add(movePos);
            curSol.Sol.add(movePos);

            _backTrackPathFinding(dest, curSol, bestSol, marks, Dirs, curSolSteps, bestSolSteps, movePos, minSteps);

            curSol.Sol.remove(curSol.Sol.size() - 1);
            curSolSteps.value--;
            marks.remove(movePos);

            //System.out.println("Backtracking from: " + movePos.Y + " " + movePos.X);
        } else {
            //System.out.println("Skipping move to: " + movePos.Y + " " + movePos.X);
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
				if (_player.getDanger(getPos())) {
					die();					
				}
				else {
					_player.update();					
				}
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
		
		if (!isAlive()) {
			return visiblePositions;
		}
		visiblePositions.add(getPos());
		Position pos = new Position(getPos().getX(), getPos().getY());
		
		
		for (int i = 0; i < _visionRange; i++) {
			pos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
			if (!pos.isValid() || Board.getInstance().isSolid(pos))
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
		
		Position visPos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
		for (int i = 0; i < _visionRange; i++) {		// TODO: maybe change vision range
			if (visPos.isValid() && !Board.getInstance().isSolid(visPos)) {
				dangerPositions.add(visPos);
				visPos = new Position(visPos.getX() + _dir.getX(), visPos.getY() + _dir.getY());
			} else break;
			
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
	
	public String getPlayer() {
		return this._player.getId();
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

