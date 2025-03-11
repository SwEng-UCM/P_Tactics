package PTactics.GameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import PTactics.Game.BoardInterface;
import PTactics.Game.Player;
import PTactics.Utils.Direction;
import PTactics.Utils.Position;
import PTactics.Utils.Utils;

public class Smoker extends Troop {

	public Smoker(Position pos, Player p, BoardInterface BI) {
		super(pos, p, BI);
		this._visionRange=10;
		this._abilityUses=3;
		this._moveRange=5;
		this._movesLeft=this._moveRange;
	}

	@Override
	public void activateAbility() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivateAbility() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		return "S";
	}
}
