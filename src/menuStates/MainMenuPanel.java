package menuStates;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mainMenu.MainMenuManager;
import mainMenu.MenuGameInputPanel;
import mainMenu.MenuInputPanel;
import mainMenu.MenuPanel;
import menuStatesListener.MainMenuListener;

public class MainMenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private MainMenuManager manager;
	private Timer timer = new Timer();
	
	private JPanel leftBase1;
	private JPanel rightBase1;
	private JPanel rightBase2Bottom;
	
	private MenuPanel[] panels = new MenuPanel[6];
	private MainMenuListener[] listeners = new MainMenuListener[6];
	/*MenuPanel corresponds to button panels, listener array is listener to these panels
	 * [0] - Title Panel
	 * [1] - Play Button
	 * [2] - User Info Panel
	 * [3] - Settings Button
	 * [4] - Credits Button
	 * [5] - QUit Button
	 */

	private Image dbImage;
	Color panelBasicColor;
	
	MainMenuListener hoverListener = new MainMenuListener();
	
	private MainMenuPanel thisMainMenuPanel = this;
	
	public MainMenuPanel(MainMenuManager manager)
	{
		this.manager = manager;
		panelBasicColor = new Color((float)0.0,(float)0.0,(float)0.4,(float)0.8); //0.6float is 153 int
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
		
		panels[0] = new MenuPanel("title");
		panels[0].setBackground(panelBasicColor);
		JLabel titleLabel = new JLabel("BOMBERMAN 2D", SwingConstants.CENTER);
		titleLabel.setFont(new Font("neuropol x rg", Font.BOLD, 40));
		titleLabel.setForeground(Color.WHITE);
		panels[0].addCenterLabel(titleLabel);
		
		
		panels[1] = new MenuPanel("Play");
		panels[1].addButtonPanel("START", 40);
		panels[1].setBackgroundModified(panelBasicColor);
		
		//show right top, center, bot division
		GridLayout g3 = new GridLayout(3,0,0,5);
		rightBase1.setLayout(g3);
		panels[2] = new MenuInputPanel("userinfo");
		panels[2].setBackground(panelBasicColor);
		panels[3] = new MenuGameInputPanel("Settings");
		panels[3].setBackgroundModified(panelBasicColor);
		rightBase2Bottom = new JPanel();
		rightBase2Bottom.setOpaque(false);
		
		//show right bottom left,right division
		GridLayout g4 = new GridLayout(0,2,5,0);
		rightBase2Bottom.setLayout(g4);
		panels[4] = new MenuPanel("Credits");
//		panels[4].addButtonPanel("CREDITS", 35);
		panels[4].setBackgroundModified(panelBasicColor);
		panels[5] = new MenuPanel("Quit");
		panels[5].addButtonPanel("EXIT", 40);
		panels[5].setBackgroundModified(panelBasicColor);

		rightBase1.add(panels[2]);
		rightBase1.add(panels[3]);
		rightBase1.add(rightBase2Bottom);
		leftBase1.add(panels[0]);//titlePane
		leftBase1.add(panels[1]);//playButtonPane
		rightBase2Bottom.add(panels[4]);
		rightBase2Bottom.add(panels[5]);
		
		/*listeners*/
		for(int counter = 0; counter < 6; counter++)
		{
			if(counter == 0 || counter == 2)
			{
				listeners[counter] = new MainMenuListener();
				panels[counter].addMouseListener(listeners[counter]);
			}
			else
			{
				listeners[counter] = new MainMenuListener(panels[counter], manager);
				panels[counter].addMouseListener(listeners[counter]);
			}
		}
		this.repaint();
	}
	
	
	private int currentStateSelected;
	public void disappear(int state)
	{
		currentStateSelected = state;
		timer.schedule(new DisappearUI(), 0, 50);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   g.drawImage(dbImage, 0, 0, getWidth(), getHeight(), this);
	   repaint();
	}
	
	public void cancelTimer() {
		timer.cancel();
	}
	
	private class DisappearUI extends TimerTask {
		
		@Override
		public void run() {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					for(int counter = 0; counter < 6; counter++)
					{
						Color panelColor = panels[counter].getBackground();
						panels[counter].removeMouseListener(listeners[counter]);
						if(panelColor.getAlpha() > 20)
						{
							panels[counter].setBackgroundModified(new Color(0, 0, 100, panelColor.getAlpha()-20));
						}
						else if(panelColor.getAlpha() > 5)
						{
							panels[counter].setBackgroundModified(new Color(0, 0, 100, panelColor.getAlpha()-5));
						}
						else if(panelColor.getAlpha() >= 1)
						{
							panels[counter].setBackgroundModified(new Color(0, 0, 100, panelColor.getAlpha()-1));
						}
						
						if(panelColor.getAlpha() == 0) {
							thisMainMenuPanel.removeAll();
							manager.initState(currentStateSelected);
						}
					}
				}
			});
		}
		
		
	}
}
