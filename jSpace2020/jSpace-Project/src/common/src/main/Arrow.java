package common.src.main;

import java.awt.geom.*;

public class Arrow {
	
	// Default settings for arrow
	int speed = 6;
	Point arrow;
	int width = 2, height = 0;
	boolean alive;
	
	public Arrow(Point arrowPos, int playerHeight) {
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
		//System.out.println(height);
		//System.out.println(arrow.toString());
		if (arrow.getY() > -1) {
			height += speed;
			arrow.setY(arrow.getY() - speed);
		} else {
			alive = false;
		}
	}
	
	public Point getArrowPos() {
		return arrow;
	}
	
	public void setAliveTo(boolean alive) {
		this.alive = alive;
	}
	
	public Rectangle2D getShape() {
		return new Rectangle2D.Double(getX(), getY(), getArrowWidth(), getArrowHeight());
	}
	
	public boolean isAlive() {
		return alive;
	}
 }
