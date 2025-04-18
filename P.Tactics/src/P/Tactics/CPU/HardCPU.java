package P.Tactics.CPU;

import java.util.List;

import PTactics.control.ControllerInterface;
import PTactics.control.commands.AimCommand;
import PTactics.control.commands.MoveCommand;
import PTactics.control.commands.SelectTroopCommand;
import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Position;

public class HardCPU extends CPUinterface {

	public HardCPU(ControllerInterface ci) {
		super(ci);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ComputeTurn(Player p) {
		for(Troop t: p.getTroops()) {
			if(t.isAlive()) 
			{
				SelectTroopCommand s=new SelectTroopCommand(t.getPos().getX(),t.getPos().getY());
				s.execute(ci);
				List<Position> enemyPositions= ci.getGame().getEnemyTroops();
				for(Position enemyPos:enemyPositions) 
				{
					boolean moved=false;
					List<Position> killDistancePositions=Board.getInstance().shootablePositions(enemyPos,t.getShootRange() );
					for(Position killPos: killDistancePositions) 
					{
						if(ci.canMove(killPos)) 
						{
							MoveCommand move= new MoveCommand(killPos.getX(), killPos.getY());
							move.execute(ci);
							moved=true;
							break;
						}
					}
					AimCommand aim = new AimCommand(this.posToDir(enemyPos, t.getPos()));
					aim.execute(ci);
					if(moved) 
					{
						break;
					}
				}
			}
		}
		ci.nextTurn();
	}

}
