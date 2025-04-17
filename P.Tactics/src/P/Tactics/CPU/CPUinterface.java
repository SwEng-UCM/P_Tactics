package P.Tactics.CPU;

import java.util.Random;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Player;
import PTactics.utils.Direction;

public abstract class  CPUinterface {
	protected ControllerInterface ci;
	public CPUinterface(ControllerInterface ci) 
	{
		this.ci=ci;
	}
	public abstract  void ComputeTurn(Player p);
	protected Direction RandomAim() 
	{
		Random random= new Random();
		int dirRand= random.nextInt(3)+1; //between 1 and 4
		Direction dirtoAim = Direction.NONE;
		switch (dirRand) {
		case 1:
			dirtoAim= Direction.UP;
			break;
		case 2:
			dirtoAim= Direction.DOWN;
			break;
		case 3:
			dirtoAim= Direction.LEFT;
			break;
		case 4:
			dirtoAim= Direction.RIGHT;
			break;
	    };
	    return dirtoAim;
	}
}
