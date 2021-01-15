package common.src.main.GUI.src.WinBuilder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class fMenu {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//showWindow();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fMenu window = new fMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {

	}

	/**
	 * Create the application.
	 */
	public fMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Start Window");

		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 782, 546);
		frame.setResizable(false);
		//frame.setSize(500,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Image img = new ImageIcon(this.getClass().getResource("/bg.png")).getImage();
		
		JLabel lblNewLabel_1 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/windows.png")).getImage();
		Image img3 = new ImageIcon(this.getClass().getResource("/BubShooter.png")).getImage();
		
		
		JButton btnNewButton = new JButton("Start Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*GameRoom gameRoom = new GameRoom();
				gameRoom.NewScreen();	*/
				
				WaitingRoom waitingRoom = new WaitingRoom();
				// waitingRoom.Waiting();
			}
		});
		
		btnNewButton.setBounds(323, 398, 117, 29);
		frame.getContentPane().add(btnNewButton); 
		//btnNewButton.addActionListener(new ActionListener());
		
		textField_1 = new JTextField();
		textField_1.setBounds(300, 342, 167, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("RoomID");
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setBounds(302, 324, 104, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("Name");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(302, 262, 115, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(300, 286, 167, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/usnId.png")).getImage();
		lblNewLabel_6.setIcon(new ImageIcon(img2));
		lblNewLabel_6.setBounds(272, 218, 235, 268);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(img1));
		lblNewLabel_1_1.setBounds(639, 114, 144, 214);
		frame.getContentPane().add(lblNewLabel_1_1);
		lblNewLabel_1.setIcon(new ImageIcon(img1));
		lblNewLabel_1.setBounds(41, 114, 144, 214);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		
		//lblNewLabel_2.setIcon(new ImageIcon(img2));
		lblNewLabel_2.setBounds(182, 6, 618, 174);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(-11, 0, 811, 534);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(img3));
		lblNewLabel_5.setBounds(211, 6, 416, 133);
		frame.getContentPane().add(lblNewLabel_5);
		
	}
}
