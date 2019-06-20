package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import player.Player;

public class TileMap extends Thread{
	private int x;
	private int y;
	
	private int map[][];
	private int powerupMap[][];  
	private ArrayList<Point> powerupLocations;
	 
	private int tileSize;
	
	private int mapWidth;
	private int mapHeight;
	
	private boolean isBombed;
	private int bombCol;
	private int bombRow;
	private int flameIdentifier = 5;
	private Toolkit t=Toolkit.getDefaultToolkit();  
    private Image hard_block = t.getImage("assets/images/Sprites/Blocks/SolidBlock.png");  
    private Image walkable = t.getImage("assets/images/Sprites/Blocks/BackgroundTile.png");
    private Image soft_block = t.getImage("assets/images/Sprites/Blocks/SoftBlock.jpg");
    private Image bomb_powerup = t.getImage("assets/images/Sprites/Powerups/BombPowerup.png");  
    private Image speed_powerup = t.getImage("assets/images/Sprites/Powerups/SpeedPowerup.png");
    private Image flame_powerup = t.getImage("assets/images/Sprites/Powerups/FlamePowerup.png");
    private Image life_powerup = t.getImage("assets/images/Sprites/Powerups/life.png");
    private Image pierceBomb_powerup = t.getImage("assets/images/Sprites/Powerups/pierceBomb.png");
    private Image redBomb_powerup = t.getImage("assets/images/Sprites/Powerups/redBomb.png");
    
    private Image bomb1 = t.getImage("assets/images/Sprites/Bomb/Bomb_f01.png");
    private Image bomb2 = t.getImage("assets/images/Sprites/Bomb/Bomb_f02.png");
    private Image bomb3 = t.getImage("assets/images/Sprites/Bomb/Bomb_f03.png");
    private Image bomb;
    private Image fire;
    private Image fire1 = t.getImage("assets/images/Sprites/Flame/Flame_f00.png");
    private Image fire2 = t.getImage("assets/images/Sprites/Flame/Flame_f01.png");
    private Image fire3 = t.getImage("assets/images/Sprites/Flame/Flame_f02.png");
    private Image softBlockBurn1 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_00.png");
    private Image softBlockBurn2 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_01.png");
    private Image softBlockBurn3 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_02.png");
    private Image softBlockBurn4 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_03.png");
    private Image softBlockBurn5 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_04.png");
    private int softBlockBurnTracker = 0;
    private Image softBlockBurn;
    private Graphics2D g;
    
    private int flameMultiplier = 0;
    
    private Player bomber;
    
