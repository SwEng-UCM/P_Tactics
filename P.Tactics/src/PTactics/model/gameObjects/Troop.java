package PTactics.model.gameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.swing.ImageIcon;

import org.json.JSONObject;

import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GUI.Icons;

public abstract class Troop extends GameObject {
	protected Movement currentMove;
	protected Direction _dir;
	protected boolean _aiming;
	protected Player _player;
	protected int _visionRange; // init in children contructor
	protected int _shootRange; // init in children contructor
	protected int _moveRange; // same
	protected int _movesLeft; // same
	protected int _abilityUses; // for implementing limited number of ability uses
	protected boolean _abilityActive; // true while using ability

	// ------------------ CONSTRUCTORS ------------------//
	public Troop(Position pos, Player p) {
		super(pos);
		this.solid = true;
		this._dir = Direction.DOWN;
		this._aiming = false;
		this._player = p;
		this._abilityActive = false;
		this.seeThrough = true;
		this.alive = true;
		_player.addTroops(this);
	}

	public Troop(Position pos, Player p, Direction dir) {
		super(pos);
		this.solid = true;
		this._dir = dir;
		this._aiming = false;
		this._player = p;
		this._abilityActive = false;
		_player.addTroops(this);
	}

	// ------------------ MOVE FUNCTIONS AND CLASSES ------------------//

	@Override
	public void startMove(Position pos) {
		currentMove = new Movement(pos);
	}
	
	public List<Position> getCurrentPath() {
		return currentMove != null ? currentMove._currentMove : null;
	}
	
	public List<Position> hoverPath(Position pos){
		return new Movement().hoverPath(pos);
	}
	
	protected class Movement {
		private static final List<int[]> dirs = Arrays.asList(new int[] { -1, 0 }, new int[] { 1, 0 },
				new int[] { 0, -1 }, new int[] { 0, 1 });
		private List<Position> _moveQueue;
		private List<Position> _currentMove;

		// Values for calculation
		private List<Position> bestSol;
		private List<Position> curSol;
		private Set<Position> marks;
		private int bestSolSteps;
		private int curSolSteps;
		
		public Movement() {
			bestSol = new ArrayList<Position>();
			curSol = new ArrayList<Position>();
			_moveQueue = new ArrayList<>();
			_currentMove = new ArrayList<>();
			marks = new HashSet<>();
			bestSolSteps = Integer.MAX_VALUE;
			curSolSteps = 0;
		}
		
		public Movement(Position dest) {
			bestSol = new ArrayList<Position>();
			curSol = new ArrayList<Position>();
			_moveQueue = new ArrayList<>();
			_currentMove = new ArrayList<>();
			marks = new HashSet<>();
			bestSolSteps = Integer.MAX_VALUE;
			curSolSteps = 0;
			_moveQueue.add(dest);
		}

		public List<Position> hoverPath(Position dest) {
			Map<Position, Integer> minSteps = new HashMap<>();

			findPath(dest, getPos(), minSteps);

			return bestSol.size() <= _movesLeft ? bestSol : null;
		}

		public void calcNewMove(Position dest) {
			Map<Position, Integer> minSteps = new HashMap<>();

			findPath(dest, getPos(), minSteps);

			_currentMove = bestSol;
		}

		private void findPath(Position dest, Position it, Map<Position, Integer> minSteps) {

			if (dest.equals(it)) {
				if (curSolSteps < bestSolSteps) {
					bestSolSteps = curSolSteps;
					bestSol = new ArrayList<>(curSol);
				}
			} else {
				for (int[] curDir : dirs) {
					Position movePos = new Position(it.getX() + curDir[0], it.getY() + curDir[1]);

					if (movePos.isValid()
							&& (Board.getInstance().getGameObject(movePos) == null
									|| !Board.getInstance().getGameObject(movePos).isSolid())
							&& !marks.contains(movePos)) {

						if (minSteps.containsKey(movePos) && minSteps.get(movePos) <= curSolSteps) {
							continue;
						}
						minSteps.put(movePos, curSolSteps);

						curSolSteps++;
						marks.add(movePos);
						curSol.add(movePos);

						findPath(dest, movePos,minSteps);

						curSol.remove(curSol.size() - 1);
						curSolSteps--;
						marks.remove(movePos);
					}
				}
			}
		}

