package common.src.main;

import org.jspace.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	
	public static final String LOCK = "lock";
	public static final String HOST = "host";
	public static final String LEFT_ROOM = "left_room";  // used to signal when the player is out of the room
	public static final String PARTICIPANT = "participant";

	public static void main(String[] args) throws InterruptedException {

		SpaceRepository repo = new SpaceRepository();
		// Users request to join room, server sends rooms
		Space lobby = new SequentialSpace();
		
<<<<<<< HEAD
		//String ngrokURI = "tcp://2.tcp.eu.ngrok.io:17657/";
=======
		//String host = "tcp://0.tcp.ngrok.io:12226/";
>>>>>>> branch 'master' of https://github.com/gernsoe/02148_Game_Repo.git
		String host = "tcp://127.0.0.1:9001/";
		String repoURI = host + "?keep";
		
		repo.add("lobby", lobby);
		repo.addGate(repoURI);
		System.out.println("Opened gate at uri " + repoURI);
		
		// Used to keep track of active rooms (server side only)
		Space rooms = new SequentialSpace(); 	// (roomID, roomCounter, playerCount)
		rooms.put(LOCK);						// Add lock for mutual exclusion
		String roomURI;
		int roomCounter = 0;		// Used to identify rooms
		
		new Thread(new leaveRoomHandler(rooms)).start();
        
		while(true) {
			int playerCount = 1;	// Players in a specific room
			System.out.println("Waiting for client to enter...");
			
			// Get client info, when someone wants to enter a room
			Object[] credentials = lobby.get(new ActualField("enter"), new FormalField(String.class), new FormalField(String.class));
			String who = (String) credentials[1];
			String roomID = (String) credentials[2];
			
			System.out.println(who + " wants to join room: " + roomID);
			
			rooms.get(new ActualField(LOCK)); // Get mutual exclusion
			Object[] getRoom = rooms.getp(new ActualField(roomID), new FormalField(Integer.class), new FormalField(Integer.class)); // (roomID, roomCounter, playerCount)
			
			// Decide what room to put the new player in.
			if (getRoom != null) {
				playerCount = (int) getRoom[2];
				if (playerCount >= 2) {
					System.out.println("Room is full. Please enter new roomID");
					lobby.put("roomURI", who, roomID, "");	// Sending empty uri back, so client knows the room was full
				} else {
					// Join room	
					//roomURI = ngrokURI + "game" + (int) getRoom[1] + "?keep";   // fx. tcp://127.0.0.1:9001/game0?keep
					roomURI = host + "game" + (int) getRoom[1] + "?keep";   // fx. tcp://127.0.0.1:9001/game0?keep
					System.out.println("Sending user: " + who + " to game room: " + roomCounter);
		            lobby.put("roomURI", who, roomID, roomURI);  
		            playerCount++;
				}  
				// Put the updated room back
				rooms.put(roomID, (int) getRoom[1], playerCount);
			} else {
				// Create thread to take care of the new game room
				System.out.println("Creating new room with ID: " + roomID + " for: " + who);
				//roomURI = ngrokURI + "game" + roomCounter + "?keep";   // fx. tcp://127.0.0.1:9001/game0?keep
				roomURI = host + "game" + roomCounter + "?keep";   // fx. tcp://127.0.0.1:9001/game0?keep
				rooms.put(roomID, roomCounter, playerCount);
                new Thread(new roomHandler(roomID, roomCounter, roomURI, repo, rooms)).start();
                roomCounter++;
                
                // Join room
                System.out.println("Sending user: " + who + " to game room: " + roomCounter);
                lobby.put("roomURI", who, roomID, roomURI);
			}
			rooms.put(LOCK);	// Release mutual exclusion
		}
	}
}




class roomHandler implements Runnable {
    private String roomID;
	private String roomURI;
	private Space gameRoom;
	private Space rooms;
	private boolean connected = true;
	private boolean inLobby = true;
	private String player1;
	private String player2;
	private boolean hostDied = false;
	private boolean participantDied = false;
	
