package game;

import java.awt.Color;
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
	
	
	private Toolkit t = Toolkit.getDefaultToolkit();  
    private Image hard_block = t.getImage("assets/images/Sprites/Blocks/SolidBlock.png");  
    private Image walkable = t.getImage("assets/images/Sprites/Blocks/BackgroundTile.png");
    private Image soft_block = t.getImage("assets/images/Sprites/Blocks/SoftBlock.jpg");
    private Image bomb_powerup = t.getImage("assets/images/Sprites/Powerups/BombPowerup.png");
    private Image speed_powerup = t.getImage("assets/images/Sprites/Powerups/SpeedPowerup.png");
    private Image flame_powerup = t.getImage("assets/images/Sprites/Powerups/FlamePowerup.png");
    private Image bomb1 = t.getImage("assets/images/Sprites/Bomb/Bomb_f01.png");
    private Image bomb2 = t.getImage("assets/images/Sprites/Bomb/Bomb_f02.png");
    private Image bomb3 = t.getImage("assets/images/Sprites/Bomb/Bomb_f03.png");
    private Image bomb;
    private Image fire;
    private Image fire1 = t.getImage("assets/images/Sprites/Flame/Flame_f00.png");
    private Image fire2 = t.getImage("assets/images/Sprites/Flame/Flame_f01.png");
    private Image fire3 = t.getImage("assets/images/Sprites/Flame/Flame_f02.png");
    private Image softBlockBurn1 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_00.jpg");
    private Image softBlockBurn2 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_01.png");
    private Image softBlockBurn3 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_02.png");
    private Image softBlockBurn4 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_03.png");
    private Image softBlockBurn5 = t.getImage("assets/images/Sprites/Blocks/softblock_burn_04.png");
    //private int softBlockBurnTracker = 0;
    private Image softBlockBurn;
    private Graphics2D g;
	public TileMap(String file, int tileSize){
		this.tileSize = tileSize;
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
					if(Integer.parseInt(tokens[col]) == 2) {
						powerupRandomizerVariable.add(new Point(row, col));
					}
					
				}
			}
			

			int[] pNumType = {510, 520, 530};
			for(int counter = 0; counter < 3; counter++) {
				for(int counter2 = 0; counter2 < 8; counter2++) {
					powerupType.add(pNumType[counter]);
				}
			}
			Random rand = new Random();
			for(int counter = 0; counter < 24; counter++) {
				int position = rand.nextInt(powerupRandomizerVariable.size());
				int pValue = rand.nextInt(powerupType.size());
				powerupMap[powerupRandomizerVariable.get(position).x][powerupRandomizerVariable.get(position).y] = powerupType.get(pValue);
				powerupLocations.add(new Point(powerupRandomizerVariable.get(position).x , powerupRandomizerVariable.get(position).y));
				powerupRandomizerVariable.remove(position);
				powerupType.remove(pValue);
			}
			
			for(int row = 0; row < mapHeight; row++){
				for(int col = 0; col < mapWidth; col++){
					System.out.print(powerupMap[row][col]);
				}
				System.out.println();
			}
			
			this.start();
			
		}catch(FileNotFoundException e){
			System.err.println("File Not Found");
			System.exit(-1);
		}catch(IOException e){}
	}
	
	
	public void run(){
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
	
	public void normalizePowerTile(int row, int col){
		 powerupMap[row][col] = 1;
	}
	
	public int getPowerTile(int row, int col){
		return powerupMap[row][col];
	}
	
	public ArrayList<Point> getPowerupLocations(){
		return powerupLocations;
	}
	 	
	public void putBomb(int col, int row){
		bombCol = col;
		bombRow = row;
		isBombed = true;
	}
	
	public double getExactTileLocation(double i){
		return i/tileSize;
	}
	

	public void update(){
		flameIdentifier = 6; //Code band aid for no flame on first fire bug;
		if(flameIdentifier == 500){
			flameIdentifier = 5;
		}
		if(isBombed){
			int threadRow = bombRow;
			int threadCol = bombCol;
			new Thread(){
				public void run(){

					map[threadRow][threadCol] = 3;
					isBombed = false;
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					setFlames(threadRow, threadCol);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					removeFlame(threadRow, threadCol);
				}
				public void draw2(Graphics2D g){
					for(int a = 0; a < burnBlockCoordinates.size(); ){
						//System.out.println("gg");
						int row = burnBlockCoordinates.get(a);
						int col = burnBlockCoordinates.get(a+1);
						a += 2;
						g.drawImage(softBlockBurn, col*tileSize, row*tileSize, tileSize, tileSize, null);
					}
				}
			}.start();
		}
	}
	
	public void setFlames(int row, int col){
		map[row][col] = 4;
		if(row -1 > 0){
			if(map[row - 1][col] != 0){
				map[row - 1][col] = 4;
			}
		}
		if(row + 1 < mapHeight){
			if(map[row + 1][col] != 0){
				map[row + 1][col] = 4;
			}
		}
		if(col - 1 > 0){
			if(map[row][col - 1] != 0){
				map[row][col - 1] = 4;
			}
		}
		if(col + 1 < mapWidth){
			if(map[row][col + 1] != 0){
				map[row][col + 1] = 4;
			}
		}
	}
	
	public void removeFlame(int row, int col, int comp){
		if(map[row][col] == comp){			
			map[row][col] = 1;
			checkIfPowerup(row, col);
		}else if(map[row][col] == comp*100){
			map[row][col] = 3;
		}
		if(row -1 > 0){
			if(map[row - 1][col] == 4){
				map[row - 1][col] = 1;
				checkIfPowerup(row-1, col);
			}else if(map[row - 1][col] == comp*100){
				map[row - 1][col] = 3;
			}
		}
		if(row + 1 < mapHeight){
			if(map[row + 1][col] == 4){
				map[row + 1][col] = 1;
				checkIfPowerup(row+1, col);
			}else if(map[row + 1][col] == comp*100){
				map[row + 1][col] = 3;
			}
		}
		if(col - 1 > 0){
			if(map[row][col - 1] == 4){
				map[row][col - 1] = 1;
				checkIfPowerup(row, col-1);
			}else if(map[row][col - 1] == comp*100){
				map[row][col - 1] = 3;
			}
		}
		if(col + 1 < mapWidth){
			if(map[row][col + 1] == 4){
				map[row][col + 1] = 1;
				checkIfPowerup(row, col+1);
			}else if(map[row][col + 1] == comp*100){
				map[row][col + 1] = 3;
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
		}
	}
	
	public void draw(Graphics2D g){
		int current;
		int powerupCurrent;
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				current = map[row][col];
				powerupCurrent = powerupMap[row][col];
				if(current == 0){
					g.drawImage(hard_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 && powerupCurrent < 500){
					g.drawImage(walkable, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 2){
					g.drawImage(soft_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 3){
					g.drawImage(bomb, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == -1){
					g.drawImage(fire, col*tileSize, row*tileSize, tileSize, tileSize, null);
				} else if(current > 5 && current <= 500){
					g.drawImage(fire, col*tileSize, row*tileSize, tileSize, tileSize, null);
				} else if(current == 1 && powerupCurrent == 511) {
					g.drawImage(bomb_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 && powerupCurrent == 521) {
					g.drawImage(speed_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 && powerupCurrent == 531) {
					g.drawImage(flame_powerup, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}
			}
		}
	}
}
