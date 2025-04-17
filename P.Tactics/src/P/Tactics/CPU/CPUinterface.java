package P.Tactics.CPU;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Player;

public abstract class  CPUinterface {
	protected ControllerInterface ci;
	public CPUinterface(ControllerInterface ci) 
	{
		this.ci=ci;
	}
	public abstract  void ComputeTurn(Player p);
}
