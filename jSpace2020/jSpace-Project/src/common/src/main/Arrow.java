package common.src.main;

public class Arrow {
	
	// Default settings for arrow
	int speed = 2;
	Point arrow;
	int width = 4;
	boolean alive;
	
	public Arrow(Point arrowPos) {
		arrow = arrowPos;
		alive = true;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setArrowWidth(int width) {
		this.width = width;
	}
	
	public int getArrowWidth() {
		return width;
	}

	public double getX() {
		return arrow.getX();
	}

	public double getY() {
		return arrow.getY();
	}
	
	public void updatePos() {
		System.out.println(arrow.getY());
		if (arrow.getY() > -1) {
			arrow.setY(arrow.getY() - speed);
		} else {
			alive = false;
		}
	}
	
	public boolean isAlive() {
		return alive;
	}
 }
