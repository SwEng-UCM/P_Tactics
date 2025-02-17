package PTactics.Utils;

import java.util.Objects;

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
