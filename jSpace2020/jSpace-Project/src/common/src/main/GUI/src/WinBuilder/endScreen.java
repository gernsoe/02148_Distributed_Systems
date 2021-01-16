package common.src.main.GUI.src.WinBuilder;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class endScreen {

	private JFrame frmEndScreen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					endScreen window = new endScreen();
					window.frmEndScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public endScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEndScreen = new JFrame();
		frmEndScreen.setTitle("End Screen");
		frmEndScreen.setBounds(100, 100, 450, 300);
		frmEndScreen.setSize(1000,700);
		frmEndScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEndScreen.getContentPane().setLayout(null);
		
		
		
		JButton btnNewButton_1 = new JButton("Back to Menu");
		btnNewButton_1.setBounds(852, 344, 117, 29);
		frmEndScreen.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Restart");
		btnNewButton.setBounds(118, 306, 165, 29);
		frmEndScreen.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/bg.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(0, 0, 817, 684);
		frmEndScreen.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(817, 70, 61, 16);
		frmEndScreen.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(781, 0, 256, 684);
		lblNewLabel_2.setIcon(new ImageIcon(img));
		frmEndScreen.getContentPane().add(lblNewLabel_2);
		


		
		

	}
}
