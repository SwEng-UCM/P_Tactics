package PTactics.Utils;

public enum Direction {
	UP, DOWN, LEFT, RIGHT, NONE;
	
	@Override
	public String toString() {
		if(this == Direction.UP) {
			return "UP";
		}
		else if(this == Direction.DOWN) {
			return "DOWN";
		}
		else if(this == Direction.LEFT) {
			return "LEFT";
		}
		else if(this == Direction.NONE) {
			return "NONE";
		}
		return "RIGHT";
	}
}
