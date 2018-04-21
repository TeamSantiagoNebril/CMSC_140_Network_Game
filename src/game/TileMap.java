package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TileMap extends Thread{
	private int x;
	private int y;
	
	private int map[][];
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
    private int softBlockBurnTracker = 0;
    private Image softBlockBurn;
    private Graphics2D g;
	public TileMap(String file, int tileSize){
		this.tileSize = tileSize;
		this.start();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int [mapHeight][mapWidth];
			
			String delimiters = " ";
			for(int row = 0; row < mapHeight; row++){
				String line = br.readLine();
				String tokens[] = line.split(delimiters);
				for(int col = 0; col < mapWidth; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}catch(FileNotFoundException e){
			System.err.println("File Not Found");
		}catch(IOException e){}
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
					// TODO Auto-generated catch block
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
	 	
	public void putBomb(int col, int row){
		bombCol = col;
		bombRow = row;
		isBombed = true;
	}
	
	public double getExactTileLocation(double i){
		return i/tileSize;
	}
	

	public void update(){
		if(flameIdentifier == 500){
			flameIdentifier = 5;
		}
		if(isBombed){
			int threadRow = bombRow;
			int threadCol = bombCol;
			new Thread(){
				
				ArrayList <Integer> burnBlockCoordinates = new ArrayList<Integer>();
				public void run(){ //animating a tile to have bomb or flame
					int localFlameIdentifier;
					map[threadRow][threadCol] = 3;
					isBombed = false;
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					localFlameIdentifier = flameIdentifier;
					burnBlockCoordinates.clear();
					burnBlockCoordinates = setFlames(threadRow, threadCol, flameIdentifier);
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
					
					removeFlame(threadRow, threadCol, localFlameIdentifier);
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
	}
	
	public ArrayList<Integer> setFlames(int row, int col, int set){
		ArrayList <Integer> burnBlockCoordinates = new ArrayList<Integer>();
		map[row][col] = set;
		if(row -1 > 0){
			if(map[row - 1][col] != 0){
				if(map[row - 1][col] == 2){
					map[row - 1][col] = 4;
					burnBlockCoordinates.add(row-1);
					burnBlockCoordinates.add(col);
				}else{
					map[row - 1][col] = set;
				}
			}
		}
		if(row + 1 < mapHeight){
			if(map[row + 1][col] != 0){
				if(map[row + 1][col] == 2){
					map[row + 1][col] = 4;
					burnBlockCoordinates.add(row+1);
					burnBlockCoordinates.add(col);
				}else{
					map[row + 1][col] = set;
				}
			}
		}
		if(col - 1 > 0){
			if(map[row][col - 1] != 0){
				if(map[row][col - 1] == 2){
					map[row][col - 1] = 4;
					burnBlockCoordinates.add(row);
					burnBlockCoordinates.add(col-1);
				}else{
					map[row][col - 1] = set;
				}
			}
		}
		if(col + 1 < mapWidth){
			if(map[row][col + 1] != 0){
				if(map[row][col + 1] == 2){
					map[row][col + 1] = 4;
					burnBlockCoordinates.add(row);
					burnBlockCoordinates.add(col+1);
				}else{
					map[row][col + 1] = set;
				}
			}
		}
		return burnBlockCoordinates;
	}
	
	public void removeFlame(int row, int col, int comp){
		if(map[row][col] == comp){			
			map[row][col] = 1;
		}
		if(row -1 > 0){
			if(map[row - 1][col] == comp || map[row - 1][col] == 4){
				map[row - 1][col] = 1;
			}
		}
		if(row + 1 < mapHeight){
			if(map[row + 1][col] == comp || map[row + 1][col] == 4){
				map[row + 1][col] = 1;
			}
		}
		if(col - 1 > 0){
			if(map[row][col - 1] == comp || map[row][col - 1] == 4){
				map[row][col - 1] = 1;
			}
		}
		if(col + 1 < mapWidth){
			if(map[row][col + 1] == comp || map[row][col + 1] == 4){
				map[row][col + 1] = 1;
			}
		}
	}
	
	public void draw(Graphics2D g){
		this.g = g;
		int current;
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				current = map[row][col];
				if(current == 0){
					g.drawImage(hard_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1 ){
					g.drawImage(walkable, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 2){
					g.drawImage(soft_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 3){
					g.drawImage(bomb, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current >= 5){
					g.drawImage(fire, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}
			}
		}
	}
}
