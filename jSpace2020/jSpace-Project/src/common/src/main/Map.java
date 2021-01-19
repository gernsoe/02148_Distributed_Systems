package common.src.main;

import java.util.ArrayList;

public class Map {
	
	private int playerCount = 2, borderHeight, borderWidth;
	double speedX, speedY;
	// Changed later to ball/bubbles class
	ArrayList<Bubble> bubbles;
	Arrow arrow;
	Player players[];

	public Map (int borderWidth, int borderHeight) {
		this.borderHeight = borderHeight;
		this.borderWidth = borderWidth;
	}
	
	public void makePlayers(String playerName1, String playerName2, int playerHeight, int hearts, int scores) {
		// Add players 
		if (!playerName2.equals("")) {
			playerCount = 2;
			players = new Player[playerCount];
			players[1] = new Player(new Point(borderWidth/2+playerHeight/3,borderHeight-playerHeight),borderWidth, playerName2, playerHeight, hearts, scores);
		}
		
		players[0] = new Player(new Point(borderWidth/2-playerHeight/3,borderHeight-playerHeight),borderWidth, playerName1, playerHeight, hearts, scores);
	}
	
	public void addPlayers(String playerName1, String playerName2, int playerHeight, int hearts, int scores) {
		playerCount = 2;
		players = new Player[playerCount];
		players[0] = new Player(new Point(borderWidth/2+playerHeight/3,borderHeight-playerHeight),borderWidth, playerName1, playerHeight, hearts, scores);
		players[1] = new Player(new Point(borderWidth/2-playerHeight/3,borderHeight-playerHeight),borderWidth, playerName1, playerHeight, hearts, scores);
	}
	
	public void makeMap(int bubbleCounts[], int bubbleSize[], double speedX, double speedY) {
		// Add bubbles
		bubbles = new ArrayList<Bubble>();
		for (int i = 0; i < bubbleCounts.length; i++) {
			//r = rand.nextFloat();
			//g = rand.nextFloat();
			//b = rand.nextFloat();
			if (i % 2 == 0) {
				makeBubbles(i,bubbleCounts[i],bubbleSize[i], speedX, speedY);
			} else {
				makeBubbles(i,bubbleCounts[i],bubbleSize[i], speedX, speedY);
			}
		}
	}
	
	public void makeBubbles(int colorID, int amount, int bubbleSize, double speedX, double speedY) {
		for (int i = 0; i < amount; i++) {
			int randomX = (int) (Math.random() * (borderWidth-bubbleSize));
			int randomY = (int) (Math.random() * (500-bubbleSize));
			if (i%2 == 0) {
				bubbles.add(new Bubble(bubbleSize, colorID, new Point(randomX,randomY), borderHeight, borderWidth, speedX, speedY));
			} else {
				bubbles.add(new Bubble(bubbleSize, colorID, new Point(randomX,randomY), borderHeight, borderWidth, -speedX, speedY));
			}
			
		}
	}

	// Make a function to remove balls from the array, if player is dead, remove player from array
	public Player getPlayer1() {
		return players[0];
	}
	
	public Player getPlayer2() {
		return players[1];
	}
	
	public Map getMap() {
		return this;
	}
	
	public ArrayList<Bubble> getBubbles() {
		return bubbles;
	}
	
    public void setListOfBubbles(ArrayList<Bubble> bubbles) {
    	this.bubbles = bubbles;
    }
}
