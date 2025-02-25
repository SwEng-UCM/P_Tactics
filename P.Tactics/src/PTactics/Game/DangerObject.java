package PTactics.Game;

import PTactics.Utils.Position;

public interface DangerObject {
	public boolean isInDanger(Position pos);
	public String getId();
}
