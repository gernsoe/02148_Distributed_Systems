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

public class GameRoom implements KeyListener, ActionListener {
	
	private Timer timer;
	private int delay = 17, playerHeight = 20, playerWidth = playerHeight/2;
	private JFrame frame;
	private JPanel panel;
	private Map game;
	private Color color = new Color(135, 206, 250);
	private int borderWidth = 800, borderHeight = 600;

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
		
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(new Color(165, 42, 42));
		frame.setResizable(false);
		frame.setSize(900,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image img = new ImageIcon(this.getClass().getResource("/Group1.png")).getImage();
		Image img1 = new ImageIcon(this.getClass().getResource("/Group2.png")).getImage();
		frame.getContentPane().setLayout(null);
		
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
				for(Bubble bubble : game.getBubbles()) {
					g.fillOval((int)bubble.getPos().getX(), (int)bubble.getPos().getY(), bubble.getSize(), bubble.getSize());
					bubble.move();
				}

				// g.dispose();
			}
		};
		panel.setBackground(Color.white);
		panel.setBounds(50, 0, borderWidth, borderHeight);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setFocusable(true);
		frame.getContentPane().add(panel);
		panel.addKeyListener(this);
		panel.setLayout(null);
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
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		// Get info about player 2
		
		panel.repaint();
	}
}
