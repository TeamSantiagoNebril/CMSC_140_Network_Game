package Game;

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
	
	public void update(){
		
	}
	
	public void draw(Graphics2D g){
		int current;
		Toolkit t=Toolkit.getDefaultToolkit();  
        Image i=t.getImage("assets/images/Brick_Block.png");  
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				current = map[row][col];
				if(current == 0){
					g.setColor(Color.BLACK);
				}else if(current == 1){
					g.setColor(Color.WHITE);
				}
				
				g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
			}
		}
	}
	
}
