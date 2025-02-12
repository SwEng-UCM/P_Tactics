package PTactics.Utils;


public final  class Position {
	public final int X;
	public final int Y;
	public Position (int X, int Y) 
	{
		this.X=X;
		this.Y=Y;
	}
	public boolean equals(Position other) 
	{
		return other.X==this.X && other.Y==this.Y;
	}
}
