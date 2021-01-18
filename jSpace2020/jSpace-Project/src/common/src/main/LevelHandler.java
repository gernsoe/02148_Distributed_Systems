package common.src.main;

import java.awt.Color;
import java.util.ArrayList;

public class LevelHandler {
	
	Map game;
	String player1, player2;
	int timer, level, bWidth, bHeight, pHeight;
	double speedX, speedY;
	int[] bubbleCounts;
	int[] bubbleSizes = new int[] {20,30,45,68,80,100,130,160,200,270}; 
	
	
	public LevelHandler(int level, int bWidth, int bHeight, String player1, String player2, int pHeight, int hearts, int scores) {
		this.level = level;
		this.bWidth = bWidth;
		this.bHeight = bHeight;
		this.player1 = player1;
		this.player2 = player2;
		this.pHeight = pHeight;
		
		makeLevel(level, hearts, scores);
	}

	public void makeLevel(int level, int hearts, int scores) {
		// Default speed
		speedX = 1;
		speedY = 0;
		if (level == 1) {
			bubbleCounts = new int[] {0,1,0,0,0,0,0,0,0,0};
		} else if (level == 2) {
			bubbleCounts = new int[] {0,0,1,0,0,0,0,0,0,0};
		} else if (level == 3) {
			bubbleCounts = new int[] {0,2,1,0,0,0,0,0,0,0};
		} else if (level == 4) {
			bubbleCounts = new int[] {0,0,0,1,0,0,0,0,0,0};
		} else if (level == 5) {
			bubbleCounts = new int[] {10,0,0,0,0,0,0,0,0,0};
			speedX = 2;
		} else if (level == 6) {
			bubbleCounts = new int[] {0,0,2,0,1,0,0,0,0,0};
		} else if (level == 7) {
			bubbleCounts = new int[] {0,0,0,0,0,0,1,0,0,0};
		} else if (level == 8) {
			bubbleCounts = new int[] {0,0,0,0,0,0,0,0,0,1};
		} else {
			bubbleCounts = new int[] {(int)(Math.round(Math.random()*4)),(int)(Math.round(Math.random()*2)),(int)(Math.round(Math.random()*1)),(int)(Math.round(Math.random()*1)),(int)(Math.round(Math.random()*1)),(int)(Math.round(Math.random()*1)),(int)(Math.round(Math.random()*1)),(int)(Math.round(Math.random()*1)),(int)(Math.round(Math.random()*1)),(int)(Math.round(Math.random()*1))};
		}
		
		game = new Map(bWidth, bHeight, bubbleCounts, bubbleSizes, player1, player2, pHeight, speedX, speedY, hearts, scores);
	}
	
	public Player getPlayer1() {
		return game.getPlayer1();
	}
	
	public Player getPlayer2() {
		return game.getPlayer2();
	}
	
	public ArrayList<Bubble> getBubbles() {
		return game.getBubbles();
	}
	
	public LevelHandler getCurrentLevel() {
		return this;
	}
	
	
	public Color getLevelColor(int i) {
		i = i%2;
		Color lightCyan = new Color(135, 206, 250);
		Color[] colors = new Color[] {Color.white,lightCyan};
		return colors[i];
	}
}