	public TileMap(String file, int tileSize){
		this.tileSize = tileSize;
		this.start();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int [mapHeight][mapWidth];
			powerupMap = new int [mapHeight][mapWidth];
			ArrayList<Point> powerupRandomizerVariable = new ArrayList<Point>();		 
			ArrayList<Integer> powerupType = new ArrayList<Integer>();
			powerupLocations = new ArrayList<Point>();
		 
			String delimiters = " ";
			for(int row = 0; row < mapHeight; row++){
				String line = br.readLine();
				String tokens[] = line.split(delimiters);
				for(int col = 0; col < mapWidth; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
					powerupMap[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}catch(FileNotFoundException e){
			System.err.println("File Not Found");
			System.exit(-1);
		}catch(IOException e){}
	}
	
	public void setMap(String xCoordinate, String yCoordinate, String powerUp){
		String xCoordinates[] = xCoordinate.split(",");
		String yCoordinates[] = yCoordinate.split(",");
		String powerUps[] = powerUp.split(",");
		
		for(int a = 0; a < 24; a++){
			powerupMap[Integer.parseInt(xCoordinates[a])][Integer.parseInt(yCoordinates[a])] = Integer.parseInt(powerUps[a]);
			powerupLocations.add(new Point(Integer.parseInt(xCoordinates[a]), Integer.parseInt(yCoordinates[a])));
		}
	}
	
	public void normalizePowerTile(int row, int col){
		if(powerupMap[row][col] != 1){
			powerupMap[row][col] = 1;
		}
	}
		
	public int getPowerTile(int row, int col){
		return powerupMap[row][col];
	}
		 	 
	public ArrayList<Point> getPowerupLocations(){
		return powerupLocations; 
	}
		 
	
	public void run(){     //always changing sprites for uniform animation of bombs and flames
		int a = 0;
			while(true){
				a ++;
				if( a % 3 == 0 ){
					bomb = bomb1;
					fire = fire1;
				}else if( a % 3 == 1 ){
					bomb = bomb2;
					fire = fire2;
				}else if( a % 3 == 2 ){
					bomb = bomb3;
					fire = fire3;
				}
				try {
					Thread.sleep(110);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void setX(int i){ x = i; }
	public void setY(int i){ y = i; }
	
	public int getRowTile(int x){
		return x/(tileSize);
	}
	
	public int getColTile(int y){
		return y/(tileSize);
	}
	
	public int getTile(int row, int col){
		return map[row][col];
	}
	 	
	public void putBomb(int col, int row, int flameMultiplier, Player player, boolean pierce, int playerId){
		int threadRow = row;
		int threadCol = col;
		new Thread(){

			ArrayList <Integer> burnBlockCoordinates = new ArrayList<Integer>();
			public void run(){ //animating a tile to have bomb or flame
				int localFlameIdentifier;
				map[threadRow][threadCol] = playerId;
				isBombed = false;

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				player.reloadBomb();
				localFlameIdentifier = flameIdentifier;

				burnBlockCoordinates.clear();
				burnBlockCoordinates = setFlames(threadRow, threadCol, flameIdentifier, flameMultiplier, pierce, playerId);
				flameIdentifier++;

				for(int a = 0; a < 10; a++){
					if(a == 0){
						softBlockBurn = softBlockBurn1;
					}else if(a == 1){
						softBlockBurn = softBlockBurn1;
					}else if(a == 2){
						softBlockBurn = softBlockBurn2;
					}else if(a == 3){
						softBlockBurn = softBlockBurn2;
					}else if(a == 4){
						softBlockBurn = softBlockBurn3;
					}else if(a == 5){
						softBlockBurn = softBlockBurn3;
					}else if(a == 6){
						softBlockBurn = softBlockBurn4;
					}else if(a == 7){
						softBlockBurn = softBlockBurn4;
					}else if(a == 8){
						softBlockBurn = softBlockBurn5;
					}else if(a == 8){
						softBlockBurn = softBlockBurn5;
					}
					draw2(g);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				removeFlame(threadRow, threadCol, localFlameIdentifier, flameMultiplier, playerId);
			}
			public void draw2(Graphics2D g){
				for(int a = 0; a < burnBlockCoordinates.size(); ){
					int row = burnBlockCoordinates.get(a);
					int col = burnBlockCoordinates.get(a+1);
					a += 2;
					g.drawImage(softBlockBurn, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}
			}
		}.start();	
	}
	
	public double getExactTileLocation(double i){
		return i/tileSize;
	}
	
	public int getMapWidth(){
		return mapWidth;
	}
	
	public int getMapHeight(){
		return mapHeight;
	}
	
	public void update(){
		if(flameIdentifier == 500){
			flameIdentifier = 5;
		}
		
	}
	
	public ArrayList<Integer> setFlames(int row, int col, int set, int numberOfFlames, boolean pierce, int playerId){
		boolean colLeft = true;
		boolean colRight = true;
		boolean rowUp = true;
		boolean rowDown = true;
		ArrayList <Integer> burnBlockCoordinates = new ArrayList<Integer>();
		map[row][col] = set;
		for(int a = 1; a <= numberOfFlames; a++){
			if(row - a > 0 && rowUp){
				if(map[row - a][col] != 0){
					if(map[row - a][col] == 2){
						map[row - a][col] = 4;
						if(!pierce){
							rowUp = false;
						}
						burnBlockCoordinates.add(row-a);
						burnBlockCoordinates.add(col);
					}else if(map[row - a][col] == playerId){
						map[row - a][col] = set*100;
					}else{
						map[row - a][col] = set;
					}
				}else if(!pierce){
					rowUp = false;
				}
			}
			if(row + a < mapHeight && rowDown){
				if(map[row + a][col] != 0){
					if(map[row + a][col] == 2){
						map[row + a][col] = 4;
						if(!pierce){
							rowDown = false;
						}
						burnBlockCoordinates.add(row+a);
						burnBlockCoordinates.add(col);
					}else if(map[row + a][col] == playerId){
						map[row + a][col] = set*100;
					}else{
						map[row + a][col] = set;
					}
				}else if(!pierce){
					rowDown = false;
				}
			}
			if(col - a > 0 && colLeft){
				if(map[row][col - a] != 0){
					if(map[row][col - a] == 2){
						map[row][col - a] = 4;
						if(!pierce){
							colLeft = false;
						}
						burnBlockCoordinates.add(row);
						burnBlockCoordinates.add(col-a);
					}else if(map[row][col - a] == 3){
						map[row][col - a] = set*100;
					}else{
						map[row][col - a] = set;
					}
				}else if(!pierce){
					colLeft = false;
				}
			}
			if(col + a < mapWidth && colRight){
				if(map[row][col + a] != 0){
					if(map[row][col + a] == 2){
						map[row][col + a] = 4;
						if(!pierce){
							colRight = false;
						}
						burnBlockCoordinates.add(row);
						burnBlockCoordinates.add(col+a);
					}else if(map[row][col + a] == playerId){
						map[row][col + a] = set*100;
					}else{
						map[row][col + a] = set;
					}
				}else if(!pierce){
					colRight = false;
				}
			}
		}
		return burnBlockCoordinates;
	}
	
	public void removeFlame(int row, int col, int comp, int numberOfFlames, int playerId){
		if(map[row][col] == comp){			
			map[row][col] = 1;
			 checkIfPowerup(row, col);
		}else if(map[row][col] == comp*100){
			map[row][col] = playerId;
		}
		
		for(int a = 1; a <= numberOfFlames; a++){
			if(row - a > 0){
				if(map[row - a][col] == comp || map[row - a][col] == 4){
					map[row - a][col] = 1;
					checkIfPowerup(row-a, col);
				}else if(map[row - a][col] == comp*100){
					map[row - a][col] = playerId;
				}
			}
			if(row + a < mapHeight){
				if(map[row + a][col] == comp || map[row + a][col] == 4){
					map[row + a][col] = 1;
					checkIfPowerup(row+a, col);
				}else if(map[row + a][col] == comp*100){
					map[row + a][col] = playerId;
				}
			}
			if(col - a > 0){
				if(map[row][col - a] == comp || map[row][col - a] == 4){
					map[row][col - a] = 1;
					checkIfPowerup(row, col-a);
				}else if(map[row][col - a] == comp*100){
					map[row][col - a] = playerId;
				}
			}
			if(col + a < mapWidth){
				if(map[row][col + a] == comp || map[row][col + a] == 4){
					map[row][col + a] = 1;
					checkIfPowerup(row, col+a);
				}else if(map[row][col + a] == comp*100){
					map[row][col + a] = playerId;
				}
			}
		}
	}
	
	public void checkIfPowerup(int row, int col) {  
		switch(powerupMap[row][col]) {
		case 510:
			powerupMap[row][col] = 511;
			break;
		case 520:
			powerupMap[row][col] = 521;
			break;
		case 530:
			powerupMap[row][col] = 531;
			break;
		case 540:
			powerupMap[row][col] = 541;
			break;
		case 550:
			powerupMap[row][col] = 551;
			break;
		case 560:
			powerupMap[row][col] = 561;
			break;
		}
	}
		 
	
	public void draw(Graphics2D g){
		this.g = g;
		int current;
		int powerupCurrent;
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				current = map[row][col];
				powerupCurrent = powerupMap[row][col];
				if(current == 1 && powerupCurrent == 511) {
					g.drawImage(bomb_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 && powerupCurrent == 521) {
					g.drawImage(speed_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 && powerupCurrent == 531) {
					g.drawImage(flame_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 && powerupCurrent == 541) {
					g.drawImage(pierceBomb_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 && powerupCurrent == 551) {
					g.drawImage(redBomb_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 && powerupCurrent == 561) {
					g.drawImage(life_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 0){
					g.drawImage(hard_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 ){
					g.drawImage(walkable, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 2){
					g.drawImage(soft_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 600 || current == 601){
					g.drawImage(bomb, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current >= 5){
					g.drawImage(fire, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}
			 
			}
		}
	}
}
