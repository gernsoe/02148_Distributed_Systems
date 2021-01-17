package common.src.main.GUI.src.WinBuilder;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jspace.RemoteSpace;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class WaitingRoom {

	private JFrame frmWaitingRoom;
	private String name;
	private String roomID;
	private String roomURI;
	private String permission;
	private JTextField textField_User2;
	private JTextField textField_User1;
	private JButton btnNewButton;
	

	/**
	 * Launch the application.
	 */
	/*
	public static void Waiting(String name, String roomID) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WaitingRoom window = new WaitingRoom(name, roomID);
					window.frmWaitingRoom.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
	public WaitingRoom(String name, String roomID) {
		initialize();
		this.name = name;
		this.roomID = roomID;
		frmWaitingRoom.setVisible(true);
		//startWaiting();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWaitingRoom = new JFrame();
		
		frmWaitingRoom.setTitle("Waiting Room");
		frmWaitingRoom.setBounds(100, 100, 782, 546);
		frmWaitingRoom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWaitingRoom.getContentPane().setLayout(null);
		frmWaitingRoom.setSize(1000,700);
		frmWaitingRoom.setLocationRelativeTo(null);
		
		
		//Logo
		JLabel lblNewLabel_3 = new JLabel();
		Image img2 = new ImageIcon(this.getClass().getResource("/BubShooter.png")).getImage();
		lblNewLabel_3.setIcon(new ImageIcon(img2));
		lblNewLabel_3.setBounds(261, 44, 544, 158);
		frmWaitingRoom.getContentPane().add(lblNewLabel_3);

		//User texfields
		textField_User2 = new JTextField();
		textField_User2.setBounds(719, 571, 163, 26);
		frmWaitingRoom.getContentPane().add(textField_User2);
		textField_User2.setColumns(10);
		
		textField_User1 = new JTextField();
		textField_User1.setBounds(105, 571, 163, 26);
		frmWaitingRoom.getContentPane().add(textField_User1);
		textField_User1.setColumns(10);
		
		//Sofa
		JLabel lblNewLabel_2 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/Sofa-icon.png")).getImage();
		lblNewLabel_2.setIcon(new ImageIcon(img1));
		lblNewLabel_2.setBounds(736, 420, 137, 150);
		frmWaitingRoom.getContentPane().add(lblNewLabel_2);
		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setIcon(new ImageIcon(img1));
		lblNewLabel_1.setBounds(120, 431, 214, 130);
		frmWaitingRoom.getContentPane().add(lblNewLabel_1);
		lblNewLabel_1.setText(name);
	
		//Background
		Image img = new ImageIcon(this.getClass().getResource("/bg.png")).getImage();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(-11, 0, 793, 678);
		frmWaitingRoom.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(img));
		lblNewLabel_4.setBounds(760, 0, 320, 678);
		frmWaitingRoom.getContentPane().add(lblNewLabel_4);
		
	}

	public void createStartButton() {
		//Button
		btnNewButton = new JButton("Start Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {}
		});
		btnNewButton.setBounds(438, 282, 117, 29);
		frmWaitingRoom.getContentPane().add(btnNewButton);
	}

	public JButton getStartButton() {
		return btnNewButton;
	}

	public void setUserName1(String name) {
		textField_User1.setText(name);
	}

	public void setUserName2(String name) {
		textField_User2.setText(name);
	}

	public void closeWindow() {
		frmWaitingRoom.setVisible(false);
	}
}