package common.src.main.GUI.src.WinBuilder;

import java.awt.EventQueue;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;

public class NewMenu {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMenu window = new NewMenu();
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
	public NewMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(51, 102, 102));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.setBounds(169, 161, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Welcome to Bubble Shooter");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setForeground(UIManager.getColor("TextPane.inactiveBackground"));
		lblNewLabel.setBackground(UIManager.getColor("TextPane.inactiveBackground"));
		lblNewLabel.setBounds(109, 21, 260, 34);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(161, 78, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		Image img = new ImageIcon(this.getClass().getResource("/bubbles-icon.png")).getImage();
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(161, 122, 130, 26);
		frame.getContentPane().add(textField_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(img));
		lblNewLabel_2.setBounds(0, 16, 309, 249);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel label = new JLabel("New label");
		label.setBounds(167, 83, 61, 16);
		frame.getContentPane().add(label);
	}
}

