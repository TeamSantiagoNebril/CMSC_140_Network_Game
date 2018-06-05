package mainMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MenuGameInputPanel extends MenuPanel implements MouseListener{

	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JTextField joinIpField;
	private JTextField joinPortNumber;
	private static final long serialVersionUID = 1L;
	private JButton createGameButton;
	private JButton joinGameButton;
	
	public MenuGameInputPanel(String ID) {
		super(ID);
		JPanel upPane = new JPanel();
		upPane.setLayout(new GridLayout(3,0,0,0));
		upPane.setOpaque(false);
		JLabel gameTypeLabel = new JLabel("GAME TYPE");
		gameTypeLabel.setForeground(Color.ORANGE);
		gameTypeLabel.setFont(new Font("neuropol x rg", Font.BOLD, 14));
		gameTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upPane.add(gameTypeLabel);
		
		upperPanel = new JPanel();
		upperPanel.setOpaque(false);
		upperPanel.setLayout(new GridLayout(0,2,0,0));
		
		
		createGameButton = new JButton("CREATE GAME");
		createGameButton.setBorderPainted(false);
		createGameButton.setBackground(Color.BLUE);
		createGameButton.setForeground(Color.ORANGE);
		createGameButton.setFont(new Font("neuropol x rg", Font.BOLD, 12));
		joinGameButton = new JButton("JOIN GAME");
		joinGameButton.setBorderPainted(false);
		joinGameButton.setBackground(Color.BLUE);
		joinGameButton.setForeground(Color.ORANGE);
		joinGameButton.setFont(new Font("neuropol x rg", Font.BOLD, 12));
		createGameButton.addMouseListener(this);
		joinGameButton.addMouseListener(this);
		upperPanel.add(createGameButton);
		upperPanel.add(joinGameButton);
		upPane.add(upperPanel);
		
		JLabel joinLabel = new JLabel("JOIN GAME INFORMATION");
		joinLabel.setForeground(Color.ORANGE);
		joinLabel.setFont(new Font("neuropol x rg", Font.BOLD, 14));
		joinLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upPane.add(joinLabel);
		
		lowerPanel = new JPanel();
		lowerPanel.setOpaque(false);
		lowerPanel.setLayout(new GridLayout(4,0,0,0));
		
		JLabel ipAddressLabel = new JLabel("IP ADDRESS:");
		ipAddressLabel.setOpaque(false);
		ipAddressLabel.setForeground(Color.ORANGE);
		ipAddressLabel.setFont(new Font("neuropol x rg", Font.PLAIN, 12));
		JLabel portNumberLabel = new JLabel("PORT NUMBER:");
		portNumberLabel.setOpaque(false);
		portNumberLabel.setForeground(Color.orange);
		portNumberLabel.setFont(new Font("neuropol x rg", Font.PLAIN, 12));
		joinIpField = new JTextField(15);
		joinIpField.setForeground(Color.ORANGE);
		joinIpField.setBackground(Color.DARK_GRAY);
		joinIpField.setFont(new Font("neuropol x rg", Font.PLAIN, 12));
		joinPortNumber = new JTextField(15);
		joinPortNumber.setForeground(Color.ORANGE);
		joinPortNumber.setBackground(Color.DARK_GRAY);
		joinPortNumber.setFont(new Font("neuropol x rg", Font.PLAIN, 12));
		lowerPanel.add(ipAddressLabel);
		lowerPanel.add(joinIpField);
		lowerPanel.add(portNumberLabel);
		lowerPanel.add(joinPortNumber);


		add(upPane, BorderLayout.NORTH);
		add(lowerPanel, BorderLayout.CENTER);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == createGameButton) {
			createGameButton.setBackground(Color.DARK_GRAY);
			joinGameButton.setBackground(Color.BLUE);

			joinPortNumber.setText("N/A");
			joinIpField.setText("N/A");
			joinIpField.setEditable(false);
			joinIpField.setEditable(false);
		}else if(e.getSource() == joinGameButton) {
			joinGameButton.setBackground(Color.DARK_GRAY);
			createGameButton.setBackground(Color.BLUE);
			joinPortNumber.setText("");
			joinIpField.setText("");
			joinIpField.setEditable(true);
			joinIpField.setEditable(true);

		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
	public String getJoinIP() {
		return joinIpField.getText();
	}
	
	public int getJoinPortNumber() {
		return Integer.parseInt(joinPortNumber.getText());
	}
}
