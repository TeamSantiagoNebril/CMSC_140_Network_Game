package game;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.swing.JPanel;

import player.Monster;
import player.Player;
import player.Player;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private Boolean running;
	
	private int FPS = 30;
	private int targetTime = 1000/FPS;
	private Player player;
	private Player player2;
	private Player player3;
	private Player player4;
	private Monster monster;
	private TileMap tileMap;
	
	private BufferedImage bufferedImage;
	private Graphics2D g;
	
	private int height;
	private int width;
	
	private double initialPlayerX;
	private double initialPlayerY;
	public GamePanel(){
		super();
		width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
	}
	
	public void addNotify(){
		super.addNotify();     
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		setVisible(true);
		requestFocus();
	}
	
	public void setInitialPosition(double x, double y){
		initialPlayerX = x;
		initialPlayerY = y;
	}
	
	public void run(){

		System.out.println("potaaaaaa");
		
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
	
	public void init(String initialPositions){
		
		running = true;
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) bufferedImage.getGraphics();
		
		int size = 48;
		tileMap = new TileMap("assets/GameInit/tileMap.txt", size);
		
		String positions[] = initialPositions.split(",");
		player = new Player(tileMap, size, Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
		
	}
	
	public void update(){
		tileMap.update();
		player.update();
		
	}
	
	
	public void render(){
		tileMap.draw(g);
		player.draw(g);
		
	}
	
	public void draw(){
		Graphics g2 = getGraphics();
		g2.drawImage(bufferedImage, 0, 0, null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent e){}
	public void keyPressed (KeyEvent e){
		
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_UP){
			player.setUp(true);
		}else if(keyCode == KeyEvent.VK_DOWN){
			player.setDown(true);
		}else if(keyCode == KeyEvent.VK_LEFT){
			player.setLeft(true);
		}else if(keyCode == KeyEvent.VK_RIGHT){
			player.setRight(true);
		}else if(keyCode == KeyEvent.VK_SPACE){
			player.setBombed(true);
		}
	}
	public void keyReleased(KeyEvent e){
		
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_UP){
			player.setUp(false);
			player.setdy(0);
		}else if(keyCode == KeyEvent.VK_DOWN){
			player.setDown(false);
			player.setdy(0);
		}else if(keyCode == KeyEvent.VK_LEFT){
			player.setLeft(false);
			player.setdx(0);
		}else if(keyCode == KeyEvent.VK_RIGHT){
			player.setRight(false);
			player.setdx(0);
		}else if(keyCode == KeyEvent.VK_SPACE){
			player.setBombed(false);
		}
		
	}
}
