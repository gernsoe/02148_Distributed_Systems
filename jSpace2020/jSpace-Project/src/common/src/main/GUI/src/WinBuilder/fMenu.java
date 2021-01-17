package common.src.main.GUI.src.WinBuilder;

import java.awt.Color;
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
	private JTextField textField_Name;
	private JTextField textField_RoomID;
	private JButton btnNewButton;

	/**
	 * Create the application.
	 */
	public fMenu() {
		initialize();
		frame.setVisible(true);
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
		frame.setSize(1000,700);
		frame.setLocationRelativeTo(null);
		
		Image img = new ImageIcon(this.getClass().getResource("/bg.png")).getImage();
		
		JLabel lblNewLabel_1 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/windows.png")).getImage();
		Image img3 = new ImageIcon(this.getClass().getResource("/BubShooter.png")).getImage();
		
		
		btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*GameRoom gameRoom = new GameRoom();
				gameRoom.NewScreen();*/	
				
				//String name = textField_Name.getText();
				//String roomID = textField_RoomID.getText();
				//WaitingRoom.Waiting(name, roomID);
			}
		});
		
		btnNewButton.setBounds(447, 477, 117, 29);
		frame.getContentPane().add(btnNewButton); 
		//btnNewButton.addActionListener(new ActionListener());
		
		textField_RoomID = new JTextField();
		textField_RoomID.setBounds(411, 398, 188, 26);
		frame.getContentPane().add(textField_RoomID);
		textField_RoomID.setColumns(10);
		
		//RoomID
		JLabel roomIDLabel = new JLabel("RoomID"); 
		roomIDLabel.setForeground(new Color(255, 255, 255));
		roomIDLabel.setBounds(413, 380, 104, 16);
		frame.getContentPane().add(roomIDLabel);
		
		JLabel nameLabel = new JLabel("Name"); //Name
		nameLabel.setForeground(new Color(255, 255, 255));
		nameLabel.setBounds(415, 300, 115, 16);
		frame.getContentPane().add(nameLabel);
		
		textField_Name = new JTextField();
		textField_Name.setBounds(411, 319, 188, 26);
		frame.getContentPane().add(textField_Name);
		textField_Name.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/usnId.png")).getImage();
		lblNewLabel_6.setIcon(new ImageIcon(img2));
		lblNewLabel_6.setBounds(384, 205, 293, 420);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(img1));
		lblNewLabel_1_1.setBounds(826, 114, 144, 214);
		frame.getContentPane().add(lblNewLabel_1_1);
		lblNewLabel_1.setIcon(new ImageIcon(img1));
		lblNewLabel_1.setBounds(77, 114, 144, 214);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		
		//lblNewLabel_2.setIcon(new ImageIcon(img2));
		lblNewLabel_2.setBounds(285, 19, 618, 174);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(-14, 0, 904, 697);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(img3));
		lblNewLabel_5.setBounds(211, 6, 416, 133);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel NewLabel_bg = new JLabel("");
		NewLabel_bg.setBounds(756, -14, 258, 724);
		NewLabel_bg.setIcon(new ImageIcon(img));
		frame.getContentPane().add(NewLabel_bg);
	}
	public void closeWindow() {
		frame.setVisible(false);
	}

	public JButton getLoginButton() {
		return btnNewButton;
	}
	
	public void clearNameField() {
		textField_Name.setText("");
	}

	public void clearIDField() {
		textField_RoomID.setText("");
	}

	public String getName() {
		return textField_Name.getText();
	}
	public String getRoomID() {
		return textField_RoomID.getText();
	}

}
