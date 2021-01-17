package common.src.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	
	private int playerCount = 1, borderHeight, borderWidth;
	double speedX, speedY;
	// Changed later to ball/bubbles class
	private float r, g, b;
	ArrayList<Bubble> bubbles;
	Arrow arrow;
	Player players[] = new Player[playerCount];

	public Map (int borderWidth, int borderHeight, int[] bubbleCounts, int[] bubbleSizes, String playerName1, String playerName2, int playerHeight, double speedX, double speedY, int hearts) {
		this.borderHeight = borderHeight;
		this.borderWidth = borderWidth;
		this.speedX = speedX;
		this.speedY = speedY;
		
		if (!playerName2.equals("")) {
			playerCount = 2;
			players[1] = new Player(new Point(borderWidth/2,borderHeight-playerHeight),borderWidth, playerName2, playerHeight, hearts);
		}
		
		
		// Add players and spawn players and bubbles
		players[0] = new Player(new Point(borderWidth/2,borderHeight-playerHeight),borderWidth, playerName1, playerHeight, hearts);
		
		
		
		// Add bubbles
		bubbles = new ArrayList<Bubble>();
		for (int i = 0; i < bubbleCounts.length; i++) {
			//r = rand.nextFloat();
			//g = rand.nextFloat();
			//b = rand.nextFloat();
			if (i % 2 == 0) {
				makeBubbles(i,bubbleSizes[i],bubbleCounts[i],bubbleSizes[i]);
			} else {
				makeBubbles(i,bubbleSizes[i],bubbleCounts[i],bubbleSizes[i]);
			}
		}
	}
	
	public void makeBubbles(int colorID, int size, int amount, int bubbleSize) {
		for (int i = 0; i < amount; i++) {
			int randomX = (int) (Math.random() * (borderWidth-bubbleSize));
			int randomY = (int) (Math.random() * (400+bubbleSize));
			if (i%2 == 0) {
				bubbles.add(new Bubble(size, getColor(colorID), new Point(randomX,randomY), borderHeight, borderWidth, speedX, speedY));
			} else {
				bubbles.add(new Bubble(size, getColor(colorID), new Point(randomX,randomY), borderHeight, borderWidth, -speedX, speedY));
			}
			
		}
	}
	
	public Color getColor(int i) {
		i = i%10;
		Color[] colors = new Color[] {Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.blue, Color.pink, Color.magenta, Color.DARK_GRAY, Color.black};
		return colors[i];
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
}