	public static final String LEAVE_ROOM = "leave_room";	// used to signal that a player wants to leave a room
	public static final String LEFT_ROOM = "left_room"; 	// used to signal when the player is out of the room
    public static final String READY_TO_PLAY = "ready_to_play";
    public static final String SETTINGS = "settings";
    public static final String PLAYER_JOINED = "player_joined";
	public static final String START_GAME = "start_game";
	public static final String GAME_STARTED = "game_started";
	public static final String PERMISSION = "permission";
	public static final String LOBBY_INSTRUCTION = "lobby_instruction";
	public static final String TO = "to";
	public static final String FROM = "from";
	public static final String HOST = "host";
	public static final String PARTICIPANT = "participant";
	public static final String LOCK = "lock";
    public static final String PLAYER = "player";
    public static final String PLAYERSHOOT = "playershoot";
    public static final String BUBBLES = "bubbles";
    public static final String GOTMAP = "gotmap";
    public static final String STARTMAP = "startmap";
    public static final String RESTART_GAME = "restart_game";
	public static final String PLAYER_HIT_TO_SERVER = "player_hit_to_server";
	public static final String PLAYER_DEAD = "player_dead";
	public static final String GO_TO_END_SCREEN = "go_to_end_screen";
	public static final String SINGLEPLAYER = "singlerplayer";

	public roomHandler(String roomID, int roomCounter, String roomURI, SpaceRepository repo, Space rooms) {
        this.roomID = roomID;
		this.roomURI = roomURI;
		this.rooms = rooms;
        
        gameRoom = new SequentialSpace(); 
        repo.add("game" + roomCounter, gameRoom);
	}
	

    
	public void run() {
		
		// Pre game lobby
		try {
			player1 = (String) gameRoom.get(new ActualField(FROM), new FormalField(String.class), new ActualField(READY_TO_PLAY))[1];
			System.out.println(player1 + " is ready to play!!!");
			
			gameRoom.put(TO, player1, PERMISSION, HOST);

			while (inLobby) {
				// Get instruction (name, instruction) - where an instruction is either ready, start or leave room
				Object[] waitingRoomInstruction = gameRoom.get(new ActualField(FROM), new FormalField(String.class), new FormalField(String.class));
				String who = (String) waitingRoomInstruction[1];
				String singlePlayerInstruction = (String) waitingRoomInstruction[2];

				switch (singlePlayerInstruction) {
					case READY_TO_PLAY:	// Player 2 joined
						player2 = who;
						System.out.println(player2 + " is ready to play!!!");
	
						// Signal to player1 that someone joined
						gameRoom.put(TO, HOST, PLAYER_JOINED, player2, 0, "", 0);	// last 3 are only used to match the template in checkWaitingRoomInstruction
						gameRoom.put(TO, player2, PERMISSION, PARTICIPANT); // Tell player 2 that he's participant
						gameRoom.put(TO, PARTICIPANT, PLAYER_JOINED, player1);
						Object[] start_settings_inst = gameRoom.get(new ActualField(FROM), new FormalField(String.class), new FormalField(String.class));
						String fromWho = (String) start_settings_inst[1];
						String multiPlayerInstruction = (String) start_settings_inst[2];
						
						switch (fromWho) {
							case HOST:
								switch (multiPlayerInstruction) {
									case START_GAME:	
										System.out.println("Starting the game");
										gameRoom.put(TO, HOST, GAME_STARTED, "", 0, "", 0);			//Player one - last 4 are only used to match the template in checkWaitingRoomInstruction
										gameRoom.put(TO, PARTICIPANT, GAME_STARTED, "", 0, "", 0); 	//Player two -  -||-
										inLobby = false;
										break;
									
									case SETTINGS:
										// Implement settings
									System.out.println("Implement settings");
										break;
		
									case LEAVE_ROOM:	// Host leaves room when it's full
										gameRoom.put(TO, HOST, LEAVE_ROOM, HOST, 0, "", 0);
										gameRoom.put(TO, PARTICIPANT, LEAVE_ROOM, HOST, 0, "", 0);
										gameRoom.get(new ActualField(FROM), new ActualField(PARTICIPANT), new ActualField(LEFT_ROOM));
										gameRoom.get(new ActualField(FROM), new ActualField(HOST), new ActualField(LEFT_ROOM));
										rooms.put(roomID, LEFT_ROOM, PARTICIPANT);
										rooms.put(roomID, LEFT_ROOM, HOST);
										Thread.interrupted();										
										break;
		
									default:
										System.out.println("Invalid command");
										break;
								}
								break;
							
							case PARTICIPANT:	// Participant leave room
								gameRoom.put(TO, HOST, LEAVE_ROOM, PARTICIPANT, 0, "", 0);
								gameRoom.put(TO, PARTICIPANT, LEAVE_ROOM, PARTICIPANT, 0, "", 0);
								gameRoom.get(new ActualField(FROM), new ActualField(PARTICIPANT), new ActualField(LEFT_ROOM));
								rooms.put(roomID, LEFT_ROOM, PARTICIPANT);
								break;
								
							default:
								System.out.println("Not a valid user");
								break;
						}
						
						break;
					case START_GAME:	// When host is alone
						gameRoom.put(TO, HOST, GAME_STARTED, "" , 0, "", 0);
						System.out.println("Starting the game");
						inLobby = false;

						break;
					case LEAVE_ROOM:	// Leave room when host is alone
						gameRoom.put(TO, HOST, LEAVE_ROOM, HOST, 0, "", 0);
						gameRoom.get(new ActualField(FROM), new ActualField(HOST), new ActualField(LEFT_ROOM));
						rooms.put(roomID, LEFT_ROOM, HOST);
						Thread.interrupted();
						break;

					default:
						System.out.println("Invalid command");
						break;
				}
			}

			// Game loop
			while (connected) {
				// System.out.println("Entered game loop");

				Object[] gameInstruction = gameRoom.get(new ActualField(FROM), new FormalField(String.class), new FormalField(String.class));
				String who = (String) gameInstruction[1];
				String instruction = (String) gameInstruction[2];

				switch (instruction) {
					case GOTMAP:
						gameRoom.put(TO, HOST, STARTMAP);
						gameRoom.put(TO, PARTICIPANT, STARTMAP);
						break;
					case PLAYER_DEAD:
						if (who.equals(HOST)) {
							hostDied = true;
						} else if (who.equals(PARTICIPANT)) {
							participantDied = true;
						}
						break;
					default:
					case SINGLEPLAYER:
						System.out.print("SinglePlayer");
						Object[] gameInstruction2 = gameRoom.get(new ActualField(FROM), new FormalField(String.class), new FormalField(String.class));
						System.out.print("Instruction recerived");
						if (gameInstruction2 != null) {
							gameRoom.put(TO, HOST, GO_TO_END_SCREEN);
						}
						break;
				}

				if (hostDied && participantDied) {
					gameRoom.put(TO, HOST, GO_TO_END_SCREEN);
					gameRoom.put(TO, PARTICIPANT, GO_TO_END_SCREEN);
					connected = false;
					rooms.put(roomID, LEFT_ROOM, HOST); // Get the leaveHandler to delete the room
					Thread.interrupted();
				}
			}

		} catch (InterruptedException e) {
			System.out.println(e.getStackTrace());
		}
    }
}



