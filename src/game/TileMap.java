package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
    private Image fire4 = t.getImage("assets/images/Sprites/Flame/Flame_f03.png");
    private Image fire5 = t.getImage("assets/images/Sprites/Flame/Flame_f04.png");
     
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
		}catch(IOException e){
		}
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
	
	public void removeFlame(int row, int col){
		map[row][col] = 1;
		if(row -1 > 0){
			if(map[row - 1][col] == 4){
				map[row - 1][col] = 1;
			}
		}
		if(row + 1 < mapHeight){
			if(map[row + 1][col] == 4){
				map[row + 1][col] = 1;
			}
		}
		if(col - 1 > 0){
			if(map[row][col - 1] == 4){
				map[row][col - 1] = 1;
			}
		}
		if(col + 1 < mapWidth){
			if(map[row][col + 1] == 4){
				map[row][col + 1] = 1;
			}
		}
	}
	
	public void draw(Graphics2D g){
		int current;
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				current = map[row][col];
				if(current == 0){
					g.drawImage(hard_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1){
					g.drawImage(walkable, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 2){
					g.drawImage(soft_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 3){
					g.drawImage(bomb, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 4){
					g.drawImage(fire, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}
			}
		}
	}
}
