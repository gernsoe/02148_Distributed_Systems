package common.src.main.GUI.src.WinBuilder;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class Help {

	private JFrame frmHelp;

	/**
	 * Launch the application.
	 */
	
	public static void HelpFunction() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Help window = new Help();
					window.frmHelp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Help() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHelp = new JFrame();
		frmHelp.setTitle("Help");
		frmHelp.setBounds(100, 100, 503, 378);
		frmHelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmHelp.getContentPane().setLayout(null);
		frmHelp.setLocationRelativeTo(null);
		frmHelp.setResizable(false);
		
		//Buttons
		JLabel buttons = new JLabel();
		buttons.setBounds(294, 25, 138, 102);
		Image btn = new ImageIcon(this.getClass().getResource("/btn.png")).getImage();
		buttons.setIcon(new ImageIcon(btn));
		frmHelp.getContentPane().add(buttons);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(288, 171, 189, 77);
		frmHelp.getContentPane().add(panel);
		panel.setLayout(null);
		
		//Text in panel - info
		JLabel left = new JLabel("Go left: Left key");
		left.setBounds(6, 6, 182, 16);
		panel.add(left);
		
		JLabel right = new JLabel("Go right: Right key");
		right.setBounds(6, 26, 182, 16);
		panel.add(right);
		
		JLabel up = new JLabel("Shoot: Up key or space key");
		up.setBounds(6, 46, 182, 16);
		panel.add(up);
		
		JLabel character1 = new JLabel("");
		character1.setBounds(24, 25, 94, 120);
		Image figure1 = new ImageIcon(this.getClass().getResource("/f1.png")).getImage();
		character1.setIcon(new ImageIcon(figure1));
		frmHelp.getContentPane().add(character1);
		
		JLabel character2 = new JLabel("");
		character2.setBounds(24, 171, 94, 134);
		Image figure2 = new ImageIcon(this.getClass().getResource("/f2.png")).getImage();
		character2.setIcon(new ImageIcon(figure2));
		frmHelp.getContentPane().add(character2);
		
		JLabel lblNewLabel = new JLabel("Player 1");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblNewLabel.setBounds(34, 145, 72, 16);
		frmHelp.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Player 2");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblNewLabel_1.setBounds(34, 305, 72, 16);
		frmHelp.getContentPane().add(lblNewLabel_1);
		
		//Background
		JLabel background = new JLabel("");
		background.setBounds(-6, 0, 519, 356);
		frmHelp.getContentPane().add(background);
		Image img = new ImageIcon(this.getClass().getResource("/bg.png")).getImage();
		background.setIcon(new ImageIcon(img));
		
	}

}
