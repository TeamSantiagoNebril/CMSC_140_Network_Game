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

import networking.Client;
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
	
	private Client controller;
	private BufferedImage bufferedImage;
	private Graphics2D g;
	
	private int height;
	private int width;
	private int lastClicked;
	private boolean clicked = false;
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
		player2 = new Player(tileMap, size, Integer.parseInt(positions[2]), Integer.parseInt(positions[3]));
	}
	
	public void setController(Client controller){
		this.controller = controller;
	}
	
	public void update(){
		tileMap.update();
		
	}
	
	public void updatePositions(String updateCoordinates){
		String positions[] = updateCoordinates.split(",");
		player.update(Double.parseDouble(positions[0]), Double.parseDouble(positions[1]));
		player2.update(Double.parseDouble(positions[2]), Double.parseDouble(positions[3]));
	}
	
	public void calculateUpdatePositions(String calculatePositions){
		String message[] =  calculatePositions.split(" ");
		if(message[0].equals("PLAYER1")){
			setPlayerMove(player, Integer.parseInt(message[1]));
			player.calculateMovement();
		}else if(message[0].equals("PLAYER2")){
			setPlayerMove(player2, Integer.parseInt(message[1]));
			player2.calculateMovement();
		}
	}
	
	public void setPlayerMove(Player player, int move){
		if(move == 1){
			player.setUp(true);
		}else if(move == 2){
			player.setDown(true);
		}else if(move == 3){
			player.setLeft(true);
		}else if(move == 4){
			player.setRight(true);
		}else if(move == 6){
			player.setUp(false);
		}else if(move == 7){
			player.setDown(false);
		}else if(move == 8){
			player.setLeft(false);
		}else if(move == 9){
			player.setRight(false);
		}
	}
	
	public String getPlayerCoordinates(String playerName){
		if(playerName.equals("PLAYER1")){
			return player.getUpdatedCoordinates();
		}else if(playerName.equals("PLAYER2")){
			return player2.getUpdatedCoordinates();
		}
		return "";
	}
	
	public void calculatePlayerBomb(String message){
		if(message.equals("PLAYER1")){
			player.calculateBomb();
		}else if(message.equals("PLAYER2")){
			player2.calculateBomb();
		}
	}
	
	public String getCalculatedPlayerBomb(String playerName){
		String temp = "";
		if(playerName.equals("PLAYER1")){
			temp = player.getBombCoordinates();
			if(temp.length() != 0)
				return "PLAYER1 " + temp;
		}else if(playerName.equals("PLAYER2")){
			temp = player2.getBombCoordinates();
			if(temp.length() != 0)
				return "PLAYER2 " + temp;
		}
		return "";
	}
	
	public void setBombLocation(String message[]){
		int a = 1;
		for(int b = 0; b < message.length; b++){
		}
		while(a < message.length){
			if(message[a].equals("PLAYER1")){
				
				player.putBomb(Integer.parseInt(message[a + 1]), Integer.parseInt(message[a + 2]));
				a += 3;
			}else if(message[a].equals("PLAYER2")){
				player2.putBomb(Integer.parseInt(message[a + 1]), Integer.parseInt(message[a + 2]));
				a += 3;
			}
		}
	}
	
	public void render(){
		tileMap.draw(g);
		player.draw(g);
		player2.draw(g);
		
	}
	
	public void draw(){
		Graphics g2 = getGraphics();
		g2.drawImage(bufferedImage, 0, 0, null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent e){
	}
	public void keyPressed (KeyEvent e){
		
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_UP){
			if(!clicked){
				controller.requestMovementUpdate(1);
				clicked = true;
			}
		}else if(keyCode == KeyEvent.VK_DOWN){
			if(!clicked){
				controller.requestMovementUpdate(2);
				clicked = true;
			}
		}else if(keyCode == KeyEvent.VK_LEFT){
			if(!clicked){
				System.out.println("LEFT");
				controller.requestMovementUpdate(3);
				clicked = true;
			}
		}else if(keyCode == KeyEvent.VK_RIGHT){
			if(!clicked){
				controller.requestMovementUpdate(4);
				clicked = true;
			}
		}else if(keyCode == KeyEvent.VK_SPACE){
			controller.requestBomb();
		}
	}
	public void keyReleased(KeyEvent e){
		
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_UP){
			clicked = false;
			controller.requestMovementUpdate(6);
		}else if(keyCode == KeyEvent.VK_DOWN){
			clicked = false;
			controller.requestMovementUpdate(7);
		}else if(keyCode == KeyEvent.VK_LEFT){
			clicked = false;
			controller.requestMovementUpdate(8);
		}else if(keyCode == KeyEvent.VK_RIGHT){
			clicked = false;
			controller.requestMovementUpdate(9);
		}else if(keyCode == KeyEvent.VK_SPACE){
			//controller.requestBomb();
		}
	}
}
