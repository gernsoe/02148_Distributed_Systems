package common.src.main;

public class LevelHandler {
	
	Map game;
	int timer;
	int[] bubbleCounts, bubbleSizes; 
	
	
	public LevelHandler(int level, int bWidth, int bHeight, String player1, String player2, int pHeight) {
		
		if (level == 1) {
			bubbleCounts = new int[] {1,2};
			bubbleSizes = new int[] {1,2};
			
			// game = new Map(bWidth, bHeight, bubbleCounts, bubbleSizes, player1, player2, pHeight);
		}
		
	}
	
	public void level1() {
		
	}
	
	public void setTime(int time) {
		timer = time;
	}
	
	public int getTime() {
		return timer;
	}
}