class leaveRoomHandler implements Runnable {
	private Space rooms;

	public static final String LOCK = "lock";
	public static final String HOST = "host";
	public static final String LEFT_ROOM = "left_room"; 	// used to signal when the player is out of the room
	public static final String PARTICIPANT = "participant";

	public leaveRoomHandler(Space rooms) {
		this.rooms = rooms;
	}

	public void run() {
		try {
			while (true) {
				Object[] playerLeft = rooms.get(new FormalField(String.class), new ActualField(LEFT_ROOM), new FormalField(String.class)); // (roomID, LEFT_ROOM, participant/host)
				rooms.get(new ActualField(LOCK));

				// Delete the room 
				if (((String) playerLeft[2]).equals(HOST)) {
					rooms.getp(new ActualField((String) playerLeft[0]), new FormalField(Integer.class), new FormalField(Integer.class)); 
				} 

				// Decrement the player counter
				else if (((String) playerLeft[2]).equals(PARTICIPANT)) {
					Object[] roomToChange = rooms.getp(new ActualField((String) playerLeft[0]), new FormalField(Integer.class), new FormalField(Integer.class));
					int updatedPlayerCount = (int) roomToChange[2] - 1;
					rooms.put((String) roomToChange[0], (int) roomToChange[1], updatedPlayerCount);
				}
				rooms.put(LOCK);
			}
		} catch (InterruptedException e) {}
	}
}