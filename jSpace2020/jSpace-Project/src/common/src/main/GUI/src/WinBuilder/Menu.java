package common.src.main.GUI.src.WinBuilder;


import java.awt.EventQueue;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;



public class Menu {
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Client clientObject = new Client();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
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
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		frame.getContentPane().setBackground(new Color(51, 102, 102));
		frame.setBounds(100, 100, 740, 480);
		frame.setResizable(false);
		//frame.setSize(500,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to Bubble Shooter");
		lblNewLabel.setBounds(109, 21, 582, 34);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 37));
		lblNewLabel.setForeground(UIManager.getColor("TextPane.inactiveBackground"));
		lblNewLabel.setBackground(UIManager.getColor("TextPane.inactiveBackground"));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(264, 100, 228, 226);
		panel.setBackground(SystemColor.window);
		panel.setForeground(new Color(47, 79, 79));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("RoomID");
		lblNewLabel_1.setBounds(51, 90, 85, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("UserName");
		lblNewLabel_1_1.setBounds(51, 33, 85, 16);
		panel.add(lblNewLabel_1_1);
		
		textField = new JTextField(); //name
		textField.setBounds(46, 52, 130, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(); //roomID
		textField_1.setColumns(10);
		textField_1.setBounds(46, 109, 130, 26);
		panel.add(textField_1);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(0, 0, 0));
		btnNewButton.setBounds(51, 161, 117, 29);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(6, 11, 317, 315);
		Image img = new ImageIcon(this.getClass().getResource("/bubbles-icon.png")).getImage();
		lblNewLabel_2.setIcon(new ImageIcon(img));
		frame.getContentPane().add(lblNewLabel_2);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Play Music");
		rdbtnNewRadioButton.setBounds(0, 0, 0, 0);
		rdbtnNewRadioButton.setForeground(new Color(255, 255, 255));
		frame.getContentPane().add(rdbtnNewRadioButton);
		
		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setBounds(-22, 11, 317, 315);
		frame.getContentPane().add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("");
		lblNewLabel_2_2.setBounds(423, 137, 317, 315);
		lblNewLabel_2_2.setIcon(new ImageIcon(img));
		frame.getContentPane().add(lblNewLabel_2_2);
		
		
	}
}