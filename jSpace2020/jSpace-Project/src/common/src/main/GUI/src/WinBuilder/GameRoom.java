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

import javax.swing.Timer;
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
	private int delay = 17, playerHeight = 48, moveDelay = 0;
	int score, level = 9, hearts = 3;
	private JFrame frame;
	private JPanel panel;
	private boolean left1, left2, right1, right2, shooting;
	private LevelHandler game;
	private int borderWidth = 800, borderHeight = 600;
	private JLabel lblNewLabel_1, lblNewLabel_3, lblNewLabel, Label_level;
	private JLabel  Player1Heart1, Player1Heart2, Player1Heart3, Player1Heart4, Player1Heart5,
		Player2Heart1, Player2Heart2, Player2Heart3, Player2Heart4, Player2Heart5, Label_leveltext, Player2Label, Player1Label, score_1, score_2;

	/**
	 * Create the application.
	 * @param actionListener 
	 */
	public GameRoom() {
		game = new LevelHandler(borderWidth, borderHeight, "Name", "", playerHeight);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void initializeAsHost() {
		game.makeLevel(level, hearts, score);
		initialize();
	}
	
	public void initializeAsParticipant(ArrayList<Bubble> bubbles) {
		game.joinLevel(level, hearts, score, bubbles);
		initialize();
	}
	
	private void initialize() {
		// Add game elements

		// Add GUI

		frame = new JFrame("Game Room");

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
					g.setColor(Color.black);
					g.fillRect((int)game.getPlayer1().getArrow().getX(), (int)game.getPlayer1().getArrow().getY(), game.getPlayer1().getArrow().getArrowWidth(), game.getPlayer1().getArrow().getArrowHeight());
				}
				
				// Player 1
				if(left1 & !shooting) {
					g.drawImage(player1left,(int)game.getPlayer1().getX(),(int)game.getPlayer1().getY(),game.getPlayer1().getPlayerWidth(),game.getPlayer1().getPlayerHeight(), null);
				} else if (right1 & !shooting) {
					g.drawImage(player1right,(int)game.getPlayer1().getX(),(int)game.getPlayer1().getY(),game.getPlayer1().getPlayerWidth(),game.getPlayer1().getPlayerHeight(), null);
				} else {
					g.drawImage(player1front,(int)game.getPlayer1().getX(),(int)game.getPlayer1().getY(),game.getPlayer1().getPlayerWidth(),game.getPlayer1().getPlayerHeight(), null);
				}
				
				// Player 2
				
				
				
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
		panel.setFocusable(true);
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
		
		Player2Label = new JLabel("");
		Player2Label.setHorizontalAlignment(SwingConstants.RIGHT);
		Player2Label.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		Player2Label.setBounds(750, 610, 150, 30);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(750,610,150,30);
		panel_2.add(Player2Label);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(270,610,70,30);
		
		// Player 2to score
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(660,610,70,30);
		frame.getContentPane().add(panel_4);
		
		score_2 = new JLabel();
		score_2.setText("?");
		score_2.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		panel_4.add(score_2);
		
		
		Player2Label.setForeground(Color.green);
		frame.getContentPane().add(panel_2);
		frame.getContentPane().add(panel_1);
		frame.getContentPane().add(panel_3);
		
		
		Player1Label.setForeground(Color.red);
		
		// Add scores
		score_1 = new JLabel();
		score_1.setText("" + game.getPlayer1().getScore());
		score_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		panel_3.add(score_1);
		
		// Hearts of player 1
		Player1Heart1 = new JLabel();
		Player1Heart1.setBounds(100, 650, 32, 32);
		frame.getContentPane().add(Player1Heart1);
		Player1Heart1.setIcon(new ImageIcon(imgHeart));
		
		Player1Heart2 = new JLabel();
		Player1Heart2.setBounds(135, 650, 32, 32);
		frame.getContentPane().add(Player1Heart2);
		Player1Heart2.setIcon(new ImageIcon(imgHeart));
		
		Player1Heart3 = new JLabel();
		Player1Heart3.setBounds(170, 650, 32, 32);
		frame.getContentPane().add(Player1Heart3);
		Player1Heart3.setIcon(new ImageIcon(imgHeart));
		
		Player1Heart4 = new JLabel();
		Player1Heart4.setBounds(205, 650, 32, 32);
		frame.getContentPane().add(Player1Heart4);
		Player1Heart4.setIcon(new ImageIcon(imgHeart));
		
		Player1Heart5 = new JLabel();
		Player1Heart5.setBounds(240, 650, 32, 32);
		frame.getContentPane().add(Player1Heart5);
		Player1Heart5.setIcon(new ImageIcon(imgHeart));
		
		// Hearts of player 2
		Player2Heart1 = new JLabel();
		Player2Heart1.setBounds(865, 650, 32, 32);
		frame.getContentPane().add(Player2Heart1);
		Player2Heart1.setIcon(new ImageIcon(imgHeart));
		
		Player2Heart2 = new JLabel();
		Player2Heart2.setBounds(830, 650, 32, 32);
		frame.getContentPane().add(Player2Heart2);
		Player2Heart2.setIcon(new ImageIcon(imgHeart));
		
		Player2Heart3 = new JLabel();
		Player2Heart3.setBounds(795, 650, 32, 32);
		frame.getContentPane().add(Player2Heart3);
		Player2Heart3.setIcon(new ImageIcon(imgHeart));
		
		Player2Heart4 = new JLabel();
		Player2Heart4.setBounds(760, 650, 32, 32);
		frame.getContentPane().add(Player2Heart4);
		Player2Heart4.setIcon(new ImageIcon(imgHeart));
		
		Player2Heart5 = new JLabel();
		Player2Heart5.setBounds(725, 650, 32, 32);
		frame.getContentPane().add(Player2Heart5);
		Player2Heart5.setIcon(new ImageIcon(imgHeart));
		
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
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			if (!shooting) {
				right1 = true;
			}
			break;
		case KeyEvent.VK_LEFT:
			if (!shooting) {
				left1 = true;	
			}
			break;
		case KeyEvent.VK_SPACE:
			if (!game.getPlayer1().getArrowIsAlive()) {
				shooting = true;
				game.getPlayer1().makeArrow();
				moveDelay = (68/delay);
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			right1 = false;
			break;
		case KeyEvent.VK_LEFT:
			left1 = false;
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
		if (shooting) {
			moveDelay--;
			if (moveDelay == 0) {
				shooting = false;
			}
		} else if (left1 & !shooting) {
			game.getPlayer1().goLeft();
		} else if (right1 & !shooting) {
			game.getPlayer1().goRight();
		} 
	}
	
	public void checkLevel() {
		// When the level is cleared, make a new level
		if (game.getBubbles().isEmpty()) {
			game.makeLevel(level, game.getPlayer1().getHearts(),game.getPlayer1().getScore());
			Label_leveltext.setText("" + level);
		}
	}
	
	public void updateBubbles() {
		for(int i = 0; i < game.getBubbles().size(); i++) {
			
			// Move bubbles
			game.getBubbles().get(i).move();
			
			// Bubble collision with player
			if(game.getPlayer1().isAlive() && game.getBubbles().get(i).getShape().intersects(game.getPlayer1().getShape())) {
				// Lose life if player gets hit and restart level, if dead then stop game
				System.out.println("bubble shape" + game.getBubbles().get(i).getShape().getBounds2D());
				System.out.println("player shape" + game.getPlayer1().getShape().toString());
				if (game.getPlayer1().getHearts() == 1) {
					Player1Heart1.setVisible(false);
				} else if (game.getPlayer1().getHearts() == 2) {
					Player1Heart2.setVisible(false);
				} else if (game.getPlayer1().getHearts() == 3) {
					Player1Heart3.setVisible(false);
				} else if (game.getPlayer1().getHearts() == 4) {
					Player1Heart4.setVisible(false);
				} else if (game.getPlayer1().getHearts() == 5) {
					Player1Heart5.setVisible(false);
				}

				game.getPlayer1().loseHeart();
				if (!game.getPlayer1().isAlive()) {
					timer.stop();
				} else {
					try {
						Thread.sleep(3000);
						game.getCurrentLevel().makeLevel(level,game.getPlayer1().getHearts(),game.getPlayer1().getScore());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			
			// Bubble collision with arrow
			if(game.getPlayer1().getArrowIsAlive() && game.getBubbles().get(i).getShape().intersects(game.getPlayer1().getArrow().getShape())) {
				if (game.getBubbles().get(i).getSize() > 20) {
					game.getBubbles().addAll(game.getBubbles().get(i).addSplitBubbles());
				}
				game.getPlayer1().setScore(game.getPlayer1().getScore()+1);
				score_1.setText("" + game.getPlayer1().getScore());
				game.getBubbles().remove(i);
				game.getPlayer1().getArrow().setAliveTo(false);
			}	
		}
	}
	
	public void updateArrows() {
		if (game.getPlayer1().getArrowIsAlive()) {
		game.getPlayer1().getArrow().updatePos();
		}
		/* if (game.getPlayer2().getArrowIsAlive()) {
		game.getPlayer2().getArrow().updatePos();
		} */
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Redraw bubbles, players and arrows
		panel.repaint();
		
		// Player movement
		updateP1();
		
		// Update arrows
		updateArrows();
		
		// Bubble movement and collisions
		updateBubbles();
		
		// Level Status
		checkLevel();
	}
	
	public boolean getPlayerLeft() {
		return left1;
	}
	
	public boolean getPlayerRight() {
		return right1;
	}
}
