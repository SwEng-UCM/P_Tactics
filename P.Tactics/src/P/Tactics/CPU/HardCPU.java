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
				s.execute(_ci);
				List<Position> enemyPositions= _ci.getGame().getEnemyTroops();
				boolean killdistance=false;
				for(Position enemyPos:enemyPositions) 
				{
					boolean moved=false;
					List<Position> killDistancePositions=Board.getInstance().shootablePositions(enemyPos,t.getShootRange() );
					for(Position killPos: killDistancePositions) 
					{
						if(_ci.canMove(killPos)) 
						{
							try 
							{
								MoveCommand move= new MoveCommand(killPos.getX(), killPos.getY());
								move.executeCPU(_ci);
								moved=true;
								killdistance=true;// moved to killdistance position 
								break;
							}
							catch(Exception e) 
							{
								
							}
						}
					}
					AimCommand aim = new AimCommand(this.posToDir(enemyPos, t.getPos()));
					aim.execute(_ci);
					if(moved) 
					{
						break;
					}
				}
				/*if(!killdistance) 
				{
					//random move as position to make a kill not found
					Random random = new Random();
					int randomX= random.nextInt(Position._gameWidth);
					int randomY= random.nextInt(Position._gameLength);
					Position oldPos=t.getPos();
					while(t.getPos().equals(oldPos))
					{
						randomX= random.nextInt(Position._gameWidth);
						randomY= random.nextInt(Position._gameLength);
						MoveCommand move= new MoveCommand(randomX, randomY);
						move.execute(ci);
					}
				}*/
			}
		}
		_ci.nextTurn();
	}

}
