package common.src.main;

import java.awt.geom.Rectangle2D;

public class Player {
	// Check if player is alive
	boolean isAlive = true;
	
	// Player position
	Point player;
	Arrow arrow;
	
	// Size of player
	private int playerHeight, playerID, borderWidth, stepSize, playerWidth;
	String playerName;
	
	// borderHeight is not
	public Player(Point playerPos, int borderWidth, String playerName, int playerHeight) {
		this.playerHeight = playerHeight;
		stepSize = playerHeight/2;
		playerWidth = stepSize;
		this.player = playerPos;
		this.playerName = playerName;
		this.borderWidth = borderWidth;
	}
	
	// Player movement and collision with wall
	public void goRight() {
		if (player.getX() + (stepSize + playerWidth) <= borderWidth & isAlive) {
			System.out.println("Going right at " + player.toString());
			player.setX(player.getX() + stepSize);
		}
	}
	
	public void goLeft() {
		if (player.getX() - playerWidth >= 0 & isAlive) {
			player.setX(player.getX() - stepSize);
			System.out.println("Going left at " + player.toString());
		}
	}
	
	/*// Player collision with bubble
	public boolean checkCollisionWith(Point bubble, int bubbleSize) {
		
		// Find where the contact point is for player
		Point contactPoint = findContactPoint(bubble);
		
		// Now check if there's a collision
		int bubbleRadius = bubbleSize/2;
		
		double distX = bubble.getX()-contactPoint.getX();
		double distY = bubble.getY()-contactPoint.getY();
		double distance = Math.sqrt((distX*distX) + (distY*distY));
		
		// true = collision
		if (distance <= bubbleRadius) {
			isAlive = false;
			return true;
		} else {
			return false;
		}
	}
	
	public Point findContactPoint(Point bubble) {
		double playerContactPointX; 
		// Check left side
		if (bubble.getX() < (player.getX() - playerWidth)) {
			playerContactPointX = player.getX();
		} else { // If not left side, then right side
			playerContactPointX = player.getX() + playerWidth;
		}
		return new Point (playerContactPointX, playerHeight);
	}*/
	
	// Get arrow point based on players position
	public void makeArrow() {
		if (!getArrowIsAlive()) {
			arrow = new Arrow (new Point(player.getX()+stepSize/4, player.getY()+playerHeight), playerHeight);
		}
	}
	
	public Rectangle2D getShape() {
		return new Rectangle2D.Double(getX(), getY(), playerWidth, playerHeight);
	}
	
	public boolean getArrowIsAlive() {
		if (arrow != null) {
			if (arrow.isAlive()) {
				return true;
			}
		} 
		return false;
	}

	public double getX() {
		return player.getX();
	}
	
	public double getY() {
		return player.getY();
	}
	
	public void setX(double x) {
		player.setX(x);
	}
	
	public void setY(double y) {
		player.setY(y);
	}
	
	public Point getPos() {
		return player;
	}
	
	public int getPlayerHeight() {
		return playerHeight;
	}
	
	public int getPlayerWidth() {
		return playerWidth;
	}

	public int getID() {
		return playerID;
	}
	
	public String getName() {
		return playerName;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean live) {
		isAlive = live;
	}
	
	public Arrow getArrow() {
		return arrow;
	}
 }