 package common.src.main;

import org.jspace.*;

	/*
	 *  Server checks that the name is valid (not empty, unique and not too long) 
	 *  and creates a new room, if the roomID doesn't exist already (Lobby/butler method)
	 * 
	 *  user.("enter", name, roomID) -> server.credentials(name, roomID)
	 *  if roomExists@server then
	 * 		server.("roomURI", name, roomID, roomURI) -> user.response("roomURI", name, roomID, roomURI)
	 *  else
	 * 		server.(createRoom(name, roomID, roomURI)) -> user.response("roomURI", name, roomID, roomURI)
	 *   
	 *  User puts movement/controls into repo. -> 
	 *  Server gets user controls and updated positions etc. ->
	 *  Server puts updated player/ball/arrow positions and collision info. -> 
	 *  User gets position and collision info.
	 *   
	 */ 

public class Server {
	
	public static void main(String[] args) throws InterruptedException {
		SpaceRepository repo = new SpaceRepository();
		
		// Users request to join room, server sends rooms
		Space lobby = new SequentialSpace();
		
		String host = "tcp://127.0.0.1:9001/";
		String repoURI = host + "?keep";
		
		repo.add("lobby", lobby);
		repo.addGate(repoURI);
		System.out.println("Opened gate at uri " + repoURI);
		
		// Used to keep track of active rooms (server side only)
		Space rooms = new SequentialSpace();
		String roomURI;
		
        int roomCounter = 0;		// Used to identify rooms
        
		while(true) {
			int playerCount = 1;	// Players in a specific room
			System.out.println("Waiting for client to enter...");
			
			// Get client info, when someone wants to enter a room
			Object[] credentials = lobby.get(new ActualField("enter"), new FormalField(String.class), new FormalField(String.class));
			String who = (String) credentials[1];
			String roomID = (String) credentials[2];
			
			System.out.println(who + " wants to join room: " + roomID);
			
			// Get room (non-blocking) and determine if a new room should be made
			Object[] getRoom = rooms.getp(new ActualField(roomID), new FormalField(Integer.class), 
					new FormalField(Integer.class));
			
			if (getRoom != null) {			// Room was found
				playerCount = (int) getRoom[2];
				if (playerCount >= 2) { 	// Room is full
					System.out.println("Room is full. Please enter new roomID");
					// Sending empty uri back, so client knows the room was full
					lobby.put("roomURI",who, roomID, "");	
				} else {
					// Join room	
					roomURI = host + "game" + (int) getRoom[1] + "?keep";   // fx. tcp://127.0.0.1:9001/game0?keep
					System.out.println("Sending user: " + who + " to game room: " + roomCounter);
		            lobby.put("roomURI", who, roomID, roomURI);  
		            playerCount++;
				}  
				// Put the updated room back
				rooms.put(roomID, (int) getRoom[1], playerCount);
			} else {
				System.out.println("Creating new room with ID: " + roomID + " for: " + who);
				roomURI = host + "game" + roomCounter + "?keep";        // fx. tcp://127.0.0.1:9001/game0?keep
				rooms.put(roomID, roomCounter, playerCount);
				// Create thread to take care of the new game room
                new Thread(new roomHandler(roomID, roomCounter, roomURI, repo)).start();
                roomCounter++;
                
                // Join room
                System.out.println("Sending user: " + who + " to game room: " + roomCounter);
                lobby.put("roomURI", who, roomID, roomURI);
            }
		}
	}
}

class roomHandler implements Runnable {
    private String roomID;
	private String roomURI;
	private Space gameRoom;
	
	public static final String LEAVE_ROOM = "leave_room";
    public static final String READY_TO_PLAY = "ready_to_play";
    public static final String SETTINGS = "settings";
    public static final String PLAYER_JOINED = "player_joined";
	public static final String START_GAME = "start_game";
	public static final String GAME_STARTED = "game_started";
	public static final String PERMISSION = "permission";
	public static final String LOBBY_INSTRUCTION = "lobby_instruction";
	public static final String TO = "to";
	public static final String FROM = "from";

	public roomHandler(String roomID, int roomCounter, String roomURI, SpaceRepository repo) {
        this.roomID = roomID;
        this.roomURI = roomURI;
        
        gameRoom = new SequentialSpace(); 
        repo.add("game" + roomCounter, gameRoom);
    }
    
	public void run() {
		
		// Pre game lobby
		try {
			String user1 = (String) gameRoom.get(new ActualField(FROM), new FormalField(String.class), new ActualField(READY_TO_PLAY))[1];
			System.out.println(user1 + " is ready to play!!!");
			
			gameRoom.put(TO, user1, PERMISSION, "host");
			boolean connected = true;
			boolean inLobby = true;

			while (inLobby) {
				// Get instruction (name, instruction) - where an instruction is either ready or start
				Object[] initGameInstruction = gameRoom.get(new ActualField(FROM), new FormalField(String.class), new FormalField(String.class));
				String who = (String) initGameInstruction[1];
				String instruction = (String) initGameInstruction[2];

				// Player 2 joins
				if (instruction.equals(READY_TO_PLAY) && !who.equals(user1)) {
					System.out.println(who + " is ready to play!!!");

					// Signal to player1 that someone joined
					gameRoom.put(TO, user1, PLAYER_JOINED);
					gameRoom.put(TO, who, PERMISSION, "participant");
					String start_settings_inst = (String) gameRoom.get(new ActualField(FROM), new ActualField(user1), new FormalField(String.class))[2];

					// Host starts game / wants to see settings
					if (start_settings_inst.equals(START_GAME)) {
						System.out.println("Starting the game");
						gameRoom.put(TO, user1, GAME_STARTED);	//Player one
						gameRoom.put(TO, who, GAME_STARTED); 	//Player two'
						inLobby = false;
					} else if (start_settings_inst.equals(SETTINGS)) {
						// Implement settings
						System.out.println("Implement settings");
					} else {
						System.out.println("No valid command found");
					}

				// Player 1 starts game alone
				} else if (instruction.equals(START_GAME)) {
					gameRoom.put(TO, user1, GAME_STARTED);
					System.out.println("Starting the game");
					inLobby = false;
				} else {
					System.out.println("Invalid command.");
				}
			}

			// Game loop
			while (connected) {
				System.out.println("Entered game loop");
			}

			
		} catch (InterruptedException e) {
			System.out.println(e.getStackTrace());
		}
    }
}