		public void move() throws IllegalArgumentException, UnsupportedOperationException {
			if (!_currentMove.isEmpty()) {
				setPosition(_currentMove.getFirst());
				_currentMove.removeFirst();
				_movesLeft--;
				_player.update();
			} else if (active && !_moveQueue.isEmpty()) {
				calcNewMove(_moveQueue.getFirst());
				_moveQueue.removeFirst();

				if (_currentMove.size() == 0) {
					_currentMove.clear();
					throw new UnsupportedOperationException("troop unable to move to position");
				} else if (_movesLeft < _currentMove.size()) {
					_currentMove.clear();
					throw new IllegalArgumentException("Not enough moves left");
				}

				if (!_currentMove.isEmpty()) {
					setPosition(_currentMove.getFirst());
					_currentMove.removeFirst();
					_movesLeft--;
				}
			}
		}
	}

	// UPDATE AND POSITIONS //

	@Override
	public void update() {
		if (_player.isMyTurn()) {
			if(!Objects.isNull(currentMove)) {
				currentMove.move();
			}
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

	public List<Position> dangerPositions() {
		List<Position> dangerPositions = new ArrayList<>();

		if (!_aiming) {
			return dangerPositions;
		}

		Position visPos = new Position(pos.getX() + _dir.getX(), pos.getY() + _dir.getY());
		for (int i = 0; i < _shootRange; i++) {
			if (visPos.isValid() && !Board.getInstance().isSolid(visPos)) {
				dangerPositions.add(visPos);
				if (!Board.getInstance().isSeeThrough(visPos))
					break;
				visPos = new Position(visPos.getX() + _dir.getX(), visPos.getY() + _dir.getY());
			} else
				break;
		}

		return dangerPositions;
	}

	// ------------------ GETTERS ------------------//
	public String getPlayerID() {
		return this._player.getId();
	}

	public Boolean isTurn() {
		return this._player.isMyTurn();
	}

	@Override
	public boolean isAlive() {
		return active;
	}

	public int getMovesLeft() {
		return this._movesLeft;
	}

	public boolean isAbility() {
		return this._abilityActive;
	}

	public int abilityUsesLeft() {
		return _abilityUses;
	}

	public Direction getDir() {
		return this._dir;
	}

	public int getShootRange() {
		return this._shootRange;
	}

	// ------------------ SETTERS ------------------//
	public abstract void activateAbility(Position pos);

	public void revive() {
		this.alive = true;
	}
	
	public void undoAbility(Position _abilityPos) {
		_abilityUses++;
	}
	
	public void setMovesLeft(int ml) {
		if (ml > this._moveRange) {
			throw new IllegalArgumentException("The new moves left cannot be larger than moveRange");
		}
		this._movesLeft = ml;
	}

	public void takeAim(Direction direction) {
		_dir = direction;
		_aiming = true;
	}

	@Override
	public void onHit() {
		active = false;
		if(currentMove != null) currentMove._currentMove.clear();
	}

	// ------------------ ABSTRACT METHODS --------------//
	public abstract void nextTurn();

	public abstract void deactivateAbility();

	// ------------------ TO STRING ------------------//
	@Override
	public String toString() {
		if (_dir == Direction.UP) {
			return Utils.TroopUtils.TROOP_FACING_UP;
		} else if (_dir == Direction.DOWN) {
			return Utils.TroopUtils.TROOP_FACING_DOWN;
		} else if (_dir == Direction.LEFT) {
			return Utils.TroopUtils.TROOP_FACING_LEFT;
		} else if (_dir == Direction.RIGHT) {
			return Utils.TroopUtils.TROOP_FACING_RIGHT;
		}
		return Utils.TroopUtils.TROOP_ICON;
	}

	// ------------------ TO ICON ------------------//
	@Override
	public ImageIcon toIcon() {
		if (_dir == Direction.UP) {
			return Icons.TroopIcons.TROOP_FACING_UP;
		} else if (_dir == Direction.DOWN) {
			return Icons.TroopIcons.TROOP_FACING_DOWN;
		} else if (_dir == Direction.LEFT) {
			return Icons.TroopIcons.TROOP_FACING_LEFT;
		} else if (_dir == Direction.RIGHT) {
			return Icons.TroopIcons.TROOP_FACING_RIGHT;
		}
		return Icons.TroopIcons.TROOP_FACING_UP;
	}

	// ------------------ REPORT ------------------//
	@Override
	public JSONObject report() {
		JSONObject troopReport = super.report();
		troopReport.put("Player", this._player.getId());
		troopReport.put("Direction", this._dir.toString());
		troopReport.put("MoveLeft", this._movesLeft);
		troopReport.put("Aim", this._aiming);
		troopReport.put("Abilities", this._abilityUses);
		return troopReport;
	}
}
