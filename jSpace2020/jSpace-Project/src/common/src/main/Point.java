package common.src.main;

public class Point {
	private double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object object) {
		Point other = (Point) object;
		if (this == object) {
			return true;
		}
		// If they truly are identical
		else if (this.getClass() != object.getClass()) {
			return false;
		}
		// If the type of class are not the same, return false
		else if (this.x != other.x || this.y != other.y) {
			return false;
		}
		// If the x or y-coordinates are not equal
		else {
			return true;
		}
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}	
}