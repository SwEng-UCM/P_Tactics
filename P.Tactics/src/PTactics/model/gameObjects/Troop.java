package PTactics.model.gameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import org.json.JSONObject;

import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GUI.Icons;

public abstract class Troop extends GameObject{
	List<Position> _moveQueue;
	List<Position> _currentMove;
	protected Direction _dir;
	protected boolean _aiming;
	protected Player _player;
	protected int _visionRange;//init in children contructor
	protected int _shootRange;//init in children contructor
	protected int _moveRange; //same
	protected int _movesLeft; //same
	protected int _abilityUses;// for implementing limited number of ability uses
	protected boolean _abilityActive; // true while using ability
	
	public Troop (Position pos, Player p) { // all GO constructors changed to include the board // children must initialize move range
		super(pos);
	    this._moveQueue = new ArrayList<>();  // Initialize the lists
        this._currentMove = new ArrayList<>();
        this.solid = true; //this should be true now
        this._dir = Direction.DOWN;
        this._aiming = false;
        this._player = p;
        this._abilityActive = false;
        this.seeThrough= true;
        this.alive=true;
        _player.addTroops(this);
	}
	
	public Troop (Position pos, Player p, Direction dir) { 
	    super(pos);
	    this._moveQueue = new ArrayList<>(); 
        this._currentMove = new ArrayList<>();
        this.solid=true; //this should be true now (I hope)
        this._dir = dir;
        this._aiming = false;
        this._player = p;
        this._abilityActive = false;
        _player.addTroops(this);
	}
	
	public JSONObject report() {
		JSONObject troopReport = super.report();
		troopReport.put("Player", this._player.getId());
		troopReport.put("Direction", this._dir.toString());
		troopReport.put("MoveLeft", this._movesLeft);
		troopReport.put("Abilities", this._abilityUses);
		return troopReport;
	}
	
	public Direction getDir() {
		return this._dir;
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
        Position movePos = new Position(it.getX() + curDir[0], it.getY() + curDir[1]);

        //System.out.println("Trying move to: " + movePos.Y + " " + movePos.X);

        if (movePos.isValid() && (Board.getInstance().getGameObject(movePos)==null || !Board.getInstance().getGameObject(movePos).isSolid()) &&  !marks.contains(movePos)) {

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

	public void Move () throws IllegalArgumentException
	{
		// player has a function getDanger(Position pos) that returns if a troop is in 
		// in danger when stepping in the tile, should be called in each step.
			if(!_currentMove.isEmpty()) 
			{			
				this.setPosition(this._currentMove.getFirst());
				this._currentMove.removeFirst();
				this._movesLeft--;
				/*if (_player.getDanger(getPos())) {
					onHit();					
				}*/
				_player.update();									
			}
			else if(active && !this._moveQueue.isEmpty())
			{
				CalcNewMove(_moveQueue.getFirst());
				_moveQueue.removeFirst();
				
				if( this._movesLeft < this._currentMove.size()) { // pa salir del paso
					this._currentMove.clear();
					throw new IllegalArgumentException("Not enough moves left");
				}
				
				if (!_currentMove.isEmpty()) {					
					this.setPosition(this._currentMove.getFirst());
					this._currentMove.removeFirst();
					this._movesLeft--;
				}
				/*if (_player.getDanger(getPos())) {
					onHit();					
				}*/
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
	
	@Override
	public ImageIcon toIcon() {
		if(_dir == Direction.UP) {
			return Icons.TroopIcons.TROOP_FACING_UP;
		}
		else if(_dir == Direction.DOWN) {
			return Icons.TroopIcons.TROOP_FACING_DOWN;
		}
		else if(_dir == Direction.LEFT) {
			return Icons.TroopIcons.TROOP_FACING_LEFT;
		}
		else if(_dir == Direction.RIGHT) {
			return Icons.TroopIcons.TROOP_FACING_RIGHT;
		}
		
		return Icons.TroopIcons.TROOP_FACING_UP;
	}
	
	@Override // TODO: i need this // the move to be boolean and return true if there are moves left in the queue so I can paint it step by step
	public void update() {
		if (_player.isMyTurn()) {
			Move();
			if (_player.getDanger(getPos())) {
				onHit();
			}			
		}
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
			if (!pos.isValid() || !Board.getInstance().isSeeThrough(pos)) {
				if (!Board.getInstance().isSolid(pos)) {
					visiblePositions.add(pos);
				}
				break;					
			}
			visiblePositions.add(pos);
		}
		
		return visiblePositions;	
	}
	
	public void addPlayer(Player p) { // is this for debug? (dm Arturo your answer)(or a feet pic)
		_player = p;
	}

	
	public List<Position> dangerPositions() {
		List<Position> dangerPositions = new ArrayList<>();
		
		if (!_aiming) {
			return dangerPositions;
		}
		
		Position visPos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
		for (int i = 0; i < _shootRange; i++) {		// TODO: maybe change vision range
			if (visPos.isValid() && !Board.getInstance().isSolid(visPos)) {
				dangerPositions.add(visPos);
				if (!Board.getInstance().isSeeThrough(visPos)) break;
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
	
	public String getPlayerID() {
		return this._player.getId();
	}
	public Boolean isTurn() 
	{
		return this._player.isMyTurn();
	}
	@Override
	public boolean isAlive() {
		return active;
	}
	@Override
	public void onHit() {
		active = false;
		_currentMove.clear();
		//Board.getInstance().eraseFromGO(this);
	}
	public int getMovesLeft() {
		return this._movesLeft;
	}
	public void activateAbility() {
		_abilityActive = true;
	}; 
	
	public abstract void deactivateAbility();
	public boolean isAbility() {
		return this._abilityActive;
	}// true if using ability
	public int abilityUsesLeft() {
		return _abilityUses;
	}

	public void setDirection(Direction dir) {
		_dir = dir;
	}

	public List<Position> getPath(Position pos) {
		return _currentMove;
	}
	
	public List<Position> hoverPath(Position dest) {
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

		    return bestSol.Sol.size() <= _movesLeft ? bestSol.Sol : null; // Store the best found solution
	}
	
	
	public void nextTurn() {
		this._movesLeft = this._moveRange; // resets move range and abilities
		this._abilityUses = 1;

	}
	public int getShootRange() 
	{
		return this._shootRange;
	}

	public abstract void undoAbility(Position _abilityPos);
	
	public void revive() { 
		this.alive = true;
	}
	
	public void setMovesLeft(int ml) {
		if(ml > this._moveRange) {
			throw new IllegalArgumentException("The new moves left cannot be larger than moveRange");
		}
		this._movesLeft = ml;
	}
}

