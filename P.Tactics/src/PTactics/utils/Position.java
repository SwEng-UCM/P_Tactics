package PTactics.utils;

import java.util.Objects;

public final  class Position {
	private final int X;
	private final int Y;
	public static int _gameLength; //both assigned at the start of game creation.
	public static int _gameWidth;
	
	public Position (int X, int Y) {

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
	 
	 @Override
	 public String toString() {	
		 StringBuilder data = new StringBuilder();
		 return data.append(String.valueOf(this.X)).append(",").append(String.valueOf(this.Y)).toString(); 
	 }
}
