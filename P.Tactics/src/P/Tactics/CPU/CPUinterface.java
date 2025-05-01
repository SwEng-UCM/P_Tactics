package P.Tactics.CPU;

import java.util.Random;

import PTactics.control.ControllerInterface;
import PTactics.model.game.Player;
import PTactics.utils.Direction;
import PTactics.utils.Position;

public abstract class  CPUinterface {
	protected ControllerInterface _ci;
	public CPUinterface(ControllerInterface ci) 
	{
		this._ci=ci;
	}
	public abstract  void ComputeTurn(Player p);
	public ControllerInterface GetCI() 
	{
		return this._ci;
	}
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
	protected  double manhattanDistance(double x1, double y1, double x2, double y2) {
	    return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}
	protected Direction posToDir(Position destination, Position target) {
    	int X = destination.getX() - target.getX(); // given pos minus troop position (setting 0,0 at troop)
    	int Y = destination.getY() - target.getY();
    	int abx = Math.abs(X); // see which axis is more prominent
    	int aby = Math.abs(Y);
    	if(abx < aby) {
    		return Y < 0? Direction.UP : Direction.DOWN;
    	}
    	else {
    		return X < 0? Direction.LEFT : Direction.RIGHT;
    	}
    	
    }
}
