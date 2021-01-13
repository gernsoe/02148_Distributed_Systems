package common.src.main;

public class Map {
	
	int bubbleCount, playerCount;
	// Changed later to ball/bubbles class
	int bubbles[]; 
	Arrow arrow;
	Player players[] = new Player[2];

	public Map (int borderWidth, int borderHeight, int bubbleCount, String playerName1, String playerName2) {
		this.players = players;
		this.bubbleCount = bubbleCount;
		
		bubbles = new int[bubbleCount];
		
		// Add players and spawn players and bubbles
		players[0] = new Player(new Point(borderWidth/2,0),borderWidth, playerName1);
		players[1] = new Player(new Point(borderWidth/2,0),borderWidth, playerName2);
		
		
	}
	
	// Make a function to remove balls from the array, if player is dead, remove player from array
	
}
