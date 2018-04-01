package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class TileMap {
	private int x;
	private int y;
	
	private int map[][];
	private int tileSize;
	
	private int mapWidth;
	private int mapHeight;
	
	public TileMap(String file, int tileSize){
		this.tileSize = tileSize;
		
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
		
	}
	
	public double getExactTileLocation(int i){
		return i/tileSize;
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics2D g){
		int current;
		Toolkit t=Toolkit.getDefaultToolkit();  
        Image hard_block = t.getImage("assets/images/Sprites/Blocks/SolidBlock.png");  
        Image walkable = t.getImage("assets/images/Sprites/Blocks/BackgroundTile.png");
        Image soft_block = t.getImage("assets/images/Sprites/Blocks/SoftBlock.jpg");
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				current = map[row][col];
				if(current == 0){
					g.drawImage(hard_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 1){
					g.drawImage(walkable, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}else if(current == 2){
					g.drawImage(soft_block, col*tileSize, row*tileSize, tileSize, tileSize, null);
				}
			}
		}
	}
	
}
