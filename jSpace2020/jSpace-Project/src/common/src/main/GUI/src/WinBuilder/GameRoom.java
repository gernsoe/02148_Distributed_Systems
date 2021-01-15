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
import common.src.main.Map;
import common.src.main.Point;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GameRoom implements KeyListener, ActionListener {
	
	private Timer timer;
	private int delay = 17, playerHeight = 20, playerWidth = playerHeight/2;
	private JFrame frame;
	private JPanel panel;
	private Map game;
	private Color color = new Color(135, 206, 250);
	private int borderWidth = 800, borderHeight = 600;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JTextField textField_player1;
	private JTextField textField_player2;
	private JTextField textField_p1_scores;
	private JTextField textField_pl2_scores;
	private JLabel pl1;
	private JLabel pl2;



	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameRoom window = new GameRoom();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameRoom() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Add game elements
		game = new Map(borderWidth, borderHeight, 10, "David", "Christian", playerHeight);
		
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
				g.setColor(color);
				g.fillRect(0,0,borderWidth,borderHeight);
				
		
				// Player 1
				g.setColor(Color.red);
				g.fillRect(((int)game.getPlayer1().getX()), ((int)game.getPlayer1().getY()), playerWidth, playerHeight);
				
				// Player 2
				g.setColor(Color.orange);
				
				// Arrow
				if (game.getPlayer1().getArrowIsAlive()) {
					g.setColor(Color.YELLOW);
					g.fillRect((int)game.getPlayer1().getArrow().getX(), (int)game.getPlayer1().getArrow().getY(), game.getPlayer1().getArrow().getArrowWidth(), game.getPlayer1().getArrow().getArrowHeight());
					game.getPlayer1().getArrow().updatePos();
				}
				
				// Bubble
				g.setColor(Color.blue);
				for(int i = 0; i < game.getBubbles().size(); i++) {
					
					// Bubble collision with arrow
					if (game.getPlayer1().getArrowIsAlive() && game.getBubbles().get(i).collisionWithArrow(game.getPlayer1().getArrow())) {
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

				// g.dispose();
			}
		};
		panel.setBackground(new Color(135, 206, 235));
		panel.setBounds(100, 0, borderWidth, borderHeight);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setFocusable(true);
		frame.getContentPane().add(panel);
		panel.addKeyListener(this);
		panel.setLayout(null);
		
		
		
		
		//Player 1 scores
		pl1 = new JLabel("");
		Image imgP1 = new ImageIcon(this.getClass().getResource("/Player1.png")).getImage();
		pl1.setIcon(new ImageIcon(imgP1));
		pl1.setBounds(110, 612, 127, 34);
		frame.getContentPane().add(pl1);
		textField_player1 = new JTextField("");
		textField_player1.setBounds(100, 612, 114, 34);
		frame.getContentPane().add(textField_player1);
		textField_player1.setColumns(10);
		textField_p1_scores = new JTextField();
		textField_p1_scores.setBounds(226, 612, 54, 34);
		frame.getContentPane().add(textField_p1_scores);
		textField_p1_scores.setColumns(10);
		
		//Player 2
		pl2 = new JLabel("");
		Image imgP2 = new ImageIcon(this.getClass().getResource("/Player2.png")).getImage();
		pl2.setIcon(new ImageIcon(imgP2));
		pl2.setBounds(714, 617, 120, 27);
		frame.getContentPane().add(pl2);
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
		lblNewLabel_3.setBounds(-4, 597, 1037, 81);
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
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {	
			game.getPlayer1().goLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.getPlayer1().makeArrow();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		// Get info about player 2
		
		panel.repaint();
	}
}
