package common.src.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	
	private int bubbleCount, playerCount = 2, playerHeight = 20;
	// Changed later to ball/bubbles class
	private float r, g, b;
	ArrayList<Bubble> bubbles;
	Arrow arrow;
	Player players[] = new Player[playerCount];

	public Map (int borderWidth, int borderHeight, int bubbleCount, String playerName1, String playerName2, int playerHeight) {
		
		// Add players and spawn players and bubbles
		players[0] = new Player(new Point(borderWidth/2,borderHeight-playerHeight),borderWidth, playerName1, playerHeight);
		players[1] = new Player(new Point(borderWidth/2,borderHeight-playerHeight),borderWidth, playerName2, playerHeight);
		
		Random rand = new Random();

		// Add bubbles
		bubbles = new ArrayList<Bubble>();
		for (int i = 0; i < bubbleCount; i++) {
			r = rand.nextFloat();
			g = rand.nextFloat();
			b = rand.nextFloat();
			if (i % 2 == 0) {
				bubbles.add(new Bubble((rand.nextInt(90)+10), new Color(r,g,b), new Point((rand.nextInt(750)),(rand.nextInt(300))), borderHeight, borderWidth, 1, 0));
			} else {
				bubbles.add(new Bubble((rand.nextInt(90)+10), new Color(r,g,b), new Point((rand.nextInt(750)),(rand.nextInt(300))), borderHeight, borderWidth, -1, 0));
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
	
	public ArrayList<Bubble> getBubbles() {
		return bubbles;
	}
}
