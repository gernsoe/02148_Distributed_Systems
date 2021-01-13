package common.src.main;

public class Player {
	// Check if player is alive
	boolean isAlive = true;
	
	// Player position
	Point player;
	
	// Size of player
	final int playerHeight = 5, playerHalfWidth = 1;
	int playerID, borderWidth; 
	int stepSize = 1;
	String playerName;
	
	// borderHeight is not
	public Player(Point playerPos, int borderWidth, String playerName) {
		this.player = playerPos;
		this.playerName = playerName;
	}
	
	// Player movement and collision with wall
	public void goRight(double x) {
		if (player.getX() + (stepSize + playerHalfWidth) <= borderWidth) {
			player.setX(x + stepSize);
		}
	}
	
	public void goLeft(double x) {
		if (player.getX() - (stepSize + playerHalfWidth) >= 0) {
			player.setX(x - stepSize);
		}
	}
	
	// Player collision with bubble
	public boolean checkCollisionWith(Point bubble, int bubbleSize) {
		
		// Find where the contact point is for player
		Point contactPoint = findContactPoint(bubble, player);
		
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
	
	public Point findContactPoint(Point bubble, Point player) {
		double playerContactPointX; 
		// Check left side
		if (bubble.getX() < (player.getX() - playerHalfWidth)) {
			playerContactPointX = player.getX() - playerHalfWidth;
		} else { // If not left side, then right side
			playerContactPointX = player.getX() - playerHalfWidth;
		}
		return new Point (playerContactPointX, playerHeight);
	}
	
	// Get arrow point based on players position
	public Point getArrowStartPoint() {
		return new Point (player.getX(), playerHeight);
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

	public int getID() {
		return playerID;
	}
	
	public String getName() {
		return playerName;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
 }