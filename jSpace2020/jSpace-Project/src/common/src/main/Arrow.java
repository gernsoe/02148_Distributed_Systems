package common.src.main;

public class Arrow {
	
	// Default settings for arrow
	int speed = 10;
	Point arrow;
	int width = 4;
	int borderWidth;
	
	public Arrow(Point arrowPos, int borderWidth) {
		arrow = arrowPos;
		this.borderWidth = borderWidth;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setArrowWidth(int width) {
		this.width = width;
	}
	
	public void updatePos() {
		if (arrow.getY() > -1) {
			arrow.setY(arrow.getY() - speed);
		}
	}
}
