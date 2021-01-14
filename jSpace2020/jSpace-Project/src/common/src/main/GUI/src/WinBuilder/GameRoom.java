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
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;

import common.src.main.Player;
import common.src.main.Point;

public class GameRoom implements KeyListener, ActionListener {
	
	private Timer timer;
	private int delay = 8;
	private JFrame frame;
	private JPanel panel;
	private Player player;
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
		player = new Player(new Point(borderWidth/2,borderHeight-50), borderWidth, "David");
		
		
		// Add GUI
		frame = new JFrame("Game Room");

		frame.getContentPane().setBackground(new Color(165, 42, 42));
		frame.setResizable(false);
		frame.setSize(900,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image img = new ImageIcon(this.getClass().getResource("/Group1.png")).getImage();
		Image img1 = new ImageIcon(this.getClass().getResource("/Group2.png")).getImage();
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				
				g.setColor(Color.red);
				g.fillRect(((int)player.getX()), ((int)player.getY()), 6, 12);
			}
		};
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.goRight();
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {	
			player.goLeft();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		panel.repaint();
	}
}
