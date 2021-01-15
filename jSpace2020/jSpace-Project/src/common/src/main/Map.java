package common.src.main;

public class Map {
	
	int bubbleCount, playerCount;
	// Changed later to ball/bubbles class
	int bubbles[]; 
	Arrow arrow;
	Player players[] = new Player[2];

	public Map (int borderWidth, int borderHeight, int platFormHeight, int bubbleCount, String playerName1, String playerName2) {
		
		// Add players and spawn players and bubbles
		players[0] = new Player(new Point(borderWidth/2,platFormHeight),borderHeight, playerName1);
		players[1] = new Player(new Point(borderWidth/2,platFormHeight),borderHeight, playerName2);
		
		
	}
	
	// Make a function to remove balls from the array, if player is dead, remove player from array
	public Player getPlayer1() {
		return players[0];
	}
	
	public Player getPlayer2() {
		return players[1];
	}
}
