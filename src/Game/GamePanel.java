package Game;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private Boolean running;
	
	private int FPS = 30;
	private int targetTime = 1000/FPS;
	
	private TileMap tileMap;
	
	private BufferedImage bufferedImage;
	private Graphics2D g;
	
	private int height;
	private int width;
	
	public GamePanel(){
		super();
		System.out.println("bea");
		width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		setVisible(true);
		requestFocus();
	}
	
	public void addNotify(){
		super.addNotify();     //Keano adto gin susuper?
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run(){
		init();
		long startTime;
		long waitTime;
		long urdTime;
		while(running){
			startTime = System.nanoTime();
			
			update();
			render();
			draw();
			
			urdTime = (System.nanoTime() - startTime)/1000000;
			waitTime = targetTime - urdTime;
			try{
				Thread.sleep(waitTime);
			}catch(Exception e){}
		}
	}
	
	public void init(){
		running = true;
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) bufferedImage.getGraphics();
		
		tileMap = new TileMap("assets/GameInit/tileMap.txt", width/44);
		
	}
	
	public void update(){
		tileMap.update();
	}
	
	public void render(){
		tileMap.draw(g);
	}
	
	public void draw(){
		Graphics g2 = getGraphics();
		g2.drawImage(bufferedImage, 0, 0, null);
		g2.dispose();
	}
}
