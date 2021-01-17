package common.src.main.GUI.src.WinBuilder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import common.src.main.Bubble;
import common.src.main.LevelHandler;
import common.src.main.Map;
import common.src.main.Point;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GameRoom implements KeyListener, ActionListener {
	
	private Timer timer;
	private int delay = 17, playerHeight = 20, timeLeftForInvincibility1 = (1000/delay)*3, 
			timeLeftForInvincibility2 = (1000/delay)*3;
	static int score1 = 0, score2 = 0, level = 1;
	private JFrame frame;
	private JPanel panel;
	private boolean left1, left2, right1, right2;
	private LevelHandler game;
	private int borderWidth = 800, borderHeight = 600;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JTextField textField_player1;
	private JTextField textField_player2;
	private JTextField textField_p1_scores;
	private JTextField textField_pl2_scores;
	private JLabel Label_level;

	/**
	 * Create the application.
	 */
	public GameRoom() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Add game elements
	
		game = new LevelHandler(level, borderWidth, borderHeight, "Name", "", playerHeight);
		
		// Add GUI
		frame = new JFrame("Game Room");
		

		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setResizable(false);
		frame.setSize(1000,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image img = new ImageIcon(this.getClass().getResource("/Group1.png")).getImage();
		Image img1 = new ImageIcon(this.getClass().getResource("/Group2.png")).getImage();
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
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
					game.getPlayer1().getArrow().updatePos();
				}
				
				// Player 1
				if (game.getPlayer1().getInvicibilityStatus()) {
					g.setColor(Color.yellow);
				} else {
					g.setColor(Color.red);
				}
				g.fillRect(((int)game.getPlayer1().getX()), ((int)game.getPlayer1().getY()), game.getPlayer1().getPlayerWidth(), game.getPlayer1().getPlayerHeight());
				
				// Player 2
				g.setColor(Color.orange);
				
				
				// Bubble
				for(int i = 0; i < game.getBubbles().size(); i++) {
					
					// Get color from bubble
					g.setColor(game.getBubbles().get(i).getColor());
					
					// Bubble collision with player
					if(!game.getPlayer1().getInvicibilityStatus()) {
						if(game.getPlayer1().isAlive() && game.getBubbles().get(i).getShape().intersects(game.getPlayer1().getShape())) {
							
							// Lose life if player gets hit and set invicincibility on
							game.getPlayer1().loseHeart();
							if (!game.getPlayer1().isAlive()) {
								timer.stop();
							}
							game.getPlayer1().setInvincibility(true);
						}
					}
					
					// Bubble collision with arrow
					if(game.getPlayer1().getArrowIsAlive() && game.getBubbles().get(i).getShape().intersects(game.getPlayer1().getArrow().getShape())) {
						if (game.getBubbles().get(i).getSize() > 20) {
							game.getBubbles().addAll(game.getBubbles().get(i).addSplitBubbles());
						}
						game.getBubbles().remove(i);
						game.getPlayer1().getArrow().setAliveTo(false);
					} else {
						int size = game.getBubbles().get(i).getSize();
				
						g.fillOval((int)game.getBubbles().get(i).getPos().getX(), (int)game.getBubbles().get(i).getPos().getY(), size, size);
					}
					game.getBubbles().get(i).move();			
				}
			}
		};
		panel.setBackground(new Color(135, 206, 235));
		panel.setBounds(100, 0, borderWidth, borderHeight);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setFocusable(true);
		frame.getContentPane().add(panel);
		panel.addKeyListener(this);
		panel.setLayout(null);
		

		Label_level = new JLabel("");
		Label_level.setBounds(384, 612, 73, 53);
		frame.getContentPane().add(Label_level);
		Image level = new ImageIcon(this.getClass().getResource("/level.png")).getImage();
		Label_level.setIcon(new ImageIcon(level));
		Image imgP1 = new ImageIcon(this.getClass().getResource("/Player1.png")).getImage();
		textField_player1 = new JTextField("");
		textField_player1.setBounds(100, 612, 114, 34);
		frame.getContentPane().add(textField_player1);
		textField_player1.setColumns(10);
		textField_p1_scores = new JTextField();
		textField_p1_scores.setBounds(226, 612, 54, 34);
		frame.getContentPane().add(textField_p1_scores);
		textField_p1_scores.setColumns(10);
		Image imgP2 = new ImageIcon(this.getClass().getResource("/Player2.png")).getImage();
		textField_player2 = new JTextField("");
		textField_player2.setBounds(707, 612, 114, 34);
		frame.getContentPane().add(textField_player2);
		textField_player2.setColumns(10);
		textField_pl2_scores = new JTextField();
		textField_pl2_scores.setBounds(846, 612, 54, 34);
		frame.getContentPane().add(textField_pl2_scores);
		textField_pl2_scores.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		Image img5 = new ImageIcon(this.getClass().getResource("/Group1.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img5));
		lblNewLabel.setBounds(-14, 0, 114, 600);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(img5));
		lblNewLabel_1.setBounds(886, 0, 114, 600);
		frame.getContentPane().add(lblNewLabel_1);
		
		Image img6 = new ImageIcon(this.getClass().getResource("/Group2.png")).getImage();
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBackground(new Color(135, 206, 250));
		lblNewLabel_3.setIcon(new ImageIcon(img6));
		lblNewLabel_3.setBounds(-34, 599, 1270, 81);
		frame.getContentPane().add(lblNewLabel_3);
		
		
		
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			game.getPlayer1().goRight();
			right1 = true;
		
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {	
			game.getPlayer1().goLeft();
			left1 = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.getPlayer1().makeArrow();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		// Keep moving the player 1
		if (left1) {
			game.getPlayer1().goLeft();
		} else if (right1) {
			game.getPlayer1().goRight();
		}
		
		// When the level is cleared, make a new level
		if (game.getBubbles().isEmpty()) {
			game.makeLevel(++level);
		}
		
		// Get info about player 1
		if(game.getPlayer1().getInvicibilityStatus()) {
			timeLeftForInvincibility1--;
		}
		
		if (timeLeftForInvincibility1 == 0) {
			game.getPlayer1().setInvincibility(false);
			timeLeftForInvincibility1 = (1000/delay)* 3;
		}
		
		panel.repaint();
	}

	public void setUserName1(String name) {
		textField_player1.setText(name);
	}

	public void setUserName2(String name) {
		textField_player2.setText(name);
	}
}
