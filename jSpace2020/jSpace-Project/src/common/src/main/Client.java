package common.src.main;

import org.jspace.*;

import common.src.main.GUI.src.WinBuilder.GameRoom;
import common.src.main.GUI.src.WinBuilder.WaitingRoom;
import common.src.main.GUI.src.WinBuilder.fMenu;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Client {
    static String name, roomID, roomURI;
    static String player1 = null;
    static String player2 = null;
    public static String permissions;
    public static boolean loginButtonClicked = false;
    public static boolean startButtonClicked = false;
    public static boolean inLobby = true;
    public static boolean connected = true;
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


	

	public static void main(String[] argv) throws InterruptedException, UnknownHostException, IOException {

        String host = "tcp://127.0.0.1:9001/";
        String lobbyURI = host + "lobby?keep";
        RemoteSpace lobby = new RemoteSpace(lobbyURI);

        fMenu menu = new fMenu();
        loginButton(menu, lobby);
        try {
            while (true) {
                loginButtonClicked = false;
                // Launch enter page and wait for input
                while (!loginButtonClicked) {
                    Thread.sleep(500);
                }

                // Name might be redundant (consider removing)
                Object[] response = lobby.get(new ActualField("roomURI"), new ActualField(name), new ActualField(roomID), new FormalField(String.class));
                roomURI = (String) response[3];
                
                if (roomURI.equals("")) {
                    // Create popup window
                    System.out.println("Room is full");
                    menu.clearIDField();
                } else {
                    enterPreGameLobby(menu);
                    break;
                }
            }

            System.out.println("Joining game room: " + roomID);
            RemoteSpace gameRoom = new RemoteSpace(roomURI);
            
            // Pre game lobby
            gameRoom.put(FROM, name, READY_TO_PLAY);
            permissions = (String) gameRoom.get(new ActualField(TO), new ActualField(name), new ActualField(PERMISSION), new FormalField(String.class))[3];
            
            System.out.print(permissions);
            WaitingRoom wRoom = new WaitingRoom(name, roomID);

            // Check if client is a host
            if (permissions.equals("host")) {
                wRoom.createStartButton();
                // Add functionallity to the start button
                System.out.println("now has permissions");
                startGameButton(wRoom, gameRoom);
                while (inLobby) {
                    wRoom.setUserName1(name);

                    if (player2 == null) {
                        Object[] playerJoined = gameRoom.getp(new ActualField(TO), new ActualField(permissions), new ActualField(PLAYER_JOINED), new FormalField(String.class));
                        if (playerJoined != null) {
                            player2 = (String) playerJoined[3];
                            wRoom.setUserName2(player2);
                            System.out.println("Player 2 joined");
                        } 
                    }
                    
                    Object[] gameStarted = gameRoom.getp(new ActualField(TO), new ActualField(permissions), new ActualField(GAME_STARTED));
                    if (gameStarted != null) {
                        enterGame(wRoom);
                    }
                    
                    //Click a button to set settings
                    //gameRoom.put(name, SETTINGS);
                }
            
            // If client is not host
            } else if (permissions.equals("participant")) {
                // Get host name
                Object[] lobbyStatus = gameRoom.get(new ActualField(TO), new ActualField(permissions), new ActualField(PLAYER_JOINED), new FormalField(String.class));

                wRoom.setUserName1(name);
                player1 = (String) lobbyStatus[3];
                wRoom.setUserName2(player1);  // Set host name under sofa

                System.out.println("Waiting for host to start the game");
                gameRoom.get(new ActualField(TO), new ActualField(permissions), new ActualField(GAME_STARTED));
                enterGame(wRoom);

                //Click a button to leave the room
                //gameRoom.put(name, LEAVE_ROOM);
            }

            GameRoom gRoom = new GameRoom();
            if (permissions.equals("host")) {
                gRoom.setUserName1(name);
                gRoom.setUserName2(player2);
            }
            if (permissions.equals("participant")) {
                gRoom.setUserName1(name);
                gRoom.setUserName2(player1);
            }

            // Game loop
            while(connected) {
                System.out.println("Entered game loop");
                gameRoom.get(new ActualField("TEST"));
            }   
            
        } catch (InterruptedException e) {}
    }
    public static void enterPreGameLobby(fMenu menu) {
        menu.closeWindow();
    }

    public static void enterGame(WaitingRoom wRoom) {
        inLobby = false;
        wRoom.closeWindow();
    }
    
    public static void loginButton(fMenu menu, Space lobby) throws InterruptedException{
        menu.getLoginButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    name = menu.getName();
                    roomID = menu.getRoomID();
                    if (name.equals("") || roomID.equals("") || name.equals(null) || roomID.equals(null)) {
                        //TODO create popup
                        System.out.println("Please enter a name and roomID");
                    } else {
                        lobby.put("enter", name, roomID);
                        loginButtonClicked = true;
                    }
                } catch (InterruptedException err) {}
            }
        });
    }
    public static void startGameButton(WaitingRoom waitingRoom, Space gameRoom) throws InterruptedException {
        waitingRoom.getStartButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    gameRoom.put(FROM, permissions, START_GAME);
                } catch (InterruptedException err) {}
            }
        });
    }
}