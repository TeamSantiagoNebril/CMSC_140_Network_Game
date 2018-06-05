package mainMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MenuInputPanel extends MenuPanel{

	private JPanel inputPanel;
	private JTextField selfIpField;
	private JTextField selfPortNumber;
	private Image bomberImage;
	private static final long serialVersionUID = 1L;
	
	public MenuInputPanel(String ID) {
		super(ID);
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("assets/images/White Bomberman png2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		add(picLabel, BorderLayout.WEST);
		inputPanel = new JPanel();
		inputPanel.setOpaque(false);
		inputPanel.setLayout(new GridLayout(7,0,0,0));
		JLabel titleLabel = new JLabel("PLAYER NETWORK INFORMATION");
		titleLabel.setFont(new Font("neuropol x rg", Font.BOLD, 14));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setOpaque(false);
		titleLabel.setForeground(Color.ORANGE);
		JLabel ipAddressLabel = new JLabel("IP ADDRESS:");
		ipAddressLabel.setOpaque(false);
		ipAddressLabel.setForeground(Color.ORANGE);
		ipAddressLabel.setFont(new Font("neuropol x rg", Font.PLAIN, 12));
		JLabel portNumberLabel = new JLabel("PORT NUMBER:");
		portNumberLabel.setOpaque(false);
		portNumberLabel.setForeground(Color.orange);
		portNumberLabel.setFont(new Font("neuropol x rg", Font.PLAIN, 12));
		selfIpField = new JTextField(15);
		selfIpField.setForeground(Color.ORANGE);
		selfIpField.setBackground(Color.DARK_GRAY);
		selfIpField.setFont(new Font("neuropol x rg", Font.PLAIN, 12));
		selfPortNumber = new JTextField(15);
		selfPortNumber.setForeground(Color.ORANGE);
		selfPortNumber.setBackground(Color.DARK_GRAY);
		selfPortNumber.setFont(new Font("neuropol x rg", Font.PLAIN, 12));
		inputPanel.add(titleLabel);
		inputPanel.add(ipAddressLabel);
		inputPanel.add(selfIpField);
		inputPanel.add(portNumberLabel);
		inputPanel.add(selfPortNumber);
		add(inputPanel, BorderLayout.CENTER);
	}
	
	
	public String getSelfIp() {
		return selfIpField.getText();
	}
	
	public int getSelfPortNumber() {
		return Integer.parseInt(selfPortNumber.getText());
	}
	
}
