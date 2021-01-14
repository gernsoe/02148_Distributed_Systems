package common.src.main;

import org.jspace.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;


public class Client {
	static String name, roomID, roomURI;

	public static void main(String[] argv) throws InterruptedException, IOException, UnknownHostException {

        String host = "tcp://127.0.0.1:9001/";
        String lobbyURI = host + "lobby?keep";

        RemoteSpace lobby = new RemoteSpace(lobbyURI);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        name = input.readLine();
        
        enterRoom(lobby, input);
        
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

        try {
            gameRoom.put(name, "ready");
            boolean connected = true;

            while (connected) {
                System.out.println("Waiting for another player to join...");

                //Click a button to start the game
                gameRoom.put(name, "start");
                
                //Click a button to set settings
                gameRoom.put(name, "settings");
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