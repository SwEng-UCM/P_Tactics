package PTactics.utils;

public enum Direction {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), NONE(0,0);
	
	private int posX;
	private int posY;
	
	private Direction(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
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
