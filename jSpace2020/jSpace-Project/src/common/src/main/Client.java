package common.src.main;

import org.jspace.*;



import common.src.main.GUI.src.WinBuilder.GameRoom;
import common.src.main.GUI.src.WinBuilder.WaitingRoom;
import common.src.main.GUI.src.WinBuilder.endScreen;
import common.src.main.GUI.src.WinBuilder.fMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.Timer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Client {
    static String name, roomID, roomURI;
    static String otherPlayerName = null;
    public static String myPermission;
    public static boolean loginButtonClicked = false;
    public static boolean startButtonClicked = false;
    public static boolean inLobby = true;
    public static boolean connected = true;
    public static final String LEAVE_ROOM = "leave_room";   // used to signal that a player wants to leave a room
    public static final String LEFT_ROOM = "left_room"; 	// used to signal when the player is out of the room
    public static final String READY_TO_PLAY = "ready_to_play";
    public static final String SETTINGS = "settings";
    public static final String PLAYER_JOINED = "player_joined";
    public static final String START_GAME = "start_game";
    public static final String PERMISSION = "permission";
    public static final String LOBBY_INSTRUCTION = "lobby_instruction";
    public static final String GAME_STARTED = "game_started";
    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String HOST = "host";
    public static final String PARTICIPANT = "participant";
    public static final String PLAYER = "player";
    public static final String PLAYERSHOOT = "playershoot";
    public static final String BUBBLES = "bubbles";
    public static final String STARTMAP = "startmap";
    public static final String GOTMAP = "gotmap";

    //String host = "tcp://2.tcp.ngrok.io:10963/";
    public static final String host = "tcp://127.0.0.1:9001/";
    public static final String lobbyURI = host + "lobby?keep";
    public static RemoteSpace lobby;
    public static RemoteSpace gameRoom;
    public static GameRoom gRoom;
    static Timer timer;

	public static void main(String[] argv) throws InterruptedException, UnknownHostException, IOException {

        lobby = new RemoteSpace(lobbyURI);

        try {
            joinRoom();
        } catch (InterruptedException e) {}
    }

    public static void endScreen(int level, int player1Score, int player2Score) throws InterruptedException, UnknownHostException, IOException {
        endScreen eScreen = new endScreen();
        eScreen.setScore(myPermission, player1Score);

    }

    public static void joinRoom() throws InterruptedException, UnknownHostException, IOException {
        resetVariables();   //Reset variables if user leaves room

        fMenu menu = new fMenu();
        loginButton(menu, lobby);

        while (true) {
            loginButtonClicked = false;
            // Launch enter page and wait for input
            while (!loginButtonClicked) {
                Thread.sleep(500);
            }
            //endScreen(1, 50, 100);

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
        myPermission = (String) gameRoom.get(new ActualField(TO), new ActualField(name), new ActualField(PERMISSION), new FormalField(String.class))[3];
        
        WaitingRoom wRoom = new WaitingRoom(name, roomID);
        wRoom.createLeaveButton();
        leaveRoomButton(wRoom);

        // Check if client is a host
        if (myPermission.equals(HOST)) {

            // Add buttons visble to host
            wRoom.createStartButton();
            startGameButton(wRoom);
            wRoom.setUserName1(name);
            wRoom.toggleFigure1();
            wRoom.setRoomID("Welcome to room "+roomID);
            
            while (inLobby) {
                if (otherPlayerName == null) {
                    Object[] playerJoined = gameRoom.getp(new ActualField(TO), new ActualField(myPermission), new ActualField(PLAYER_JOINED), new FormalField(String.class));
                    if (playerJoined != null) {
                        otherPlayerName = (String) playerJoined[3];
                        wRoom.setUserName2(otherPlayerName);
                        wRoom.toggleFigure2();
                        //wRoom.createLeaveButton();
                        System.out.println("Player 2 joined");
                    } 
                }
                
                checkGameStarted(wRoom);
                checkLeaveRoom(wRoom);

                //Click a button to set settings
                //gameRoom.put(name, SETTINGS);
            }
        
        // If client is not host
        } else if (myPermission.equals(PARTICIPANT)) {
            // Get host name
            Object[] lobbyStatus = gameRoom.get(new ActualField(TO), new ActualField(myPermission), new ActualField(PLAYER_JOINED), new FormalField(String.class));

            // Setup waiting room for participant
            wRoom.setUserName1(name);
            wRoom.toggleFigure1();
            otherPlayerName = (String) lobbyStatus[3];
            wRoom.setUserName2(otherPlayerName);  // Set host name under sofa
            wRoom.toggleFigure2();
            wRoom.hostGame();
            wRoom.setRoomID("Welcome to room "+roomID);
            
            System.out.println("Waiting for host to start the game");

            while (inLobby) {
                checkGameStarted(wRoom);
                checkLeaveRoom(wRoom);
            }   
        }
    }

    public static void gameLoop() throws InterruptedException {
    	gRoom = new GameRoom();
    	Gson gson = new Gson();
    	
    	if (myPermission.equals(HOST)) {
    		gRoom.initializeAsHost();
    		ArrayList<Bubble> bubbles = gRoom.getGame().getBubbles();
    		String json = gson.toJson(bubbles);
    		gameRoom.put(FROM, HOST, BUBBLES, json);
    		
    		// send bubbles to participant
    		// gameRoom.put(fields)
    		gameRoom.get(new ActualField(TO), new ActualField(HOST), new ActualField(STARTMAP));
    		gRoom.getTimer().start();
    	} else if (myPermission.equals(PARTICIPANT)) {
    		// Receive bubles from host
    		Object[] getBubbles = gameRoom.get(new ActualField(FROM), new ActualField(HOST), new ActualField(BUBBLES), new FormalField(String.class));
    		JsonParser parser = new JsonParser();
    		String json = (String) getBubbles[3];
    		
    		ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
    		JsonArray bubbleList = parser.parse(json).getAsJsonArray();
    		for(int i = 0; i < bubbleList.size(); i++) {
    			Bubble bubble = gson.fromJson(bubbleList.get(i), Bubble.class);
    			bubbles.add(bubble);
    		}
    		gRoom.initializeAsParticipant(bubbles);
    		gameRoom.put(FROM, PARTICIPANT, GOTMAP);
    		gameRoom.get(new ActualField(TO), new ActualField(PARTICIPANT), new ActualField(STARTMAP));
    		gRoom.getTimer().start();
    	}
    	
        gRoom.setUserName1(name);
        gRoom.setUserName2(otherPlayerName);
        
        // Start timer
        gRoom.getTimer().start();
        

        LevelHandler game = gRoom.getGame();

        /*
        int level = game.getCurrentLevel();
        int score1 = game.getPlayer1().getScore();
        int score2 = game.getPlayer2().getScore();
        endScreen(level, score1, score2);
        */
        // Game loop
        while(connected) {
            // System.out.println("Entered game loop");
            
            // Send player movement information (Only X coordinate is needed)
            if (gRoom.getPlayerRight() || gRoom.getPlayerLeft()) {
            	gameRoom.put(FROM, name, PLAYER, gRoom.getGame().getPlayer1().getPos().getX());
            }
            
            // Receive other player movement information
           /* otherPlayerInfo = gameRoom.getp(new ActualField(TO), new ActualField(otherPlayerName), new ActualField(PLAYERMOVE));
            if (otherPlayerInfo != null) {
            	gRoom.getGame().getPlayer2().setX((int)otherPlayerInfo[3]);;
            }*/
            
            // Arrow shooting from player, send boolean, so other player can make arrow
            if (gRoom.getGame().getPlayer1().getArrowIsAlive()) {
            	gameRoom.put(FROM, name, PLAYERSHOOT, gRoom.getGame().getPlayer1().getArrow().isAlive());
            }
            
            // Receive other player movement
            
            // Send bubbles movement information, if host
            for (Bubble bubble: gRoom.getGame().getBubbles()) {
            	 gameRoom.put(FROM, name, BUBBLES, bubble.getPos().getX(), bubble.getPos().getY(), bubble.getSize());
            }
            
            
           // otherPlayerInfo = gameRoom.get(new ActualField(TO));
        
        
            System.out.println("Entered game loop");

            Bubble testBubble = game.getBubbles().get(0);
            Bubble testBubble1 = game.getBubbles().get(1);
          
            String json = gson.toJson(testBubble);
            String json1 = gson.toJson(testBubble1);
            gameRoom.put("newBubble", json);
            gameRoom.put("newBubble", json1);
            
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
                    gameRoom.put(FROM, myPermission, START_GAME);
                } catch (InterruptedException err) {}
            }
        });
    }
    
    public static void leaveRoomButton(WaitingRoom wRoom) throws InterruptedException {
        wRoom.getLeaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    gameRoom.put(FROM, myPermission, LEAVE_ROOM);
                } catch (InterruptedException err) {}
            }
        });
    }

    public static void resetVariables() {
        name = null;
        roomID = null;
        roomURI = null;
        otherPlayerName = null;
        myPermission = null;
        loginButtonClicked = false;
        startButtonClicked = false;
        inLobby = true;
        connected = true;
    }

    public static void checkGameStarted(WaitingRoom wRoom) throws InterruptedException {
        Object[] gameStarted = gameRoom.getp(new ActualField(TO), new ActualField(myPermission), new ActualField(GAME_STARTED));
        if (gameStarted != null) {
            wRoom.closeWindow();
            gameLoop();
        }
    }

    public static void checkLeaveRoom(WaitingRoom wRoom) throws InterruptedException, IOException {
        Object[] gameLeft = gameRoom.getp(new ActualField(TO), new ActualField(myPermission), new ActualField(LEAVE_ROOM), new FormalField(String.class));
        
        if (gameLeft != null) {
            String me = (String) gameLeft[1];
            String whoLeft = (String) gameLeft[3];
            switch (me) {
                case HOST:
                    switch (whoLeft) {
                        case HOST:
                            wRoom.closeWindow();
                            gameRoom.put(FROM, myPermission, LEFT_ROOM);
                            joinRoom();

                            break;
                        case PARTICIPANT:
                            wRoom.toggleFigure2();
                            wRoom.setUserName2("");
                            otherPlayerName = null;
                            break;
                        default:
                            break;
                    }

                    break;
                case PARTICIPANT:
                    wRoom.closeWindow();
                    gameRoom.put(FROM, myPermission, LEFT_ROOM);
                    joinRoom();
                    break;
                    
                default:
                    break;
            }
        }
    }
}