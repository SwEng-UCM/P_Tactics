package PTactics.control;

import java.util.ArrayList;
import java.util.List;

import P.Tactics.CPU.CPUinterface;
import P.Tactics.CPU.EasyCPU;
import P.Tactics.CPU.HardCPU;
import P.Tactics.CPU.MediumCPU;
import PTactics.control.maps.MapSelector;
import PTactics.model.game.DangerMediator;
import PTactics.model.game.Game;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.view.GameObserver;

public abstract class Controller implements ControllerInterface {
	protected Game _game;
	protected boolean _endTurn;
	public static int mapSelected = 1;
	protected int _numPlayers = 0;
	private List<String> _playerNames = new ArrayList<>();
	protected CPUinterface _cpuInterface;
	
	public Controller() {
		_game = new Game();
		_endTurn = false;
	}
	
	public void setPlayerNum(int playerNum) {
		this._numPlayers = playerNum;
	}

	public void setPlayerNames(List<String> names) {
		_playerNames = names;
	}
	
	public List<String> getPlayerNames(){
		return _playerNames;
	}
	
	public String getCurrentPlayerName() {
		int idxPlayer = _game.getNumPlayer() - 1;
		return _playerNames.get(idxPlayer);
		
	}
	
	public void setUpPlayerVsCPU(String playerName, int levelCPU) {
		DangerMediator dangerMediator = new DangerMediator();
		
		// for a real player
		Player realPlayer = new Player(playerName, dangerMediator);
		for (Troop troop : MapSelector.getTroops(realPlayer)) {
			_game.addNewElement(troop, troop.getPos());		// assign troops
		}
		_game.addPlayer(realPlayer);
		
		// for the CPU player
		Player cpuPlayer = new Player("CPU", dangerMediator);
		for (Troop troop : MapSelector.getTroops(cpuPlayer)) {
			_game.addNewElement(troop, troop.getPos());
		}
		_game.addPlayer(cpuPlayer);
		
		// level difficulty
		switch(levelCPU) {
		case 0:
			_cpuInterface = new EasyCPU(this);
			break;
		case 1:
			_cpuInterface = new MediumCPU(this);
			break;
		case 2:
			_cpuInterface = new HardCPU(this);
			break;
		}
		
		_game.inicialize();
		/*
		if(_numPlayers == 2) {
			_cpuInterface.ComputeTurn(_game.getPlayer());
		}*/
	}
	
	// In principle, we do like player 0 turn --> check if player 1 has alive
	// troops...
	public boolean isFinish() {
		for (Troop t : _game.getPlayer().getTroops()) {
			if (t.isAlive())
				return false;
		}
		return true;
	}

	public void setupPlayers() {
		boolean playersSetUp = false;

		if (!playersSetUp) {
			DangerMediator dangerMediator = new DangerMediator();
			for (Integer i = 1; i <= _numPlayers; ++i) {
				Player p = new Player(i.toString(), dangerMediator);
				for (Troop t : MapSelector.getTroops(p)) {
					_game.addNewElement(t, t.getPos());
				}
				_game.addPlayer(p);
			}
			_game.inicialize();
			playersSetUp = true;
		}
	}

	@Override
	public void endTurn() {
		_endTurn = true;
	}

	public void nextTurn() {
		_game.dropTroop();
		_game.nextTurn();
	}

	@Override
	public void setTroop(Troop t) {
		_game.setTroop(t);
	}

	@Override
	public void update() {
		this._game.update();
	}

	public void updatePlayers() {
		this._game.updatePlayers();
	}

	@Override
	public int getNumPlayer() {
		return _game.getNumPlayer();
	}

	public void addObserver(GameObserver o) {
		this._game.addObserver(o);
	}

	@Override
	public void selectTroop(Position pos) throws Exception {
		_game.selectTroop(pos);
	}

	public boolean isTroopSelected() {
		return _game.isTroopSelected();
	}

	public boolean canMove(Position pos) {
		return _game.canMove(pos);
	}

	public void moveTroop(Position pos) throws IllegalArgumentException {
		_game.moveTroop(pos);
	}

	public void troopAbility(Position pos) throws Exception {
		_game.troopAbility(pos);
	}

	public void takeAim(Direction _dirToAim) {
		_game.takeAim(_dirToAim);
	}

	public Boolean isTroop(Position pos) {
		return this._game.isTroop(pos);
	}

	public Game getGame() {
		return this._game;
	}

	public Troop currTroop() {
		return _game.currentTroop();
	}

	public boolean dangerTile(Position pos) {
		return _game.dangerTile(pos);
	}

	@Override
	public List<Position> getPath(Position pos) {
		return _game.getPath(pos);
	}

	public List<Position> hoverPath(Position pos) {
		return _game.hoverPath(pos);
	}
}
