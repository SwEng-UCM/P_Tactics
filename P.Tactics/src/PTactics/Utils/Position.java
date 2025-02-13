package PTactics.Utils;


public final  class Position {
	public final int X;
	public final int Y;
	
	public Position (int X, int Y) {
		if(X < 0 || Y < 0) throw new IllegalArgumentException("Coordinates have to be valid.");
		this.X=X;
		this.Y=Y;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public boolean equals(Position other) {
		return other.X==this.X && other.Y==this.Y;
	}
}
