package common.src.main;

import org.jspace.*;

import common.src.main.GUI.src.WinBuilder.GameRoom;
import common.src.main.GUI.src.WinBuilder.WaitingRoom;
import common.src.main.GUI.src.WinBuilder.fMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

public class Client {
    static String name, roomID, roomURI;
    static String otherPlayerName = null;
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

    //String host = "tcp://2.tcp.ngrok.io:10963/";
    public static final String host = "tcp://127.0.0.1:9001/";
    public static final String lobbyURI = host + "lobby?keep";
    public static RemoteSpace lobby;
    public static RemoteSpace gameRoom;

	public static void main(String[] argv) throws InterruptedException, UnknownHostException, IOException {

        lobby = new RemoteSpace(lobbyURI);

        try {
            joinRoom();
            
            preGameLobby();

            gameLoop(); 
            
        } catch (InterruptedException e) {}
    }

    public static void joinRoom() throws InterruptedException, UnknownHostException, IOException {
        resetVariables();   //Reset variables if user laves room

        fMenu menu = new fMenu();
        loginButton(menu, lobby);

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
                menu.closeWindow();
                System.out.println("Joining game room: " + roomID);
                preGameLobby();
            }
        }
    }

    public static void preGameLobby() throws InterruptedException, UnknownHostException, IOException {
        gameRoom = new RemoteSpace(roomURI);
        gameRoom.put(FROM, name, READY_TO_PLAY);
        permissions = (String) gameRoom.get(new ActualField(TO), new ActualField(name), new ActualField(PERMISSION), new FormalField(String.class))[3];
        
        System.out.print(permissions);
        WaitingRoom wRoom = new WaitingRoom(name, roomID);

        // Check if client is a host
        if (permissions.equals("host")) {
            // Add buttons visble to host
            wRoom.createStartButton();
            wRoom.createLeaveButton();

            // Add functionallity to the start button
            System.out.println("now has permissions");
            startGameButton(wRoom);
            leaveRoomButton(wRoom);
            
            while (inLobby) {
                wRoom.setUserName1(name);
                wRoom.figure1();
                wRoom.setRoomID("Welcome to room "+roomID);

                if (otherPlayerName == null) {
                    Object[] playerJoined = gameRoom.getp(new ActualField(TO), new ActualField(permissions), new ActualField(PLAYER_JOINED), new FormalField(String.class));
                    if (playerJoined != null) {
                        otherPlayerName = (String) playerJoined[3];
                        wRoom.setUserName2(otherPlayerName);
                        wRoom.figure2();
                        //wRoom.createLeaveButton();
                        System.out.println("Player 2 joined");
                    } 
                }
                
                Object[] gameStarted = gameRoom.getp(new ActualField(TO), new ActualField(permissions), new ActualField(GAME_STARTED));
                if (gameStarted != null) {
                    wRoom.closeWindow();
                    gameLoop();
                }

                Object[] gameLeft = gameRoom.getp(new ActualField(TO), new ActualField(permissions), new ActualField(LEAVE_ROOM));
                if (gameLeft != null) {
                    wRoom.closeWindow();
                    joinRoom();
                }
                
                //Click a button to set settings
                //gameRoom.put(name, SETTINGS);
            }
        
        // If client is not host
        } else if (permissions.equals("participant")) {
            // Get host name
            Object[] lobbyStatus = gameRoom.get(new ActualField(TO), new ActualField(permissions), new ActualField(PLAYER_JOINED), new FormalField(String.class));

            // Setup waiting room for participant
            wRoom.createLeaveButton();
            wRoom.setUserName1(name);
            wRoom.figure1();
            otherPlayerName = (String) lobbyStatus[3];
            wRoom.setUserName2(otherPlayerName);  // Set host name under sofa
            wRoom.figure2();
            wRoom.hostGame();
            wRoom.setRoomID("Welcome to room "+roomID);
            
            System.out.println("Waiting for host to start the game");
            gameRoom.get(new ActualField(TO), new ActualField(permissions), new ActualField(GAME_STARTED));
            wRoom.closeWindow();
            gameLoop();
        }
    }

    public static void gameLoop() throws InterruptedException {
        GameRoom gRoom = new GameRoom();
        if (permissions.equals("host")) {
            gRoom.setUserName1(name);
            gRoom.setUserName2(otherPlayerName);
        }
        if (permissions.equals("participant")) {
            gRoom.setUserName1(name);
            gRoom.setUserName2(otherPlayerName);
        }

        // Game loop
        while(connected) {
            System.out.println("Entered game loop");
            gameRoom.get(new ActualField("TEST"));
        }  
    }
    
    public static void loginButton(fMenu menu, Space lobby) throws InterruptedException{
        menu.getLoginButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    name = menu.getName();
                    roomID = menu.getRoomID();
                    if (name.equals("") || name.equals(null)  ) {
                        //TODO create popup
                    	menu.missingName();
                        System.out.println("Name is missing");
                        
                    } if (roomID.equals("") || roomID.equals(null)) {
                    	menu.missingRoomID();
                    	System.out.println("roomID is missing");
                    	
                    }
                    if (!name.equals("") && !roomID.equals("") && !name.equals(null) && !roomID.equals(null) ) {
                        lobby.put("enter", name, roomID);
                        loginButtonClicked = true;
                    }
                } catch (InterruptedException err) {}
            }
        });
    }

    public static void startGameButton(WaitingRoom wRoom) throws InterruptedException {
        wRoom.getStartButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    gameRoom.put(FROM, permissions, START_GAME);
                } catch (InterruptedException err) {}
            }
        });
    }
    
    public static void leaveRoomButton(WaitingRoom wRoom) throws InterruptedException {
        wRoom.getLeaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    gameRoom.put(FROM, permissions, LEAVE_ROOM);
                } catch (InterruptedException err) {}
            }
        });
    }

    public static void resetVariables() {
        name = null;
        roomID = null;
        roomURI = null;
        otherPlayerName = null;
        permissions = null;
        loginButtonClicked = false;
        startButtonClicked = false;
        inLobby = true;
        connected = true;
    }
}