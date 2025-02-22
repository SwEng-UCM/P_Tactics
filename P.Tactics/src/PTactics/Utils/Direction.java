package PTactics.Utils;

public enum Direction {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), NONE(0,0);
	
	int _x;
	int _y;
	
	private Direction(int x, int y) {
		_x = x;
		_y = y;
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
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
	
	public static Direction toDir(String direction) {
		if(direction == "UP") {
			return Direction.UP;
		}
		else if(direction == "DOWN") {
			return Direction.DOWN;
		}
		else if(direction == "LEFT") {
			return Direction.LEFT;
		}
		else if(direction == "RIGHT") {
			return Direction.RIGHT;
		}
		return Direction.NONE;
	}
}
