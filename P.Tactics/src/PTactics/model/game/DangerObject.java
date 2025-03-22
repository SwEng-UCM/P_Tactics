package PTactics.model.game;

import PTactics.utils.Position;

public interface DangerObject {
	public boolean isInDanger(Position pos);
	public String getId();
}
