package mainMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ButtonPanel extends MenuPanel{

	public ButtonPanel(String ID) {
		super(ID);
	}
	
	
	public void addTitleLabel(String label, int fontSize)
	{
		panelLabel = new JLabel(label, SwingConstants.CENTER);
		panelLabel.setFont(new Font("FREEDOM", Font.BOLD, fontSize));
		panelLabel.setForeground(Color.BLACK);
		this.add(panelLabel, BorderLayout.CENTER);

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
