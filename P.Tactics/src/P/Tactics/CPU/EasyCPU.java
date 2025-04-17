package P.Tactics.CPU;

import java.util.Random;

import PTactics.control.ControllerInterface;
import PTactics.control.commands.AbilityCommand;
import PTactics.control.commands.AimCommand;
import PTactics.control.commands.MoveCommand;
import PTactics.control.commands.SelectTroopCommand;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Direction;
import PTactics.utils.Position;

public class EasyCPU extends CPUinterface{

	public EasyCPU(ControllerInterface ci) {
		super(ci);
	}

	@Override
	public void ComputeTurn(Player p) {
		Random random = new Random();
		for(Troop t: p.getTroops()) 
		{
			SelectTroopCommand s=new SelectTroopCommand(t.getPos().getX(),t.getPos().getY());
			s.execute(ci);
			int randomX= random.nextInt(Position._gameWidth);
			int randomY= random.nextInt(Position._gameLength);
			while(!ci.canMove(new Position(randomX,randomY))) 
			{
				randomX= random.nextInt(Position._gameWidth);
				randomY= random.nextInt(Position._gameLength);
			}
			MoveCommand move= new MoveCommand(randomX, randomY);
			move.execute(ci);
			randomX= random.nextInt(Position._gameWidth);
			randomY= random.nextInt(Position._gameLength);
			Position abilityPos= new Position(randomX,randomY);
			AbilityCommand ability= new AbilityCommand(randomX,randomY);
			ability.execute(ci);
			AimCommand aim = new AimCommand(this.RandomAim());
			aim.execute(ci);
		}
		ci.nextTurn();
	}

}
