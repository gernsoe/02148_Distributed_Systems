package common.src.main;

import java.awt.Color;
import java.util.ArrayList;

import common.src.main.GUI.src.WinBuilder.endScreen;

public class LevelHandler {
	
	Map game;
	String player1, player2;
	int timer, level, bWidth, bHeight, pHeight;
	int[] bubbleCounts;
	int[] bubbleSizes = new int[] {20,30,45,68,80,100,130,160,200,270}; 
	
	
	public LevelHandler(int bWidth, int bHeight, String player1, String player2, int pHeight) {
		game = new Map(bWidth, bHeight);
		this.bWidth = bWidth;
		this.player1 = player1;
		this.player2 = player2;
		this.bHeight = bHeight;
		this.pHeight = pHeight;
	}

	public void makeLevel(int level, int hearts1, int scores1, int hearts2, int scores2) {
		// Default speed
		double speedX = 1;
		double speedY = 0;
		if (level == 1) {
			bubbleCounts = new int[] {1,0,0,0,0,0,0,0,0,0};
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
			bubbleCounts = new int[] {0,0,0,0,2,1,0,0,0,0};
		} else if (level == 8) {
			bubbleCounts = new int[] {0,0,0,0,0,0,2,0,0,0};
		} else if (level == 9) {
			bubbleCounts = new int[] {0,10,0,2,0,0,0,0,0,0};
		} else if (level == 10) {
			bubbleCounts = new int[] {100,100,100,100,100,100,100,100,100,100};
		} else {
			bubbleCounts = new int[] {0,0,0,0,0,0,0,0,0,10};
		}

		game.makeMap(bubbleCounts, bubbleSizes, speedX, speedY);
		game.makePlayers(player1, player2, pHeight, hearts1, scores1, hearts2, scores2);
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
	
	public Map getMap() {
		return game.getMap();
	}
	
	public LevelHandler getCurrentLevel() {
		return this;
	}
	
	
	public Color getLevelColor(int i) {
		i = i%2;
		Color lightCyan = new Color(135, 206, 250);
		Color[] colors = new Color[] {Color.LIGHT_GRAY,lightCyan};
		return colors[i];
	}
	
	public void joinLevel(int level, int hearts1, int scores1, int hearts2, int scores2, ArrayList<Bubble> bubbles) {
		game.addPlayers(player1, player2, pHeight, hearts1, scores1, hearts2, scores2);
		game.setListOfBubbles(bubbles);
	}
}
