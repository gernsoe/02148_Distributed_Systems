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
    public static boolean otherPlayerDied = false;
    public static int startingLevel = 1;
    public static int amountOfHearts = 5;
    public static int currentLevel;
    public static String myPermission;
    public static boolean loginButtonClicked = false;
    public static boolean startButtonClicked = false;
    public static boolean backToMenuButtonClicked = false;
    public static boolean multiplayer = false;
    public static boolean inLobby = true;
    public static boolean multiConnected = false;
    public static boolean singleConnected = false;
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
    public static final String ARROW = "arrow";
    public static final String PLAYERSHOOT = "playershoot";
    public static final String BUBBLES = "bubbles";
    public static final String STARTMAP = "startmap";
    public static final String GOTMAP = "gotmap";
    public static final String PLAYER_HIT = "player_hit";
    public static final String ARROW_HIT = "arrow_hit";
    public static final String PLAYER_DEAD = "player_dead";
    public static final String GO_TO_END_SCREEN = "go_to_end_screen";
    public static final String AMOUNT_OF_HEARTS = "amount_of_hearts";
    public static final String STARTING_LEVEL = "starting_level";
    public static final String GAME_SETTINGS = "game_settings";
    public static final String SINGLEPLAYER = "singlerplayer";
     
    public static final String host = "tcp://2.tcp.eu.ngrok.io:17657/";
    //public static final String host = "tcp://127.0.0.1:9001/";
    public static final String lobbyURI = host + "lobby?keep";
    public static RemoteSpace lobby;
    public static RemoteSpace gameRoom;
    public static GameRoom gRoom;
    static Timer timer;
    public static Gson gson;
    public static JsonParser parser;
    public static ArrayList<Bubble> bubbles;

	public static void main(String[] argv) throws InterruptedException, UnknownHostException, IOException {

        lobby = new RemoteSpace(lobbyURI);

        try {
            gameStates();

        } catch (InterruptedException e) {}
    }

    

    // Call when restarting the game
    public static void gameStates() throws InterruptedException, UnknownHostException, IOException {
        joinRoom();

        preGameLobby();

        gameLoop();
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
                break;
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
            wRoom.addSettingsButton(wRoom);
            saveSettingsButton(wRoom, gameRoom);
            wRoom.setUserName1(name);
            wRoom.toggleFigure1();
            wRoom.setRoomID("Welcome to room "+roomID);
            
            while (inLobby) {
                checkWaitingRoomInstructions(wRoom);
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
                checkWaitingRoomInstructions(wRoom);
            }   
        }
    }



    public static void gameLoop() throws InterruptedException {
    	multiplayer = false;
    	if (otherPlayerName != null) {
    		gRoom = new GameRoom(multiplayer, name, otherPlayerName, startingLevel, amountOfHearts);
    	} else {
    		gRoom = new GameRoom(multiplayer, name, "", startingLevel, amountOfHearts);
        }
        
        currentLevel = startingLevel;
    	
    	gson = new Gson();
        parser = new JsonParser();
        
        if (myPermission.equals(HOST)) {
    		gRoom.initializeAsHost();
    		bubbles = gRoom.getGame().getBubbles();
    		String json = gson.toJson(bubbles);
    		// Send bubbles
    		gameRoom.put(FROM, HOST, BUBBLES, json);
    		
    		// Get approved by server to start game, if two players
    		if (otherPlayerName != null) {
                gameRoom.get(new ActualField(TO), new ActualField(HOST), new ActualField(STARTMAP));
                multiConnected = true;
                multiplayer = true;
    		} else {
                singleConnected = true;
                gameRoom.put(FROM,HOST,SINGLEPLAYER);
            }
    	} else if (myPermission.equals(PARTICIPANT)) {
    		// Receive bubbles from host
    		Object[] getBubbles = gameRoom.get(new ActualField(FROM), new ActualField(HOST), new ActualField(BUBBLES), new FormalField(String.class));
    	
    		String json = (String) getBubbles[3];
    		
    		bubbles = new ArrayList<Bubble>();
    		JsonArray bubbleList = parser.parse(json).getAsJsonArray();
    		for(int i = 0; i < bubbleList.size(); i++) {
    			Bubble bubble = gson.fromJson(bubbleList.get(i), Bubble.class);
    			bubbles.add(bubble);
            }
            gRoom.initializeAsParticipant(bubbles);
    		gameRoom.put(FROM, PARTICIPANT, GOTMAP);
    		
    		// Get approved by server to start game
            gameRoom.get(new ActualField(TO), new ActualField(PARTICIPANT), new ActualField(STARTMAP));
            multiConnected = true;
            multiplayer = true;
    	}
    	
    	gRoom.setPlayerMode(multiplayer);
        gRoom.setUserName1(name);
        if (multiplayer) {
        	 gRoom.setUserName2(otherPlayerName);
        }
        
        int id = 0;
        int otherid = 1;
        if (myPermission.equals(HOST)) {
        	id = 0;
        	otherid = 1;
        } else if (myPermission.equals(PARTICIPANT)) {
        	id = 1;
        	otherid = 0;
        }

        
        // Start timer
        gRoom.getTimer().start();       

        // Game loop - multiplayer
        while(multiConnected) {

            if (gRoom.getGame().getBubbles().isEmpty() && otherPlayerDied) {
                currentLevel++;
                gRoom.setCurrentLevel(currentLevel);
                gRoom.setLevelText();
                multiConnected = false;
                singleConnected = true;
                gRoom.getGame().makeLevel(currentLevel, gRoom.getGame().getPlayer1().getHearts(), gRoom.getGame().getPlayer1().getScore(), 
                                                        gRoom.getGame().getPlayer2().getHearts(), gRoom.getGame().getPlayer2().getScore());
            }

            if (gRoom.getGame().getBubbles().isEmpty() && myPermission.equals(HOST) && !otherPlayerDied) {
                gRoom.getTimer().stop();
                currentLevel++;
                gRoom.setCurrentLevel(currentLevel);
                gRoom.setLevelText();
                gameRoom.put(FROM, id, "next_level", currentLevel);
                createNewLevel();
            }
            
            Object[] newLevel = gameRoom.getp(new ActualField(FROM), new ActualField(otherid), new ActualField("next_level"), new FormalField(Integer.class));
            if (newLevel != null) {
                gRoom.getTimer().stop();
                currentLevel = (int) newLevel[3];
                System.out.println("Current level PART: " + currentLevel);
                gRoom.setCurrentLevel(currentLevel);
                gRoom.setLevelText();
                createNewLevel();
            }

            // System.out.println("Entered game loop");
            // Player infos from this client
            Player player1 = gRoom.getGame().getPlayer1();
            String jsonPlayer = gson.toJson(player1);
            // Send player movement information
            if (player1.getLeft() | player1.getRight() | player1.isShooting) {
            	gameRoom.put(FROM, id, PLAYER, jsonPlayer);
            }
            
            // Get player movement from other player
            Object[] otherPlayer = gameRoom.getp(new ActualField(FROM), new ActualField(otherid), new ActualField(PLAYER), new FormalField(String.class));

            if (otherPlayer != null) {
                String jsonOtherPlayer = (String) otherPlayer[3];
                JsonObject player2 = parser.parse(jsonOtherPlayer).getAsJsonObject();
                // Point player2pos = gson.fromJson(player2.get("player"), Point.class);
                boolean p2goRight = gson.fromJson(player2.get("right"), Boolean.class);
                boolean p2goLeft = gson.fromJson(player2.get("left"), Boolean.class);
                boolean p2shooting = gson.fromJson(player2.get("isShooting"), Boolean.class);
                int p2score = gson.fromJson(player2.get("scores"), Integer.class);
                int p2hearts = gson.fromJson(player2.get("hearts"), Integer.class);
                Point p2pos = gson.fromJson(player2.get("player"), Point.class);
                // Set other players position and make arrow if they shoot
                gRoom.setP2(p2goRight, p2goLeft, p2shooting,p2pos.getX(),p2score,p2hearts);
            }
          // Send player collision with bubble
            if (gRoom.checkBubbleHitPlayer1()) {
            	gameRoom.put(FROM, id, PLAYER_HIT);  	
            	gRoom.player1LoseHeart();
            	gRoom.setBubbleHitPlayer1(false);
            	
            	System.out.println("check2" + gRoom.checkBubbleHitPlayer1());
            	
            	if(!gRoom.getGame().getPlayer1().isAlive) {
            		
            		// Give server information that player is dead.
            		gameRoom.put(FROM,myPermission,PLAYER_DEAD);
            		
            		// Leads to end screen, atm stopping time
            		gRoom.getTimer().stop();
                    gameRoom.get(new ActualField(TO), new ActualField(myPermission), new ActualField(GO_TO_END_SCREEN));
                    try {
                        gRoom.closeWindow();
                        endScreen(gRoom.getGame().getPlayer1().getScore(), gRoom.getGame().getPlayer2().getScore());
                    } catch (IOException e) {}
            	}
            }
            
            // Receive information about player hit
            Object[] otherPlayerGotHit = gameRoom.getp(new ActualField(FROM),new ActualField(otherid),new ActualField(PLAYER_HIT));
            if (otherPlayerGotHit != null) {
                gRoom.player2LoseHeart();
                if (!gRoom.getGame().getPlayer2().isAlive()) {
                    otherPlayerDied = true;
                }
            }
        }  

        // Game loop - single player
        while (singleConnected) {
        	System.out.println("check2" + gRoom.checkBubbleHitPlayer1());
        	if (gRoom.getGame().getBubbles().isEmpty()) {
        		 System.out.println("check2" + gRoom.checkBubbleHitPlayer1());
                 gRoom.setCurrentLevel(gRoom.getCurrentLevel()+1);
                 gRoom.setLevelText();
                 gRoom.getGame().makeLevel(gRoom.getCurrentLevel(), gRoom.getGame().getPlayer1().getHearts(), gRoom.getGame().getPlayer1().getScore(), 
                         gRoom.getGame().getPlayer2().getHearts(), gRoom.getGame().getPlayer2().getScore());
        	}
        	
            // Send player collision with bubble
            if (gRoom.checkBubbleHitPlayer1()) {
            	gameRoom.put(FROM, id, PLAYER_HIT);  	
            	gRoom.player1LoseHeart();
            	gRoom.setBubbleHitPlayer1(false);
            	
            	System.out.println("check2" + gRoom.checkBubbleHitPlayer1());
            	
            	if(!gRoom.getGame().getPlayer1().isAlive) {
            		
            		// Give server information that player is dead.
            		gameRoom.put(FROM,myPermission,PLAYER_DEAD);
            		
            		// Leads to end screen, atm stopping time
            		gRoom.getTimer().stop();
                    gameRoom.get(new ActualField(TO), new ActualField(myPermission), new ActualField(GO_TO_END_SCREEN));
                    try {
                        gRoom.closeWindow();
                        endScreen(gRoom.getGame().getPlayer1().getScore(), gRoom.getGame().getPlayer2().getScore());
                    } catch (IOException e) {}
            	}
            }
        }
    }



    public static void endScreen(int player1Score, int player2Score) throws InterruptedException, UnknownHostException, IOException {
        endScreen eScreen = new endScreen();
        eScreen.setScore(1, player1Score);
        if (otherPlayerName != null) {
            eScreen.setScore(2, player2Score);
        }
        eScreen.setLevel(currentLevel);

        eScreen.createBackButton();
        backToMenuButton(eScreen);
        while (!backToMenuButtonClicked) {
            Thread.sleep(500);
        }
        eScreen.closeWindow();
        gameStates();
    }



    public static void createNewLevel() throws InterruptedException {
        if (myPermission.equals(HOST)) {
            gRoom.getGame().makeLevel(currentLevel, gRoom.getGame().getPlayer1().getHearts(), gRoom.getGame().getPlayer1().getScore(), 
                                                    gRoom.getGame().getPlayer2().getHearts(), gRoom.getGame().getPlayer2().getScore());
            bubbles = gRoom.getGame().getBubbles();
    		String json = gson.toJson(bubbles);
            // Send bubbles
            System.out.println("Sending new bubbles to participant");
            gameRoom.put(FROM, HOST, BUBBLES, json);
            gameRoom.get(new ActualField(TO), new ActualField(HOST), new ActualField(STARTMAP));
        } else if (myPermission.equals(PARTICIPANT)) {
            
    		// Receive bubbles from host
    		Object[] getBubbles = gameRoom.get(new ActualField(FROM), new ActualField(HOST), new ActualField(BUBBLES), new FormalField(String.class));
    	
    		String json = (String) getBubbles[3];
    		
    		bubbles = new ArrayList<Bubble>();
    		JsonArray bubbleList = parser.parse(json).getAsJsonArray();
    		for(int i = 0; i < bubbleList.size(); i++) {
    			Bubble bubble = gson.fromJson(bubbleList.get(i), Bubble.class);
    			bubbles.add(bubble);
            }
            gRoom.getGame().joinLevel(currentLevel, gRoom.getGame().getPlayer1().getHearts(), gRoom.getGame().getPlayer1().getScore(), 
                                                    gRoom.getGame().getPlayer2().getHearts(), gRoom.getGame().getPlayer2().getScore(), bubbles);
    		gameRoom.put(FROM, PARTICIPANT, GOTMAP);
            System.out.println("joined new level");
    		// Get approved by server to start game
            gameRoom.get(new ActualField(TO), new ActualField(PARTICIPANT), new ActualField(STARTMAP));
        }
        gRoom.getTimer().start();
    }



    public static void checkWaitingRoomInstructions(WaitingRoom wRoom) throws InterruptedException, IOException {
        Object[] waitingRoomOptions = gameRoom.get(new ActualField(TO), new ActualField(myPermission), new FormalField(String.class), new FormalField(String.class),
                                                           new FormalField(Integer.class), new FormalField(String.class), new FormalField(Integer.class));

        String sender = (String) waitingRoomOptions[1];
        String instruction = (String) waitingRoomOptions[2];

        switch (instruction) {
            case PLAYER_JOINED:
                otherPlayerName = (String) waitingRoomOptions[3];
                wRoom.setUserName2(otherPlayerName);
                wRoom.toggleFigure2();
                System.out.println("Player 2 joined");
                break;

            case GAME_STARTED:
                wRoom.closeWindow();
                gameLoop();
                break;

            case GAME_SETTINGS:
                startingLevel = (int) waitingRoomOptions[4];
                amountOfHearts = (int) waitingRoomOptions[6];
                break;

            case LEAVE_ROOM:
                String whoLeft = (String) waitingRoomOptions[3];
                switch (sender) {
                    case HOST:
                        switch (whoLeft) {
                            case HOST:
                                wRoom.closeWindow();
                                gameRoom.put(FROM, myPermission, LEFT_ROOM);
                                gameStates();
    
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
                        gameStates();
                        break;
                        
                    default:
                        break;
                }
                break;
        
            default:
                break;
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



    public static void saveSettingsButton(WaitingRoom wRoom, Space gameRoom) throws InterruptedException{
        wRoom.getSettingsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    for (int i = 0; i < wRoom.settings.checkHearts.size(); ++i) {
                        if (wRoom.settings.checkHearts.get("lives"+i).isSelected()) {
                            amountOfHearts = i+1;
                        }
                    }
                    for (int i = 0; i < wRoom.settings.checkLevels.size(); ++i) {
                        if (wRoom.settings.checkLevels.get("level"+i).isSelected()) {
                            startingLevel = i+1;
                        }
                    }
                    gameRoom.put(TO, PARTICIPANT, GAME_SETTINGS, STARTING_LEVEL, startingLevel, AMOUNT_OF_HEARTS, amountOfHearts);
                    wRoom.settings.closeWindow();
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



    public static void backToMenuButton(endScreen eScreen) {
        eScreen.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backToMenuButtonClicked = true;
            }
        });
    }



    public static void resetVariables() {
        name = "";
        roomID = "";
        roomURI = "";
        otherPlayerName = null;
        myPermission = "";
        loginButtonClicked = false;
        startButtonClicked = false;
        inLobby = true;
        multiConnected = false;
        singleConnected = false;
    }
}