package mainMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainMenuManager extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainMenuManager()
	{
		super();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Bomberman");
		this.setLayout(new BorderLayout());
		loadFonts();
		MainMenuPanel mainPanel = new MainMenuPanel();
		this.add(mainPanel, BorderLayout.CENTER);
		this.repaint();
		this.setVisible(true);//Always execute this after all is loaded to avoid inconsistency
	}
	
	private void loadFonts()
	{
		try {
			   GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			   ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/neuropol x rg.ttf")));
			   ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/FREEDOM.ttf")));
			} catch (IOException|FontFormatException e) {
			 System.out.println("something is wrong");
			 System.out.println(e);
			}
	}
	
}
