package mainMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

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
	private MainMenuManager manager;
	private Timer timer = new Timer();
	
	private JPanel leftBase1;
	private JPanel rightBase1;
	private JPanel rightBase2Bottom;
	
	private MenuPanel[] panels = new MenuPanel[6];
	private MainMenuListener[] listeners = new MainMenuListener[6];

	private MenuPanel titlePane; //Title Panel
	private MenuPanel playButtonPane; // Play Button
	private MenuPanel userInfoPane; //User Info Panel
	private MenuPanel settingButPane; // Settings Button
	private MenuPanel creditsPane; //Credits Panel
	private MenuPanel quitButPane; //Quit Button
	


	private Image dbImage;
	Color panelBasicColor;
	
	MainMenuListener hoverListener = new MainMenuListener();
	private MainMenuListener zz, zzz;
	
	public MainMenuPanel(MainMenuManager manager)
	{
		this.manager = manager;
		panelBasicColor = new Color((float)0.0,(float)0.0,(float)0.4,(float)0.6); //0.6float is 153 int
		try {
			dbImage = ImageIO.read(new File("assets/images/shard_metal.jpg"));
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
		
		titlePane = new MenuPanel("title");
		titlePane.setBackground(panelBasicColor);
		JLabel titleLabel = new JLabel("BOMBERMAN 2D", SwingConstants.CENTER);
//		titlePane.setLayout(new BorderLayout());
		titleLabel.setFont(new Font("neuropol x rg", Font.BOLD, 40));
		titleLabel.setForeground(Color.WHITE);
		titlePane.addCenterLabel(titleLabel);
		
		playButtonPane = new MenuPanel("Play");
		playButtonPane.addButtonPanel("PLAY", 40);
		playButtonPane.setBackgroundModified(panelBasicColor);
		
		//show right top, center, bot division
		GridLayout g3 = new GridLayout(3,0,0,5);
		rightBase1.setLayout(g3);
		userInfoPane = new MenuPanel("userinfo");
		userInfoPane.setBackground(panelBasicColor);
		settingButPane = new MenuPanel("Settings");
		settingButPane.addButtonPanel("SETTINGS", 40);
		settingButPane.setBackgroundModified(panelBasicColor);
		rightBase2Bottom = new JPanel();
		rightBase2Bottom.setOpaque(false);

		
		//show right bottom left,right division
		GridLayout g4 = new GridLayout(0,2,5,0);
		rightBase2Bottom.setLayout(g4);
		creditsPane = new MenuPanel("Credits");
		creditsPane.addButtonPanel("CREDITS", 35);
		creditsPane.setBackgroundModified(panelBasicColor);
		quitButPane = new MenuPanel("Quit");
		quitButPane.addButtonPanel("EXIT", 40);
		quitButPane.setBackgroundModified(panelBasicColor);

/*********************************************/
		rightBase1.add(userInfoPane);
		rightBase1.add(settingButPane);
		rightBase1.add(rightBase2Bottom);
/********************************************/
/********************************************/		
		leftBase1.add(titlePane);
		leftBase1.add(playButtonPane);
/********************************************/
/********************************************/
		rightBase2Bottom.add(creditsPane);
		rightBase2Bottom.add(quitButPane);
/********************************************/		
		
		//test2for listener
		titlePane.addMouseListener(new MainMenuListener());
		zzz = new MainMenuListener(playButtonPane, manager);
		playButtonPane.addMouseListener(zzz); // Play Button
		userInfoPane.addMouseListener(new MainMenuListener()); //User Info Panel
		zz = new MainMenuListener(settingButPane, manager);
		settingButPane.addMouseListener(zz); // Settings Button
		creditsPane.addMouseListener(new MainMenuListener(creditsPane, manager)); //Credits Panel
		quitButPane.addMouseListener(new MainMenuListener(quitButPane, manager));//Quit Button
		this.repaint();
	}
	
	
	
	public void disappear(int state)
	{
		timer.schedule(new DisappearUI(), 0, 50);
	}

	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   g.drawImage(dbImage, 0, 0, getWidth(), getHeight(), this);
	   repaint();
	}
	
	private class DisappearUI extends TimerTask {

//		boolean isAllZero = false;

		@Override
		public void run() {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					Color settingColor  = settingButPane.getBackground();
					settingButPane.removeMouseListener(zz);
//					isAllZero = true;
					if(settingColor.getAlpha() > 20)
					{
						settingButPane.setBackgroundModified(new Color(0, 0, 100, settingColor.getAlpha()-20));
						
//						isAllZero = false;
					}
					else if(settingColor.getAlpha() > 5)
					{
						settingButPane.setBackgroundModified(new Color(0, 0, 100, settingColor.getAlpha()-5));
					}
					else if(settingColor.getAlpha() > 1)
					{
						settingButPane.setBackgroundModified(new Color(0, 0, 100, settingColor.getAlpha()-1));
					}
					Color playColor = playButtonPane.getBackground();
					playButtonPane.removeMouseListener(zzz);
//					isAllZero = true;
					if(playColor.getAlpha() > 20)
					{
						playButtonPane.setBackgroundModified(new Color(0, 0, 100, playColor.getAlpha()-20));
//						isAllZero = false;
					}
					else if(playColor.getAlpha() > 5)
					{
						settingButPane.setBackgroundModified(new Color(0, 0, 100, playColor.getAlpha()-5));
					}
					else if(playColor.getAlpha() > 5)
					{
						settingButPane.setBackgroundModified(new Color(0, 0, 100, playColor.getAlpha()-1));
					}
					//userInfoPane
					//settingButPane
					//creditsPane
					//quitButPane

				}
			});
		}
	}
}
