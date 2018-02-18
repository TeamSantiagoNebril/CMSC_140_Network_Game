package mainMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainMenuPanel extends JPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel leftBase1;
	private JPanel rightBase1;
	
	private JPanel titlePane; //Title Panel
	private MenuPanel playButtonPane; // Play Button
	private JPanel userInfoPane; //User Info Panel
	private MenuPanel settingButPane; // Settings Button
	private JPanel rightBase2Bottom;
	
	private MenuPanel creditsPane; //Credits Panel
	private MenuPanel quitButPane; //Quit Button
	private Image dbImage;
	Color panelBasicColor;
	
	MainMenuListener hoverListener = new MainMenuListener();
	
	public MainMenuPanel()
	{
		panelBasicColor = new Color((float)0.0,(float)0.0,(float)0.4,(float)0.6); //0.6float is 153 int
		try {
			dbImage = ImageIO.read(new File("assets/images/test2.jpg"));
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenMarginWidth = (int)(screenSize.getWidth() * 0.10);
		int screenMarginHeight = (int)(screenSize.getHeight() * 0.10);
		
		//show left and right division
		GridLayout g1 = new GridLayout(0, 2, 5 , 0);
		this.setLayout(g1);
		
		this.setBorder(new EmptyBorder(screenMarginHeight, screenMarginWidth, screenMarginWidth, screenMarginWidth)); //EmptyBorder(top,left,bottom,right)
		leftBase1 = new JPanel();
		leftBase1.setOpaque(false);
		rightBase1 = new JPanel();
		rightBase1.setOpaque(false);
		this.add(leftBase1);
		this.add(rightBase1);
		
		//show left top and bot division
		GridLayout g2 = new GridLayout(2,0,0,5);
		leftBase1.setLayout(g2);
		titlePane = new JPanel();
		titlePane.setBackground(panelBasicColor);
		/*Test*/
		JLabel titleLabel = new JLabel("BOMBERMAN 2D", SwingConstants.CENTER);
		titlePane.setLayout(new BorderLayout());
		
		titleLabel.setFont(new Font("neuropol x rg", Font.BOLD, 40));
		titleLabel.setForeground(Color.WHITE);
		titlePane.add(titleLabel, BorderLayout.CENTER);
		
		
		playButtonPane = new MenuPanel("PLAY", 40);
		playButtonPane.setBackground(panelBasicColor);
		leftBase1.add(titlePane);
		leftBase1.add(playButtonPane);
		
		//show right top, center, bot division
		GridLayout g3 = new GridLayout(3,0,0,5);
		rightBase1.setLayout(g3);
		userInfoPane = new JPanel();
		userInfoPane.setBackground(panelBasicColor);
		settingButPane = new MenuPanel("SETTINGS", 40);
		settingButPane.setBackground(panelBasicColor);
		rightBase2Bottom = new JPanel();
		rightBase2Bottom.setOpaque(false);
		rightBase1.add(userInfoPane);
		rightBase1.add(settingButPane);
		rightBase1.add(rightBase2Bottom);
		
		//show right bottom left,right division
		GridLayout g4 = new GridLayout(0,2,5,0);
		rightBase2Bottom.setLayout(g4);
		creditsPane = new MenuPanel("CREDITS", 35);
		creditsPane.setBackground(panelBasicColor);
		quitButPane = new MenuPanel("EXIT", 40);
		quitButPane.setBackground(panelBasicColor);
		rightBase2Bottom.add(creditsPane);
		rightBase2Bottom.add(quitButPane);
		
		
		//test2for lisetener
		titlePane.addMouseListener(new MainMenuListener());
		playButtonPane.addMouseListener(new MainMenuListener()); // Play Button
		userInfoPane.addMouseListener(new MainMenuListener()); //User Info Panel
		settingButPane.addMouseListener(new MainMenuListener()); // Settings Button
		rightBase2Bottom.addMouseListener(new MainMenuListener());
		creditsPane.addMouseListener(new MainMenuListener()); //Credits Panel
		quitButPane.addMouseListener(new MainMenuListener());//Quit Button
		
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   g.drawImage(dbImage, 0, 0, getWidth(), getHeight(), this);
	   repaint();
	}
}
