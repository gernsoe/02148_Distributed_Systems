package common.src.main;

public class Arrow {
	
	// Default settings for arrow
	int speed = 6;
	Point arrow;
	int width = 0, height = 0;
	boolean alive;
	
	public Arrow(Point arrowPos, int playerHeight) {
		width = playerHeight/4;
		arrow = arrowPos;
		alive = true;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setArrowWidth(int width) {
		this.width = width;
	}
	
	public int getArrowHeight() {
		return height;
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
		System.out.println(height);
		if (arrow.getY() > -1) {
			height += speed;
			arrow.setY(arrow.getY() - speed);
		} else {
			alive = false;
		}
	}
	
	public void setAliveTo(boolean alive) {
		this.alive = alive;
	}
	
	public boolean isAlive() {
		return alive;
	}
 }
