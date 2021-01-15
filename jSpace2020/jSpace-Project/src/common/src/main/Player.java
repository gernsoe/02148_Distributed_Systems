package common.src.main;

public class Player {
	// Check if player is alive
	boolean isAlive = true;
	
	// Player position
	Point player;
	Arrow arrow;
	
	// Size of player
	final int playerHeight = 12, playerWidth = 6, playerHalfWidth = playerWidth/2;
	int playerID, borderWidth; 
	int stepSize = 5;
	String playerName;
	
	// borderHeight is not
	public Player(Point playerPos, int borderWidth, String playerName) {
		this.player = playerPos;
		this.playerName = playerName;
		this.borderWidth = borderWidth;
	}
	
	// Player movement and collision with wall
	public void goRight() {
		if (player.getX() + (stepSize + playerHalfWidth) <= borderWidth & isAlive) {
			System.out.println("Going right");
			player.setX(player.getX() + stepSize);
		}
	}
	
	public void goLeft() {
		if (player.getX() - (stepSize + playerHalfWidth) >= 0 & isAlive) {
			player.setX(player.getX() - stepSize);
			System.out.println("Going left");
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
	public void makeArrow() {
		if (!getArrowIsAlive()) {
			arrow = new Arrow (new Point(player.getX(), player.getY() - playerHeight));
		}
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
	
	public Arrow getArrow() {
		return arrow;
	}
 }