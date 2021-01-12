package common.src.main;

import org.jspace.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;


public class Client {

	public static void main(String[] argv) throws InterruptedException, IOException, UnknownHostException {

        String host = "tcp://127.0.0.1:9001/";
        String lobbyURI = host + "lobby?keep";

        RemoteSpace lobby = new RemoteSpace(lobbyURI);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        String name = input.readLine();

        System.out.print("Enter roomID: ");
        String roomID = input.readLine();

        lobby.put("enter", name, roomID);
        System.out.println("Waiting for server response...");

        // Name might be redundant (consider removing)
        Object[] response = lobby.get(new ActualField("roomURI"), new ActualField(name), new ActualField(roomID), new FormalField(String.class));
        String roomURI = (String) response[3];

        System.out.println("Joining game room: " + roomID);
        RemoteSpace gameRoom = new RemoteSpace(roomURI);

        while(true) {
            try {
                gameRoom.put("ready");
                gameRoom.get(new ActualField("data"));
            } catch (InterruptedException e) {}
        }
	}

}