package common.src.main.GUI.src.WinBuilder;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Settings {

	private JFrame frmSetting;

	/**
	 * Launch the application.
	 */
	public static void SettingScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings window = new Settings();
					window.frmSetting.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Settings() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSetting = new JFrame();
		frmSetting.setTitle("Setting");
		frmSetting.getContentPane().setBackground(new Color(218, 165, 32));
		frmSetting.getContentPane().setLayout(null);
		frmSetting.setLocationRelativeTo(null);
		
		//Amount of lives
		JPanel panel = new JPanel();
		panel.setBounds(136, 62, 222, 73);
		frmSetting.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		//CheckBoxes
		JRadioButton checkBox1 = new JRadioButton("1");
		checkBox1.setBounds(6, 33, 45, 23);
		panel.add(checkBox1);
		
		JRadioButton checkBox2 = new JRadioButton("2");
		checkBox2.setBounds(47, 33, 45, 23);
		panel.add(checkBox2);
		
		JRadioButton checkBox3 = new JRadioButton("3");
		checkBox3.setBounds(88, 33, 45, 23);
		panel.add(checkBox3);
		
		JRadioButton checkBox4 = new JRadioButton("4");
		checkBox4.setBounds(129, 33, 45, 23);
		panel.add(checkBox4);
		
		JRadioButton checkBox5 = new JRadioButton("5");
		checkBox5.setBounds(170, 33, 45, 23);
		panel.add(checkBox5);
		
		JLabel lblNewLabel_2 = new JLabel("Amount of Lives");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNewLabel_2.setBounds(64, 6, 110, 16);
		panel.add(lblNewLabel_2);
		
		
		//Settings label
		JLabel lblNewLabel_1 = new JLabel("Setting");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBackground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblNewLabel_1.setBounds(210, 0, 102, 33);
		frmSetting.getContentPane().add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(35, 162, 431, 83);
		frmSetting.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Level");
		lblNewLabel.setBounds(190, 6, 42, 16);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		//Level check box
		JRadioButton checkLevel1 = new JRadioButton("1");
		checkLevel1.setBounds(6, 41, 47, 23);
		panel_1.add(checkLevel1);
		
		JRadioButton checkLevel2 = new JRadioButton("2");
		checkLevel2.setBounds(47, 41, 47, 23);
		panel_1.add(checkLevel2);

		JRadioButton checkLevel3 = new JRadioButton("3");
		checkLevel3.setBounds(88, 41, 47, 23);
		panel_1.add(checkLevel3);
		
		JRadioButton checkLevel4 = new JRadioButton("4");
		checkLevel4.setBounds(129, 41, 47, 23);
		panel_1.add(checkLevel4);
		
		JRadioButton checkLevel5 = new JRadioButton("5");
		checkLevel5.setBounds(170, 41, 47, 23);
		panel_1.add(checkLevel5);
		
		JRadioButton checkLevel6 = new JRadioButton("6");
		checkLevel6.setBounds(211, 41, 47, 23);
		panel_1.add(checkLevel6);
		
		JRadioButton checkLevel7 = new JRadioButton("7");
		checkLevel7.setBounds(252, 41, 47, 23);
		panel_1.add(checkLevel7);
		
		JRadioButton checkLevel8 = new JRadioButton("8");
		checkLevel8.setBounds(293, 41, 47, 23);
		panel_1.add(checkLevel8);
		
		JRadioButton checkLevel9 = new JRadioButton("9");
		checkLevel9.setBounds(334, 41, 47, 23);
		panel_1.add(checkLevel9);
		
		JRadioButton checkLevel10 = new JRadioButton("10");
		checkLevel10.setBounds(375, 41, 58, 23);
		panel_1.add(checkLevel10);
		
		//Save Settings
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBounds(181, 279, 136, 29);
		frmSetting.getContentPane().add(btnNewButton);
		
		//Background
		JLabel background = new JLabel("");
		background.setBounds(-6, 0, 517, 344);
		frmSetting.getContentPane().add(background);
		Image img = new ImageIcon(this.getClass().getResource("/bg.png")).getImage();
		background.setIcon(new ImageIcon(img));
		
		
		frmSetting.setBounds(100, 100, 491, 352);
		frmSetting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
