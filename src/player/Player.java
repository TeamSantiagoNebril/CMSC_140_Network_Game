package player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Game.TileMap;

public class Player {
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double tox;
	private double toy;
	private double moveSpeed;
	private TileMap tileMap;
	private int width;
	private int height;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private double tempx;
	private double tempy;
	public Player(TileMap tileMap, int spriteSize){
		this.tileMap = tileMap;
		width = spriteSize;
		height = spriteSize;
		moveSpeed = 6;
		
		x = spriteSize;
		y = spriteSize;
	}
	
	public void setLeft(boolean bool){ left = bool; }
	public void setRight(boolean bool){ right = bool; }
	public void setUp(boolean bool){ up = bool; }
	public void setDown(boolean bool){ down = bool; }
	
	public int calculateDestination(double x, double y){
		int col = tileMap.getColTile((int) x );
		int row = tileMap.getRowTile((int) y );

		//System.out.println(row + ":" + col);
		return tileMap.getTile(row, col);
	}
	
	public void update(){
		tempx = 0;
		tempy = 0;
		
		double tempx2 = 0; //for example move is right, top and bottom part of the tile
		double tempy2 = 0; //should equal to 1 (in case tile is between to tiles)
		if(up){
			dx =  0;
			dy = -moveSpeed;
		}else if(down){
			dx = 0;
			dy = moveSpeed;
			tempy = height - 1;
		}else if(right){
			dx = moveSpeed;
			dy = 0;
			tempx = width - 1;
		}else if(left){
			dx = -moveSpeed;
			dy = 0;
		}
		
		//check collisions
		
		tox = x + dx;
		toy = y + dy;
		
		tempx += tox;
		tempy += toy;
		
		if(x == tox){  //move is vertical
			tempx2 = tempx + width - 1;
			tempy2 = tempy;
		}else if(y == toy){ //move is horizontal
			tempx2 = tempx;
			tempy2 = tempy + height - 1;
		}
		
		if(calculateDestination(tempx, tempy) != 0 && calculateDestination(tempx2, tempy2) != 0){
			x = tox;
			y = toy;
		}else{
			dx = 0;
			dy = 0;
		}
	}
	
	public void setdx(double a){
		dx = a;
	}
	
	public void setdy(double a){
		dy = a;
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, width, height);
	}
	
}

