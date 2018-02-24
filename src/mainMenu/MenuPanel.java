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
	private String ID;
	
	private JLabel panelLabel = new JLabel("");
	
	public MenuPanel(String ID)
	{
		super();
		this.ID = ID;
		this.setLayout(new BorderLayout());

	}
	
	public void addButtonPanel(String label, int fontSize)
	{
		panelLabel = new JLabel(label, SwingConstants.RIGHT);
		panelLabel.setFont(new Font("FREEDOM", Font.BOLD, fontSize));
		panelLabel.setForeground(Color.white);
		this.add(panelLabel, BorderLayout.SOUTH);
	}
	
	public void addTitleLabel(String label, int fontSize)
	{
		panelLabel = new JLabel(label, SwingConstants.RIGHT);
		panelLabel.setFont(new Font("FREEDOM", Font.BOLD, fontSize));
		panelLabel.setForeground(Color.white);
		this.add(panelLabel, BorderLayout.CENTER);

	}
	
	public void addCenterLabel(JLabel label)
	{
		this.panelLabel = label;
		this.add(panelLabel, BorderLayout.CENTER);
	}
	
	
	
	public String getID()
	{
		return ID;
	}
	
	public JLabel getLabel()
	{
		return panelLabel;
	}
	
	public void setBackgroundModified(int r, int g, int b, int a)
	{
		this.setBackground(new Color(r,g,b,a));
		panelLabel.setBackground(new Color(r,g,b,a));
		panelLabel.setForeground(new Color(200,200,200,a));
	}
	
	public void setBackgroundModified(float r, float g, float b, float a)
	{
		this.setBackground(new Color(r,g,b,a));
		panelLabel.setBackground(new Color(r,g,b,a));
		panelLabel.setForeground(new Color(200,200,200,a));
	}
	
	public void setBackgroundModified(Color c)
	{
		this.setBackground(c);
		panelLabel.setBackground(c);
		panelLabel.setForeground(new Color(200,200,200, c.getAlpha()));
	}
	
	
	public void becomeInvisibleAnimation()
	{
		
	}
}
