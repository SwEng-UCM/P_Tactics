package PTactics.Utils;

import java.util.Objects;

public final  class Position {
	private final int X;
	private final int Y;
	public static int _gameLength; //both assigned at the start of game creation.
	public static int _gameWidth;
	
	public Position (int X, int Y) {
		if(X < 0 || Y < 0) throw new IllegalArgumentException("Coordinates have to be valid.");
		this.X=X;
		this.Y=Y;
	}
	public Boolean isValid() 
	{
		//Position always knows if it is valid
		return !(X < 0 || Y < 0 || X>=_gameWidth||Y>=_gameLength) ;
	}
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	 public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        Position position = (Position) obj;
	        return X == position.X && Y == position.Y;
	  }
	 public int hashCode() {
	        return Objects.hash(X, Y);
	    }
}
