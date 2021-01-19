package common.src.main;

import java.awt.geom.Rectangle2D;

public class Player {
	// Check if player is alive
	boolean isAlive = true;
	boolean isInvincible = false;
	
	// Player position
	Point player;
	Arrow arrow;
	int hearts, scores;
	
	// Size of player
	private int playerHeight, playerID, borderWidth, stepSize = 2, playerWidth;
	String playerName;
	
	// borderHeight is not
	public Player(Point player, int borderWidth, String playerName, int playerHeight, int hearts, int scores) {
		this.playerHeight = playerHeight;
		this.scores = scores;
		this.hearts = hearts;
		playerWidth = (playerHeight/3)*2;
		this.player = player;
		this.playerName = playerName;
		this.borderWidth = borderWidth;
	}
	
	// Player movement and collision with wall
	public void goRight() {
		if (player.getX() + (stepSize + playerWidth) <= borderWidth & isAlive) {
			// System.out.println("Going right at " + player.toString());
			player.setX(player.getX() + stepSize);
		}
	}
	
	public void goLeft() {
		if (player.getX() >= 0 & isAlive) {
			player.setX(player.getX() - stepSize);
			// System.out.println("Going left at " + player.toString());
		}
	}
	
	// Get arrow point based on players position
	public void makeArrow() {
		if (!getArrowIsAlive()) {
			arrow = new Arrow (new Point(player.getX()+playerWidth/2, player.getY()+playerHeight), playerHeight);
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
	
	public void setInvincibility(boolean invicibility) {
		isInvincible = invicibility;
	}
	
	public boolean getInvicibilityStatus() {
		return isInvincible;
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
	
	public int getStepSize() {
		return stepSize;
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
	
	public void loseHeart() {
		if (isAlive) {
			hearts--;
		}
		if (hearts == 0) {
			setAlive(false);
		}
	}
	
	public void setScore(int score) {
		scores = score;
	}
	
	public int getScore() {
		return scores;
	}
	
	public int getHearts() {
		return hearts;
	}
	
	public Arrow getArrow() {
		return arrow;
	}
 }