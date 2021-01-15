package common.src.main;

import org.jspace.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;


public class Client {
    static String name, roomID, roomURI;
    public static final String LEAVE_ROOM = "leave_room";
    public static final String READY_TO_PLAY = "ready_to_play";
    public static final String SETTINGS = "settings";
    public static final String PLAYER_JOINED = "player_joined";
    public static final String START_GAME = "start_game";
    public static final String PERMISSION = "permission";
    public static final String LOBBY_INSTRUCTION = "lobby_instruction";
    public static final String GAME_STARTED = "game_started";
    public static final String TO = "to";
	public static final String FROM = "from";

	public static void main(String[] argv) throws InterruptedException, IOException, UnknownHostException {

        String host = "tcp://127.0.0.1:9001/";
        String lobbyURI = host + "lobby?keep";

        RemoteSpace lobby = new RemoteSpace(lobbyURI);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        name = input.readLine();
        
        enterRoom(lobby, input);
        
        try {
            while (true) {
                // Name might be redundant (consider removing)
                Object[] response = lobby.get(new ActualField("roomURI"), new ActualField(name), new ActualField(roomID), new FormalField(String.class));
                roomURI = (String) response[3];
                
                if (roomURI.equals("")) {
                    System.out.println("Room is full");
                    enterRoom(lobby, input);         	
                } else {
                    break;
                }
            }

            System.out.println("Joining game room: " + roomID);
            RemoteSpace gameRoom = new RemoteSpace(roomURI);
            
            
            // Pre game lobby
            boolean inLobby = true;
            boolean connected = true;

            gameRoom.put(name, READY_TO_PLAY);
            String permissions = (String) gameRoom.get(new ActualField(name), new ActualField(PERMISSION), new FormalField(String.class))[2];

            // Check if client is a host
            if (permissions.equals("host")) {
                while (inLobby) {
                    System.out.println("You're the host");
                    System.out.println("Waiting for another player to join...");
                    
                    Object[] lobbyStatus = gameRoom.getp(new ActualField(TO), new ActualField(name), new ActualField(PLAYER_JOINED));
                    if (lobbyStatus != null) {
                        //Todo update lobby screen, to show the joined player
                        System.out.println("Player 2 joined");
                        gameRoom.put(name, START_GAME);
                        inLobby = false;
                    } else {
                        System.out.println("No player joined yet");
                    }

                    //Click a button to start the game
                    //gameRoom.put(name, START_GAME);
                    //inLobby = false;
                    
                    //Click a button to set settings
                    //gameRoom.put(name, SETTINGS);
                }
            
            // If client is not host
            } else if (permissions.equals("participant")) {

                System.out.println("Waiting for host to start the game");
                gameRoom.get(new ActualField(name), new ActualField(GAME_STARTED));

                //Click a button to leave the room
                //gameRoom.put(name, LEAVE_ROOM);
            }

            // Game loop
            while(connected) {
                System.out.println("Entered game loop");
            }   
            
        } catch (InterruptedException e) {}
	}
	
	public static void enterRoom (RemoteSpace lobby, BufferedReader input) throws InterruptedException, IOException {
        System.out.print("Enter roomID: ");
        roomID = input.readLine();

        lobby.put("enter", name, roomID);
        System.out.println("Waiting for server response...");
    }
}