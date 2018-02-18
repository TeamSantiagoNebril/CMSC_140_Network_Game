package mainMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel panelLabel;
	
	public MenuPanel(String label, int fontSize)
	{
		super();
		this.setLayout(new BorderLayout());
		panelLabel = new JLabel(label, SwingConstants.RIGHT);
		panelLabel.setFont(new Font("FREEDOM", Font.BOLD, fontSize));
		panelLabel.setForeground(Color.white);
		this.add(panelLabel, BorderLayout.SOUTH);
	}
	
	public void setImage()
	{
		
	}

}
