package game;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
	private Monster monster2;
	private Monster monster3;
	private Monster monster4;
	private Monster monster5;
	private Monster monster6;
	private Monster monster7;
	private Monster monster8;
	private Monster monster9;
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
	
	ArrayList<String> playerNames = new ArrayList<String>();
	
	public GamePanel(){
		super();
		width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		playerNames.add("PLAYER1");
		playerNames.add("PLAYER2");
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
	
	public String getMonsterCoordinates(){
		String temp = "";
		temp = monster.calculateMonsterCoordinates() + ",";
		temp += monster2.calculateMonsterCoordinates() + ",";
		temp += monster3.calculateMonsterCoordinates() + ",";
		temp += monster4.calculateMonsterCoordinates() + ",";
		temp += monster5.calculateMonsterCoordinates() + ",";
		temp += monster6.calculateMonsterCoordinates() + ",";
		temp += monster7.calculateMonsterCoordinates() + ",";
		temp += monster8.calculateMonsterCoordinates() + ",";
		temp += monster9.calculateMonsterCoordinates() + ",";
		return temp;
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
	
	public void setMonsterCoordinates(String message){
		String coordinates[] = message.split(",");
		int counter = 0;
		for(int a = 0; a< coordinates.length;){
			if(coordinates[a].equals("DEAD")){
				if(counter == 0){
					monster.kill();
				}else if(counter ==1){
					monster2.kill();
				}else if(counter == 2){
					monster3.kill();
				}else if(counter == 3){
					monster4.kill();
				}else if(counter == 4){
					monster5.kill();
				}else if(counter == 5){
					monster6.kill();
				}else if(counter == 6){
					monster7.kill();
				}else if(counter == 7){
					monster8.kill();
				}else if(counter == 8){
					monster9.kill();
				}
				a++;
				counter++;
			}else{
				if(counter == 0){
					monster.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}else if(counter == 1){
					monster2.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}else if(counter == 2){
					monster3.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}else if(counter == 3){
					monster4.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}else if(counter == 4){
					monster5.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}else if(counter == 5){
					monster6.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}else if(counter == 6){
					monster7.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}else if(counter == 7){
					monster8.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}else if(counter == 8){
					monster9.update(Double.parseDouble(coordinates[a]), Double.parseDouble(coordinates[a+1]));
				}
				a += 2;
				counter++;
			}
		}
	}
	
	public String getWinner(){
		if(playerNames.size() == 0){
			return "TIE";
		}else if(playerNames.size() == 1){
			return playerNames.get(0);
		}
		return "";
	}
	
	public String getDiedPlayer(){
		String temp = "";
		if(player.isDiedPlayer()){
			temp += "PLAYER1 ";
			for(int a = 0; a < playerNames.size(); a++){
				if(playerNames.get(a).equals("PLAYER1")){
					playerNames.remove(a);
					break;
				}
			}
		}
		if(player2.isDiedPlayer()){
			temp += "PLAYER2 ";
			playerNames.remove(1);
			for(int a = 0; a < playerNames.size(); a++){
				if(playerNames.get(a).equals("PLAYER2")){
					playerNames.remove(a);
					break;
				}
			}
		}
		return temp;
	}
	
	public void setDiedPlayer(String message[]){
		for(int a = 1; a < message.length; a++){
			if(message[a].equals("PLAYER1")){
				player.diedPlayer();
			}else if(message[a].equals("PLAYER2")){
				player2.diedPlayer();
			}
		}
	}
	
	public void init(String initialPositions){
		Player players[] = new Player[2];
		running = true;
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) bufferedImage.getGraphics();
		
		int size = 48;
		tileMap = new TileMap("assets/GameInit/tileMap.txt", size);
		
		String positions[] = initialPositions.split(",");
		player = new Player(tileMap, size, Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
		player2 = new Player(tileMap, size, Integer.parseInt(positions[2]), Integer.parseInt(positions[3]));
		players[0] = player;
		players[1] = player2;
		monster = new Monster(tileMap, 240, 48, size, size, players);
		monster2 = new Monster(tileMap, 48, 240, size, size, players);
		monster3 = new Monster(tileMap, 1056, 48, size, size, players);
		monster4 = new Monster(tileMap, 240, 528, size, size, players);
		monster5 = new Monster(tileMap, 1344, 240, size, size, players);
		monster6 = new Monster(tileMap, 1056, 528, size, size, players);
		monster7 = new Monster(tileMap, 336, 288, size, size, players);
		monster8 = new Monster(tileMap, 624, 432, size, size, players);
		monster9 = new Monster(tileMap, 912, 288, size, size, players);
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
	
	public String getKillPlayer(){
		String temp = "";
		if(player.isplayerDead()){
			temp +=  "PLAYER1 ";
		}
		if(player2.isplayerDead()){
			temp += "PLAYER2 ";
		}
		return temp;
	}
	
	public void setMap(String xCoordinates, String yCoordinates, String powerUp){
		tileMap.setMap(xCoordinates, yCoordinates, powerUp);
	}
	
	public void killPlayer(String playerNames){
		String playerName[] = playerNames.split(" ");
		for(int a = 0; a < playerName.length; a++){
			if(playerName[a].equals("PLAYER1")){
				player.deadPlayer();
			}else if(playerName[a].equals("PLAYER2")){
				player2.deadPlayer();
			}
		}
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
	
	public String getPowerUp(String playerName){
		String temp = "";
		if(playerName.equals("PLAYER1")){
			temp = player.getPowerUp();
			if(temp.length() != 0){
				return "PLAYER1 " + temp + " ";
			}
		}else if(playerName.equals("PLAYER2")){
			temp = player2.getPowerUp();
			if(temp.length() != 0){
				return "PLAYER2 " + temp + " ";
			}
		} 
		return "";
	}
	
	public void setPowerUp(String message[]){
		int a = 1;
		for(int b = 0; b < message.length; b++){
		}
		while(a < message.length){
			if(message[a].equals("PLAYER1")){
				if(message[a + 3].equals("2")){
					player.addFlame();
				}else if(message[a + 3].equals("3")){
					player.addMaxBomb();
				}else if(message[a + 3].equals("4")){
					player.pierceBomb();
				}else if(message[a + 3].equals("5")){
					player.maxFlame();
				}else if(message[a + 3].equals("6")){
					player.healPlayer();
				}
			}else if(message[a].equals("PLAYER2")){
				if(message[a + 3].equals("2")){
					player2.addFlame();
				}else if(message[a + 3].equals("3")){
					player2.addMaxBomb();
				}else if(message[a + 3].equals("4")){
					player2.pierceBomb();
				}else if(message[a + 3].equals("5")){
					player2.maxFlame();
				}else if(message[a + 3].equals("6")){
					player2.healPlayer();
				}
			}
			tileMap.normalizePowerTile(Integer.parseInt(message[a + 1]), Integer.parseInt(message[a + 2]));
			a += 4;
		}
	}
	
	public void setBombLocation(String message[]){
		int a = 2;
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
		monster.draw(g);
		monster2.draw(g);
		monster3.draw(g);
		monster4.draw(g);
		monster5.draw(g);
		monster6.draw(g);
		monster7.draw(g);
		monster8.draw(g);
		monster9.draw(g);
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
			if(lastClicked != 1 && clicked){
				controller.requestMovementUpdate(lastClicked + 5);
				clicked = false;
			}
			if(!clicked){
				controller.requestMovementUpdate(1);
				clicked = true;
				lastClicked = 1;
			}
		}else if(keyCode == KeyEvent.VK_DOWN){
			if(lastClicked != 2 && clicked){
				controller.requestMovementUpdate(lastClicked + 5);
				clicked = false;
			}
			if(!clicked){
				controller.requestMovementUpdate(2);
				clicked = true;
				lastClicked = 2;
			}
			
		}else if(keyCode == KeyEvent.VK_LEFT){
			if(lastClicked != 3 && clicked){
				controller.requestMovementUpdate(lastClicked + 5);
				clicked = false;
			}
			if(!clicked){
				controller.requestMovementUpdate(3);
				clicked = true;
				lastClicked = 3;
			}
		}else if(keyCode == KeyEvent.VK_RIGHT){
			if(lastClicked != 4 && clicked){
				controller.requestMovementUpdate(lastClicked + 5);
				clicked = false;
			}
			if(!clicked){
				controller.requestMovementUpdate(4);
				clicked = true;
				lastClicked = 4;
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
