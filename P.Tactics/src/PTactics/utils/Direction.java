package PTactics.utils;

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
		if(direction.equals("UP") || direction.equals("U")) {
			return Direction.UP;
		}
		else if(direction.equals("DOWN") || direction.equals("D")) {
			return Direction.DOWN;
		}
		else if(direction.equals("LEFT")  || direction.equals("L")) {
			return Direction.LEFT;
		}
		else if(direction.equals("RIGHT") || direction.equals("R")) {
			return Direction.RIGHT;
		}
		return Direction.NONE;
	}
}
