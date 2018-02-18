package mainMenu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class MainMenuListener  extends MouseAdapter{

	private int transparencyDisplacement;
	private MouseEvent g;
	private Timer timer = new Timer();
	private int type = 0;
	private int running = 0;
	
	public MainMenuListener()
	{
		super();
	}
	
	public void mouseEntered(MouseEvent e)
	{
		
		type = 1;
		g = e;
		transparencyDisplacement = e.getComponent().getBackground().getAlpha();
		if(running == 0)
		{
			running = 1;
			timer.schedule(new UpdateUITask(), 0, 50);
		}
		running = 0;
		//System.out.println(transparencyDisplacement);
	}
	
	private void increase()
	{
		g.getComponent().setBackground(new Color(0,0,100,transparencyDisplacement));
		g.getComponent().repaint();
		g.getComponent().revalidate();
		transparencyDisplacement += 20;
	}
	
	private void decrease()
	{
		g.getComponent().setBackground(new Color(0,0,100,transparencyDisplacement));
		g.getComponent().repaint();
		g.getComponent().revalidate();
		transparencyDisplacement -= 20;
	}
	
	public void mouseExited(MouseEvent e)
	{
		type = 2;
		g = e;
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
