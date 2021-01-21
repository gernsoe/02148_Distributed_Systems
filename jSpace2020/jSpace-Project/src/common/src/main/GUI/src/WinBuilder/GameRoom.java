package common.src.main.GUI.src.WinBuilder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import common.src.main.Bubble;
import common.src.main.LevelHandler;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GameRoom implements KeyListener, WindowListener, ActionListener {
	
	private Timer timer;
	private int delay = 17, playerHeight = 48;
	public int score, level = 1, hearts;
	boolean bubbleHitPlayer1 = false;
	boolean player1ArrowHit = false;
	boolean player2ArrowHit = false;
	boolean multiplayer;
	private JFrame frame;
	private JPanel panel;
	private LevelHandler game;
	private int borderWidth = 800, borderHeight = 600;
	private JLabel lblNewLabel_1, lblNewLabel_3, lblNewLabel, Label_level;
	private JLabel Label_leveltext, Player2Label, Player1Label, score_1, score_2;
	private Map<String, JLabel> Player1Hearts = new HashMap<String, JLabel>();
	private Map<String, JLabel> Player2Hearts = new HashMap<String, JLabel>();

	/**
	 * Create the application.
	 * @param actionListener 
	 */
	public GameRoom(boolean multiplayer, String player1name, String player2name, int level, int hearts) {
		game = new LevelHandler(borderWidth, borderHeight, player1name, player2name, playerHeight);
		this.multiplayer = multiplayer;
		this.level = level;
		this.hearts = hearts;
	}
	
	public void setPlayerMode(boolean multiplayer) {
		this.multiplayer = multiplayer;
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	
	public void initializeAsHost() {
		game.makeLevel(level, hearts, score, hearts, score);
	}
	
	public void initializeAsParticipant(ArrayList<Bubble> bubbles) {
		game.joinLevel(level, hearts, score, hearts, score, bubbles);
	}
	
	private void initialize() {

		// Add game elements
		System.out.println("multiplayer" + multiplayer);

		// Add GUI
		frame = new JFrame("Game Room");
		frame.setVisible(true);

		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.getContentPane().setPreferredSize(new Dimension(1000,700));
		frame.pack();
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		// Images for both players
		Image player1front = new ImageIcon(this.getClass().getResource("/figure1.png")).getImage().getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		Image player1left = new ImageIcon(this.getClass().getResource("/figure1_left.png")).getImage().getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		Image player1right = new ImageIcon(this.getClass().getResource("/figure1_right.png")).getImage().getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		Image player2front =  new ImageIcon(this.getClass().getResource("/figure2.png")).getImage().getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		Image player2left = new ImageIcon(this.getClass().getResource("/figure2_left.png")).getImage().getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		Image player2right = new ImageIcon(this.getClass().getResource("/figure2_right.png")).getImage().getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		
		panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				
				// Background
				g.setColor(game.getLevelColor(level));
				g.fillRect(0,0,borderWidth,borderHeight);
				
				// Arrow for player 1
				if (game.getPlayer1().getArrowIsAlive()) {
					g.setColor(Color.red);
					g.fillRect((int)game.getPlayer1().getArrow().getX(), (int)game.getPlayer1().getArrow().getY(), game.getPlayer1().getArrow().getArrowWidth(), game.getPlayer1().getArrow().getArrowHeight());
				}
				
				// Arrow for player 2
				if (multiplayer && game.getPlayer2().isAlive()) {
					if (game.getPlayer2().getArrowIsAlive()) {
						g.setColor(Color.green);
						g.fillRect((int)game.getPlayer2().getArrow().getX(), (int)game.getPlayer2().getArrow().getY(), game.getPlayer2().getArrow().getArrowWidth(), game.getPlayer2().getArrow().getArrowHeight());
					}
				}

				// Color player background yellow if invincible
				if (game.getPlayer1().getInvincible()) {
					g.setColor(Color.yellow);
					g.fillRect((int)game.getPlayer1().getX(),(int)game.getPlayer1().getY(),game.getPlayer1().getPlayerWidth(),game.getPlayer1().getPlayerHeight());
				}
				if (multiplayer && game.getPlayer2().getInvincible()) {
					g.setColor(Color.yellow);
					g.fillRect((int)game.getPlayer2().getX(),(int)game.getPlayer2().getY(),game.getPlayer2().getPlayerWidth(),game.getPlayer2().getPlayerHeight());
				}
				
				// Player 1
				if(game.getPlayer1().getLeft() & !(game.getPlayer1().isShooting())) {
					g.drawImage(player1left,(int)game.getPlayer1().getX(),(int)game.getPlayer1().getY(),game.getPlayer1().getPlayerWidth(),game.getPlayer1().getPlayerHeight(), null);
				} else if (game.getPlayer1().getRight() & !(game.getPlayer1().isShooting())) {
					g.drawImage(player1right,(int)game.getPlayer1().getX(),(int)game.getPlayer1().getY(),game.getPlayer1().getPlayerWidth(),game.getPlayer1().getPlayerHeight(), null);
				} else {
					g.drawImage(player1front,(int)game.getPlayer1().getX(),(int)game.getPlayer1().getY(),game.getPlayer1().getPlayerWidth(),game.getPlayer1().getPlayerHeight(), null);
				}
				
				
				// Player 2
				if (multiplayer && game.getPlayer2().isAlive()) {
					if(game.getPlayer2().getLeft() & !(game.getPlayer2().isShooting())) {
						g.drawImage(player2left,(int)game.getPlayer2().getX(),(int)game.getPlayer2().getY(),game.getPlayer2().getPlayerWidth(),game.getPlayer2().getPlayerHeight(), null);
					} else if (game.getPlayer2().getRight() & !(game.getPlayer2().isShooting())) {
						g.drawImage(player2right,(int)game.getPlayer2().getX(),(int)game.getPlayer2().getY(),game.getPlayer2().getPlayerWidth(),game.getPlayer2().getPlayerHeight(), null);
					} else {
						g.drawImage(player2front,(int)game.getPlayer2().getX(),(int)game.getPlayer2().getY(),game.getPlayer2().getPlayerWidth(),game.getPlayer2().getPlayerHeight(), null);
					}

					// To update it visually correctly
					game.getPlayer2().setRight(false);
					game.getPlayer2().setLeft(false);
				}
				
				// Bubble
				for(int i = 0; i < game.getBubbles().size(); i++) {
					// Get color from bubble and draw its movement
					g.setColor(game.getBubbles().get(i).getColor());
					g.fillOval((int)Math.round(game.getBubbles().get(i).getPos().getX()), (int)Math.round(game.getBubbles().get(i).getPos().getY()), game.getBubbles().get(i).getSize(), game.getBubbles().get(i).getSize());
				}
			}
		};
		panel.setBounds(100, 0, 800, 600);
		panel.setBackground(new Color(135, 206, 235));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setPreferredSize(new Dimension(borderWidth,borderHeight));
		frame.getContentPane().add(panel);
		panel.addKeyListener(this);
		panel.setLayout(null);
		
		// Read images and resize and add to panels
		Image img = new ImageIcon(this.getClass().getResource("/Tall.png")).getImage();
		Image img1 = new ImageIcon(this.getClass().getResource("/Long.png")).getImage();
		Image img_level = new ImageIcon(this.getClass().getResource("/level.png")).getImage();
		Image imgHeart =  new ImageIcon(this.getClass().getResource("/heart.png")).getImage();
		
		// Add playerNames
		Player1Label = new JLabel("");
		Player1Label.setHorizontalAlignment(SwingConstants.LEFT);
		Player1Label.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		Player1Label.setBounds(100, 610, 150, 30);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(100,610,150,30);
		panel_1.add(Player1Label);
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(270,610,70,30);
		
		// Player 2's label and score
		Player2Label = new JLabel("");
		Player2Label.setHorizontalAlignment(SwingConstants.RIGHT);
		Player2Label.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		Player2Label.setBounds(750, 610, 150, 30);
			
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(750,610,150,30);
		panel_2.add(Player2Label);
		frame.getContentPane().add(panel_2);
			
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(660,610,70,30);
		frame.getContentPane().add(panel_4);
			
		score_2 = new JLabel();
		score_2.setText("0");
		score_2.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		panel_4.add(score_2);
			
		Player2Label.setForeground(Color.green);
		
		// Hide if not multiplayer
		if (!multiplayer) {
			panel_4.setVisible(false);
			panel_2.setVisible(false);
		}
		
		frame.getContentPane().add(panel_1);
		frame.getContentPane().add(panel_3);
		
		
		Player1Label.setForeground(Color.red);
		
		// Add scores
		score_1 = new JLabel();
		score_1.setText("0");
		score_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		panel_3.add(score_1);

		// Hearts of player 1
		for (int i = 0; i < hearts; ++i) {
			JLabel Player1Heart = new JLabel();
			Player1Heart.setBounds(100 + i*35, 650, 32, 32);
			frame.getContentPane().add(Player1Heart);
			Player1Heart.setIcon(new ImageIcon(imgHeart));
			Player1Hearts.put("heart"+i, Player1Heart);
		}

		if (multiplayer) {
			for (int i = 0; i < hearts; ++i) {
				JLabel Player2Heart = new JLabel();
				Player2Heart.setBounds(865 - i*35, 650, 32, 32);
				frame.getContentPane().add(Player2Heart);
				Player2Heart.setIcon(new ImageIcon(imgHeart));
				Player2Hearts.put("heart"+i, Player2Heart);
			}
		}
		
		// Add labels
		Label_leveltext = new JLabel("");
		Label_leveltext.setHorizontalAlignment(SwingConstants.CENTER);
		Label_leveltext.setBounds(470, 620, 61, 34);
		frame.getContentPane().add(Label_leveltext);
		Label_leveltext.setText("" + level);
		Label_leveltext.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		Label_level = new JLabel("");
		Label_level.setBounds(470, 620, 61, 34);
		frame.getContentPane().add(Label_level);
		Label_level.setIcon(new ImageIcon(img_level));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 100, 600);
		frame.getContentPane().add(lblNewLabel);
						
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(0, 600, 1000, 100);
		frame.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setIcon(new ImageIcon(img1));

		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(900, 0, 100, 600);
		frame.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_1.setIcon(new ImageIcon(img));
		
		timer = new Timer(delay,this);
		panel.setFocusable(true);
		panel.requestFocusInWindow();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				if (!game.getPlayer1().isShooting()) {
					game.getPlayer1().setRight(true);
				}
				break;
			case KeyEvent.VK_LEFT:
				if (!game.getPlayer1().isShooting()) {
					game.getPlayer1().setLeft(true);
				}
				break;
			case KeyEvent.VK_SPACE:
				if (!game.getPlayer1().getArrowIsAlive() & !game.getPlayer1().isShooting()) {
					game.getPlayer1().setShooting(true);
					game.getPlayer1().makeArrow();
					game.getPlayer1().setMoveDelay(68/delay);
				}
				break;
			case KeyEvent.VK_UP:
				if (!game.getPlayer1().getArrowIsAlive() & !game.getPlayer1().isShooting()) {
					game.getPlayer1().setShooting(true);
					game.getPlayer1().makeArrow();
					game.getPlayer1().setMoveDelay(68/delay);
				}
				break;
			default:
				System.out.println("key not recognized");
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				game.getPlayer1().setRight(false);
				break;
			case KeyEvent.VK_LEFT:
				game.getPlayer1().setLeft(false);
				break;
		}
	}

	public void setUserName1(String name) {
		Player1Label.setText(name);
	}

	public void setUserName2(String name) {
			Player2Label.setText(name);
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	public LevelHandler getGame() {
		return game;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void updateP1() {
		int moveDelay = game.getPlayer1().getMoveDelay();
		if (game.getPlayer1().isShooting()) {
			game.getPlayer1().setMoveDelay(--moveDelay);
			if (moveDelay == 0) {
				game.getPlayer1().setShooting(false);
			}
		} else if (game.getPlayer1().getLeft() & !(game.getPlayer1().isShooting())) {
			game.getPlayer1().goLeft();
		} else if (game.getPlayer1().getRight() & !(game.getPlayer1().isShooting())) {
			game.getPlayer1().goRight();
		} 
	}
	
	public void setP2(boolean p2Right, boolean p2Left, boolean p2shoot, double xCord, int p2score, int p2hearts) {
		game.getPlayer2().setX(xCord);
		game.getPlayer2().setShooting(p2shoot);
		game.getPlayer2().setLeft(p2Left);
		game.getPlayer2().setRight(p2Right);
		game.getPlayer2().setScore(p2score);
		game.getPlayer2().setHearts(p2hearts);
		if (p2shoot & !(game.getPlayer2().getArrowIsAlive())) {
			game.getPlayer2().makeArrow();
		}
	}
	
	public void updateP2() {
		int moveDelay = game.getPlayer2().getMoveDelay();
		if(game.getPlayer2().isShooting()) {
			game.getPlayer2().setMoveDelay(--moveDelay);
			if (moveDelay == 0) {
				game.getPlayer2().setShooting(false);
			}
		}
	}

	public void closeWindow() {
		frame.setVisible(false);
	}
	/*
	public void checkLevel() {
		// When the level is cleared, make a new level
		if (game.getBubbles().isEmpty()) {
			++level;
			System.out.println("Incrementing levels");
			//game.makeLevel(++level, game.getPlayer1().getHearts(),game.getPlayer1().getScore(), game.getPlayer2().getHearts(), game.getPlayer2().getScore());
			Label_leveltext.setText("" + level);
		} 
	}
	*/

	public void setLevelText() {
		Label_leveltext.setText("" + level);
	}

	public void setCurrentLevel(int newLevel) {
		level = newLevel;
	}

	public int getCurrentLevel() {
		return level;
	}
	
	public void updateBubbles() {
		
		for(int i = 0; i < game.getBubbles().size(); i++) {
			
			// Move bubbles
			game.getBubbles().get(i).move();
			
			// Bubble collision with player
			if(game.getPlayer1().isAlive() && !(game.getPlayer1().getInvincible()) && game.getBubbles().get(i).getShape().intersects(game.getPlayer1().getShape())) {
				// Lose life if player gets hit and restart level, if dead then stop game
				System.out.println("bubble shape" + game.getBubbles().get(i).getShape().getBounds2D());
				System.out.println("player shape" + game.getPlayer1().getShape().toString());
				
				// Lose health if not multiplayer
				bubbleHitPlayer1 = true;
				
			}
			
			// Bubble collision with arrow
			if(game.getPlayer1().getArrowIsAlive() && game.getBubbles().get(i).getShape().intersects(game.getPlayer1().getArrow().getShape())) {

				player1ArrowHit = true;

				//setBubbleHitByArrow = game.getBubbles().get(i);

				if (game.getBubbles().get(i).getSize() > 20) {
					game.getBubbles().addAll(game.getBubbles().get(i).addSplitBubbles());
				}
				game.getPlayer1().setScore(game.getPlayer1().getScore()+1);
				score_1.setText("" + game.getPlayer1().getScore());
				game.getBubbles().remove(i);
				game.getPlayer1().getArrow().setAliveTo(false);
			}	

			// Bubble collision with arrow
			if(game.getPlayer2().getArrowIsAlive() && game.getBubbles().get(i).getShape().intersects(game.getPlayer2().getArrow().getShape())) {

				player1ArrowHit = true;

				//setBubbleHitByArrow = game.getBubbles().get(i);

				if (game.getBubbles().get(i).getSize() > 20) {
					game.getBubbles().addAll(game.getBubbles().get(i).addSplitBubbles());
				}
				game.getPlayer2().setScore(game.getPlayer2().getScore()+1);
				score_2.setText("" + game.getPlayer2().getScore());
				game.getBubbles().remove(i);
				game.getPlayer2().getArrow().setAliveTo(false);
			}	
			
		}
	}
	
	public void updateArrows() {
		if (game.getPlayer1().getArrowIsAlive()) {
			game.getPlayer1().getArrow().updatePos();
		}
		if (multiplayer) {
			if (game.getPlayer2().getArrowIsAlive()) {
				game.getPlayer2().getArrow().updatePos();
			} 
		}
	}
	
	public boolean checkBubbleHitPlayer1() {
		return bubbleHitPlayer1;
	}
	
	public void setBubbleHitPlayer1(boolean hit) {
		bubbleHitPlayer1 = hit;
	}

	public boolean checkPlayer1ArrowHit() {
		return player1ArrowHit;
	}

	public void setPlayer1ArrowHit(boolean hit) {
		player1ArrowHit = hit;
	}

	public boolean checkPlayer2ArrowHit() {
		return player2ArrowHit;
	}
	public void setPlayer2ArrowHit(boolean hit) {
		player2ArrowHit = hit;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		// Redraw bubbles, players and arrows
		panel.repaint();
		// Player movement
		updateP1();
		if (multiplayer) {
			updateP2();
		}
		
		// Update arrows
		updateArrows();
		
		// Bubble movement and collisions
		updateBubbles();
		
		// Update invincibility status for player
		updateInvincibility();
		
		// Level Status
		//checkLevel();
	}
	
	public void updateInvincibility() {
		if (game.getPlayer1().getInvincible()) {
			game.getPlayer1().setInvinTime(game.getPlayer1().getInvinTime()-1);
			if (game.getPlayer1().getInvinTime() == 0) {
				game.getPlayer1().setInvincible(false);
			}
		}
		if (multiplayer && game.getPlayer2().getInvincible()) {
			game.getPlayer2().setInvinTime(game.getPlayer2().getInvinTime()-1);
			if (game.getPlayer2().getInvinTime() == 0) {
				game.getPlayer2().setInvincible(false);
			}
		}
	}
	
	public void player1LoseHeart() {

		if (game.getPlayer1().getHearts() == 1) {
			Player1Hearts.get("heart0").setVisible(false);
		} else if (game.getPlayer1().getHearts() == 2) {
			Player1Hearts.get("heart1").setVisible(false);
		} else if (game.getPlayer1().getHearts() == 3) {
			Player1Hearts.get("heart2").setVisible(false);
		} else if (game.getPlayer1().getHearts() == 4) {
			Player1Hearts.get("heart3").setVisible(false);
		} else if (game.getPlayer1().getHearts() == 5) {
			Player1Hearts.get("heart4").setVisible(false);
		}

		game.getPlayer1().loseHeart();
		if (game.getPlayer1().isAlive()) {
			game.getPlayer1().setInvincible(true);
			game.getPlayer1().setInvinTime((1000/delay)*3);
		}
	}
	
	public void player2LoseHeart() {

		if (game.getPlayer2().getHearts() == 1) {
			Player2Hearts.get("heart0").setVisible(false);
		} else if (game.getPlayer2().getHearts() == 2) {
			Player2Hearts.get("heart1").setVisible(false);
		} else if (game.getPlayer2().getHearts() == 3) {
			Player2Hearts.get("heart2").setVisible(false);
		} else if (game.getPlayer2().getHearts() == 4) {
			Player2Hearts.get("heart3").setVisible(false);
		} else if (game.getPlayer2().getHearts() == 5) {
			Player2Hearts.get("heart4").setVisible(false);
		}

		game.getPlayer2().loseHeart();
		if (game.getPlayer2().isAlive()) {
			game.getPlayer2().setInvincible(true);
			game.getPlayer2().setInvinTime((1000/delay)*3);
		}
	}
	
	public boolean getPlayerLeft() {
		return game.getPlayer1().getLeft();
	}
	
	public boolean getPlayerRight() {
		return game.getPlayer1().getRight();
	}
}
