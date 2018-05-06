package mainMenu;

import game.GamePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import menuStates.MainMenuPanel;
import menuStates.OptionsPanel;

public class MainMenuManager extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int menuState = 1;
	private MainMenuPanel mainPanel;
	private GamePanel gamePanel;
	public MainMenuManager()
	{
		super();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Bomberman");
		this.setLayout(new BorderLayout());
		loadFonts();
		mainPanel = new MainMenuPanel(this);
		
		
		//OptionsPanel optionsPanel = new OptionsPanel();
		//optionsPanel.setBackground(Color.BLACK);
		//this.add(optionsPanel, BorderLayout.CENTER);
		
		
		
		this.add(mainPanel, BorderLayout.CENTER);
		this.repaint();
		this.setVisible(true);//Always execute this after all is loaded to avoid inconsistency
	}
	
	public void changeState(int state)
	{
		switch(state)
		{
			case 1:
				break;
			case 2:
				mainPanel.disappear(2);
				gamePanel = new GamePanel();
				this.setContentPane(gamePanel);
				this.pack();
				gamePanel.requestFocusInWindow();
				gamePanel.addNotify();							//start Thread
				//System.exit(0);
				break;
			case 3:
				break;
			case 4: //Settings is pressed
				mainPanel.disappear(4);
				break;
			case 5:
				System.exit(0);
				break;
			default:
				break;
		}
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
	
	public void initState(int state) {
		switch(state)
		{
			case 1: //Title
				break;
			case 2: //Play
				break;
			case 3: //Player info
				break;
			case 4: //Settings is pressed
				if(menuState != state) {
					menuState = state;
					//System.out.println("went here");
					mainPanel.cancelTimer();
					this.remove(mainPanel);
					initOptionsGUI();
				}
				break;
			case 5: //Exit
				System.exit(0);
				break;
			case 6: //Credits
				
				break;
			default:
				break;
		}

	}
	
	private void initOptionsGUI() {
		OptionsPanel optionsPanel = new OptionsPanel();
		optionsPanel.setBackground(Color.BLACK);
		this.add(optionsPanel, BorderLayout.CENTER);
		this.repaint();
	}
	
}
