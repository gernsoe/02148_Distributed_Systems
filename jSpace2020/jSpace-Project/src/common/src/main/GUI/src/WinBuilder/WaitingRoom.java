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
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class WaitingRoom {

	private JFrame frmWaitingRoom;
	private String name;
	private String roomID;
	private String roomURI;
	private String permission;
	private JLabel textField_User2, textField_User1;
	private JButton btnStart;
	private JLabel label_figure1;

	private JLabel label_figure2;
	private JButton btnLeave;
	private JLabel lblRoomID;
	private String lRoomID;
	private JLabel lblWaiting;
	private JPopupMenu popupMenu;
	private JMenuBar menuBar;
	private JMenu settings;
	private JMenu help;

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
		label_figure1.setVisible(false);
		label_figure2.setVisible(false);
		
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
		
		lblWaiting = new JLabel("Waiting for host to start game...");
		lblWaiting.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		lblWaiting.setBounds(391, 458, 278, 29);
		frmWaitingRoom.getContentPane().add(lblWaiting);
		lblWaiting.setVisible(false);
		
		
		
	
		//Leave Room
		btnLeave = new JButton("Leave Room");
		btnLeave.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnLeave.setBounds(438, 325, 117, 29);
		btnLeave.setVisible(false);
		btnLeave.setEnabled(false);
		frmWaitingRoom.getContentPane().add(btnLeave);
		
		//Logo
		JLabel lblNewLabel_3 = new JLabel();
		Image img2 = new ImageIcon(this.getClass().getResource("/BubShooter.png")).getImage();
		
		btnStart = new JButton("Start Game");
		btnStart.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnStart.setBounds(438, 282, 117, 29);
		btnStart.setVisible(false);
		btnStart.setEnabled(false);
		frmWaitingRoom.getContentPane().add(btnStart);
		
		//Character 1
		lblNewLabel_3.setIcon(new ImageIcon(img2));
		lblNewLabel_3.setBounds(304, 44, 544, 158);
		frmWaitingRoom.getContentPane().add(lblNewLabel_3);
		
		//Figure 1
		label_figure1 = new JLabel("");
		Image imgFigure1 = new ImageIcon(this.getClass().getResource("/f1.png")).getImage();
		label_figure1.setIcon(new ImageIcon(imgFigure1));
		label_figure1.setBounds(146, 402, 96, 130);
		frmWaitingRoom.getContentPane().add(label_figure1);
		label_figure1.setVisible(false);
		
		//Character 2
		label_figure2 = new JLabel("");
		Image imgFigure2 = new ImageIcon(this.getClass().getResource("/f2.png")).getImage();
		label_figure2.setBounds(760, 375, 88, 175);
		frmWaitingRoom.getContentPane().add(label_figure2);
		label_figure2.setIcon(new ImageIcon(imgFigure2));

		//User texfields
		textField_User2 = new JLabel();
		textField_User2.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		textField_User2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_User2.setBounds(719, 571, 163, 26);
		frmWaitingRoom.getContentPane().add(textField_User2);
		
		textField_User1 = new JLabel();
		textField_User1.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		textField_User1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_User1.setBounds(105, 571, 163, 26);
		frmWaitingRoom.getContentPane().add(textField_User1);
		
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
	
		//Menu Bar
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 132, 22);
		frmWaitingRoom.getContentPane().add(menuBar);
		
		//MENU - Settings
		settings = new JMenu("Settings");
		//mnNewMenu.setBounds(868, 0, 124, 19);
		menuBar.add(settings);
		JMenuItem itemSet = new JMenuItem("Settings");
		settings.add(itemSet);
		
		itemSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.SettingScreen();	
			}
		});
		

		//MENU - Help 
		help = new JMenu("Help");
		menuBar.add(help);
		JMenuItem itemHelp = new JMenuItem("Help");
		help.add(itemHelp);
		
		itemHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.HelpFunction();	
			}
		});
		
		
		//Room ID label
		lblRoomID = new JLabel();
		lblRoomID.setBounds(391, 375, 239, 39);
		frmWaitingRoom.getContentPane().add(lblRoomID);
		lblRoomID.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblRoomID.setHorizontalAlignment(SwingConstants.CENTER);
		
		
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

	public void toggleFigure1(){
		if (label_figure1.isVisible()) {
			label_figure1.setVisible(false); 
		} else {
			label_figure1.setVisible(true);
		}
	}
	
	public void toggleFigure2(){
		if (label_figure2.isVisible()) {
			label_figure2.setVisible(false); 
		} else {
			label_figure2.setVisible(true);
		}
	}
	
	public void createLeaveButton() {
		btnLeave.setVisible(true);
		btnLeave.setEnabled(true);
	}
	
	public JButton getLeaveButton() {
		return btnLeave;
	}
	
	public void createStartButton() {
		btnStart.setVisible(true);
		btnStart.setEnabled(true);
	}

	public void hostGame() {
		lblWaiting.setVisible(true);
	
	}
	
	public JButton getStartButton() {
		return btnStart;
	}
	
	public void setRoomID(String lRoomID) {
		lblRoomID.setText(lRoomID);
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
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}