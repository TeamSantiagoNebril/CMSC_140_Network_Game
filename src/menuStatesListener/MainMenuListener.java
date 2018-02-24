package menuStatesListener;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import mainMenu.MainMenuManager;
import mainMenu.MenuPanel;

public class MainMenuListener  extends MouseAdapter{

	private MainMenuManager manager;
	private int transparencyDisplacement;
	private MenuPanel component;
	private MouseEvent currentEvent;
	private Timer timer = new Timer();
	private int type = 0;
	private int running = 0;
	
	
	public MainMenuListener(MenuPanel g, MainMenuManager manager)
	{
		super();
		this.component = g;
		this.manager = manager;
	}
	
	public MainMenuListener()
	{
		super();
	}
	
	public void mouseClicked(MouseEvent e)
	{
		
		if(component != null)
		{
			if(component.getID().equals("Quit"))
			{
				manager.changeState(5);
			}
			else if(component.getID().equals("Play"))
			{
				manager.changeState(2);
			}
		}
		
	}
	
	
	public void mouseEntered(MouseEvent e)
	{
		
		type = 1;
		currentEvent = e;
		transparencyDisplacement = currentEvent.getComponent().getBackground().getAlpha();
		if(running == 0)
		{
			running = 1;
			timer.schedule(new UpdateUITask(), 0, 50);
		}
		running = 0;
	}
	
	private void increase()
	{
		currentEvent.getComponent().setBackground(new Color(0,0,100,transparencyDisplacement));
		currentEvent.getComponent().repaint();
		currentEvent.getComponent().revalidate();
		transparencyDisplacement += 20;
	}
	
	private void decrease()
	{
		currentEvent.getComponent().setBackground(new Color(0,0,100,transparencyDisplacement));
		currentEvent.getComponent().repaint();
		currentEvent.getComponent().revalidate();
		transparencyDisplacement -= 20;
	}
	
	public void mouseExited(MouseEvent e)
	{
		type = 2;
		currentEvent = e;
		transparencyDisplacement = e.getComponent().getBackground().getAlpha();
		if(running == 0)
		{
			running = 1;
			timer.schedule(new UpdateUITask(), 0, 50);
		}
		running = 0;
		//System.out.println(transparencyDisplacement);
	}

	private class UpdateUITask extends TimerTask {

		int nSeconds = 0;

		@Override
		public void run() {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					//timeLabel.setText(String.valueOf(nSeconds++));
					if(type == 1)
					{
						if(transparencyDisplacement < 245)
						{
							running = 1;
							increase();
						}
					}
					else if(type == 2)
					{
						if(transparencyDisplacement > 153)
						{
							running  = 1;
							decrease();
						}
					}
					

				}
			});
		}
	}
}
