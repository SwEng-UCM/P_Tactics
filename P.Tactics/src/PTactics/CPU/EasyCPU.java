package PTactics.CPU;

import java.util.Random;
import PTactics.control.ControllerInterface;
import PTactics.control.commands.AbilityCommand;
import PTactics.control.commands.AimCommand;
import PTactics.control.commands.MoveCommand;
import PTactics.control.commands.SelectTroopCommand;
import PTactics.model.game.Player;
import PTactics.model.gameObjects.Troop;
import PTactics.utils.Position;

public class EasyCPU extends CPUinterface {

	public EasyCPU(ControllerInterface ci) {
		super(ci);
	}

	@Override
	public void ComputeTurn(Player p) {
		Random random = new Random();
		for (Troop t : p.getTroops()) {
			if (t.isAlive()) {
				SelectTroopCommand s = new SelectTroopCommand(t.getPos().getX(), t.getPos().getY());
				s.execute(_ci);
				int randomX;
				int randomY;
				Position oldPos = t.getPos();
				while (t.getPos().equals(oldPos)) {
					randomX = random.nextInt(Position._gameWidth);
					randomY = random.nextInt(Position._gameLength);
					try {
						if (_ci.canMove(new Position(randomX, randomY))) {
							MoveCommand move = new MoveCommand(randomX, randomY);
							move.executeCPU(_ci);
						}
					} catch (UnsupportedOperationException e) {
						e.printStackTrace();
						break;
					}
				}
				randomX = random.nextInt(Position._gameWidth);
				randomY = random.nextInt(Position._gameLength);
				AbilityCommand ability = new AbilityCommand(randomX, randomY);
				ability.execute(_ci);
				AimCommand aim = new AimCommand(this.RandomAim());
				aim.execute(_ci);
			}
		}
		_ci.nextTurn();
	}

}
