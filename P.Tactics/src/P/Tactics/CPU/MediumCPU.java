package P.Tactics.CPU;

import java.util.List;
import java.util.Random;

import PTactics.control.ControllerInterface;
import PTactics.control.commands.AimCommand;
import PTactics.control.commands.MoveCommand;
import PTactics.control.commands.SelectTroopCommand;
import PTactics.model.game.Board;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;

public class MediumCPU extends CPUinterface {
	public MediumCPU(ControllerInterface ci) {
		super(ci);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ComputeTurn(Player p) {
//		for(Troop t: p.getTroops()) {
//			if(t.isAlive()) 
//			{
//				SelectTroopCommand s=new SelectTroopCommand(t.getPos().getX(),t.getPos().getY());
//				s.execute(ci);
//				List<Position> enemyPositions= ci.getEnemyTroopsPos();
//				for(Position enemyPos:enemyPositions) 
//				{
//					boolean moved=false;
//					List<Position> killDistancePositions=Board.getInstance().shootablePositions(enemyPos,t.getShootRange() );
//					for(Position killPos: killDistancePositions) 
//					{
//						if(ci.canMove(killPos)) 
//						{
//							MoveCommand move= new MoveCommand(killPos.getX(), killPos.getY());
//							move.execute(ci);
//							moved=true;
//							break;
//						}
//					}
//					if(moved) 
//					{
//						break;
//					}
//				}
//				AimCommand aim = new AimCommand(this.RandomAim());
//				aim.execute(ci);
//			}
//		}
//		ci.nextTurn();
	}
}
