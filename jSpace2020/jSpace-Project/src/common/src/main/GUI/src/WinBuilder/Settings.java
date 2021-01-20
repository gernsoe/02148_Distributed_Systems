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
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;

public class Settings {

	public JFrame frmSetting;
	public Map<String, JRadioButton> checkHearts = new HashMap<String, JRadioButton>();
	public Map<String, JRadioButton> checkLevels = new HashMap<String, JRadioButton>();
	public JButton saveSettingsBtn;

	/**
	 * Launch the application.
	 */
	/*
	public static void SettingScreen(WaitingRoom wRoom) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings window = new Settings(wRoom);
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
	public Settings(WaitingRoom wRoom) {
		initialize(wRoom);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(WaitingRoom wRoom) {
		frmSetting = new JFrame();
		frmSetting.setTitle("Setting");
		frmSetting.getContentPane().setBackground(new Color(218, 165, 32));
		frmSetting.getContentPane().setLayout(null);
		
		//Amount of lives
		JPanel panel = new JPanel();
		panel.setBounds(136, 62, 222, 73);
		frmSetting.getContentPane().add(panel);
		panel.setLayout(null);
		frmSetting.setLocationRelativeTo(null);

		for (int i = 0; i < 5; ++i) {
			JRadioButton checkBox = new JRadioButton("" + (i+1));
			checkBox.setBounds(6 + 41*i, 33, 45, 23);
			panel.add(checkBox); 
			checkBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkBox.isSelected()) {	
						for (int i = 0; i < checkHearts.size(); ++i) {
							checkHearts.get("lives"+i).setSelected(false);
						}
						checkBox.setSelected(true);
					}
				}
			});
			checkHearts.put("lives" + i, checkBox);
		}
		
		// Default starting lives
		checkHearts.get("lives"+4).setSelected(true);

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
		for (int i = 0; i < 10; ++i) {
			JRadioButton checkLevel = new JRadioButton("" + (i+1));
			checkLevel.setBounds(6 + 41*i, 41, 47, 23);
			panel_1.add(checkLevel); 
			checkLevel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkLevel.isSelected()) {	
						for (int i = 0; i < checkLevels.size(); ++i) {
							checkLevels.get("level"+i).setSelected(false);
						}
						checkLevel.setSelected(true);
					}
				}
			});
			checkLevels.put("level" + i, checkLevel);
		}

		// Default starting levels
		checkLevels.get("level"+0).setSelected(true);
		for (int i = 0; i < 10; ++i) {
			JRadioButton checkLevel = new JRadioButton("" + (i+1));
			checkLevel.setBounds(6 + 41*i, 41, 47, 23);
			panel_1.add(checkLevel); 
			checkLevel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkLevel.isSelected()) {	
						for (int i = 0; i < checkLevels.size(); ++i) {
							checkLevels.get("level"+i).setSelected(false);
						}
						checkLevel.setSelected(true);
					}
				}
			});
			checkLevels.put("level" + i, checkLevel);
		}

		// Default starting levels
		checkLevels.get("level"+0).setSelected(true);
		for (int i = 0; i < 10; ++i) {
			JRadioButton checkLevel = new JRadioButton("" + (i+1));
			checkLevel.setBounds(6 + 41*i, 41, 47, 23);
			panel_1.add(checkLevel); 
			checkLevel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkLevel.isSelected()) {	
						for (int i = 0; i < checkLevels.size(); ++i) {
							checkLevels.get("level"+i).setSelected(false);
						}
						checkLevel.setSelected(true);
					}
				}
			});
			checkLevels.put("level" + i, checkLevel);
		}

		// Default starting levels
		checkLevels.get("level"+0).setSelected(true);
		for (int i = 0; i < 10; ++i) {
			JRadioButton checkLevel = new JRadioButton("" + (i+1));
			checkLevel.setBounds(6 + 41*i, 41, 47, 23);
			panel_1.add(checkLevel); 
			checkLevel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkLevel.isSelected()) {	
						for (int i = 0; i < checkLevels.size(); ++i) {
							checkLevels.get("level"+i).setSelected(false);
						}
						checkLevel.setSelected(true);
					}
				}
			});
			checkLevels.put("level" + i, checkLevel);
		}

		// Default starting levels
		checkLevels.get("level"+0).setSelected(true);
		
		//Save Settings
		saveSettingsBtn = new JButton("Save");
		saveSettingsBtn.setBounds(181, 279, 136, 29);
		frmSetting.getContentPane().add(saveSettingsBtn);
		
		//Background
		JLabel background = new JLabel("");
		background.setBounds(-6, 0, 517, 344);
		frmSetting.getContentPane().add(background);
		Image img = new ImageIcon(this.getClass().getResource("/bg.png")).getImage();
		background.setIcon(new ImageIcon(img));
		
		
		frmSetting.setBounds(100, 100, 491, 352);
		frmSetting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public JButton getSaveSettingsButton() {
		return saveSettingsBtn;
	}

	public void closeWindow() {
		frmSetting.setVisible(false);
	}
}
