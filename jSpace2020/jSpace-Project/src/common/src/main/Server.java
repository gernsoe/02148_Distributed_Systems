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
		
		String host = "tcp://127.0.0.1:9001/";
		String repoURI = host + "?keep";
		
		repo.add("lobby", lobby);
		repo.addGate(repoURI);
		System.out.println("Opened gate at uri " + repoURI);
		
		Space rooms = new SequentialSpace();
			
        int roomCounter = 0;
        
        // id=1 rooms(id=1,counter=0)
        // id=0 rooms(id=0,counter=1)
        // id=0 rooms(id=0,counter=1)
		
		String roomURI;
		
		while(true) {
			System.out.println("Waiting for client to enter...");
			
			Object[] credentials = lobby.get(new ActualField("enter"), new FormalField(String.class), new FormalField(Integer.class));
			String who = (String) credentials[1];
			int roomID = (int) credentials[2];
			
			System.out.println(who + " wants to join room: " + roomID);
			
			Object[] getRoom = rooms.queryp(new ActualField(roomID), new FormalField(Integer.class));
			if (getRoom != null) {
                roomURI = host + "game" + (int) getRoom[1] + "?keep";   // fx. tcp://127.0.0.1:9001/game0?keep
			} else {
				System.out.println("Creating new room with ID: " + roomID + " for: " + who);
				roomURI = host + "game" + roomCounter + "?keep";        // fx. tcp://127.0.0.1:9001/game0?keep
				rooms.put(roomID, roomCounter);
                new Thread(new roomHandler(roomID, roomCounter, roomURI, repo)).start();
                roomCounter++;
            }
            
            System.out.println("Sending user: " + who + " to game room: " + roomCounter);
            lobby.put("roomURI", who, roomID, roomURI);
		}
	}
}

class roomHandler implements Runnable {
    private int roomID;
    private int roomCounter;
	private String roomURI;
    private Space gameRoom;
    private SpaceRepository repo;

	public roomHandler(int roomID, int roomCounter, String roomURI, SpaceRepository repo) {
        this.roomID = roomID;
        this.roomCounter = roomCounter;
        this.roomURI = roomURI;
        this.repo = repo;
    }
    
	public void run() {
        gameRoom = new SequentialSpace(); 
        repo.add("game" + roomCounter, gameRoom);

        while(true) {
            try {
                gameRoom.get(new ActualField("ready"));
                
            } catch (InterruptedException e) {
                System.out.println(e.getStackTrace());
            }
        }
    }
}