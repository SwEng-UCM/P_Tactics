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
		for(Troop t: p.getTroops()) {
			if(t.isAlive()) 
			{
				SelectTroopCommand s=new SelectTroopCommand(t.getPos().getX(),t.getPos().getY());
				s.execute(_ci);
				List<Position> enemyPositions= _ci.getEnemyTroops();
				boolean killdistance=false;
				for(Position enemyPos:enemyPositions) 
				{
					boolean moved=false;
					List<Position> killDistancePositions=Board.getInstance().shootablePositions(enemyPos,t.getShootRange() );
					for(Position killPos: killDistancePositions) 
					{
						try 
						{
							if(_ci.canMove(killPos)) 
							{
								Position oldPos=t.getPos();
								MoveCommand move= new MoveCommand(killPos.getX(), killPos.getY());
								move.executeCPU(_ci);
								if(!t.getPos().equals(oldPos)) 
								{
									moved=true;
									killdistance=true;// moved to killdistance position 
									break;
								}
							}
						}
						catch(UnsupportedOperationException e) 
						{
							e.printStackTrace();
							break;
						}
					}
					if(moved) 
					{
						break;
					}
				}
				if(!killdistance) 
				{
					//random move as position to make a kill not found
					Random random = new Random();
					int randomX;
					int randomY;
					Position oldPos=t.getPos();
					while(t.getPos().equals(oldPos))
					{
						randomX= random.nextInt(Position._gameWidth);
						randomY= random.nextInt(Position._gameLength);
						try 
						{
							if(_ci.canMove(new  Position(randomX,randomY))) 
							{
								MoveCommand move= new MoveCommand(randomX,randomY);
								move.executeCPU(_ci);
							}
						}
						catch(UnsupportedOperationException e) 
						{
							e.printStackTrace();
							break;
						}
						catch(Exception e) 
						{
							e.printStackTrace();
						}
					}
				}
				AimCommand aim = new AimCommand(this.RandomAim());
				aim.execute(_ci);
			}
		}
		_ci.nextTurn();
	}
}
