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
		
		Space lobby = new SequentialSpace();
		
		host = "tcp://127.0.0.1:9001/";
		repoURI = host + "?keep";
		
		repo.add("lobby", lobby);
		repo.addGate(repoURI);
		System.out.println("Opened gate at uri " + repoURI);
		
		Space rooms = new SequentialSpace();
			
		private int roomCounter = 0;
		
		String roomURI;
		
		while(true) {
			System.out.println("Waiting for client to enter...");
			
			Object[] credentials = lobby.get(new ActualField("enter"), new FormalField(String.class), new FormalField(String.class));
			String who = credentials[1];
			String roomID = credentials[2];
			
			System.out.println(who + " wants to join room: " + roomID);
			
			Object[] getRoom = rooms.queryp(new ActualField("roomURI"), new FormalField(String.class), 
										  new ActualField(roomID), new FormalField(String.class));
			if (getRoom != null) {
				roomURI = getRoom[3];
			} else {
				System.out.println("Creating new room with ID: " + roomID + " for: " + who);
				
				roomURI = host + roomCounter + "?keep"
						
				rooms.put("roomURI", who, roomID, roomURI);
				new Thread(new roomHandler(roomID, roomURI)).start();
			}
		}
	}
}

class roomHandler implements Runnable {
	private int roomID;
	private String roomURI;
	private Space mySpace;

	public roomHandler(int RoomID, String roomURI) {
		this.roomID = roomID;
		this.roomURI = roomURI;
	}
	
	public static void run() {
		
	}
}