package player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import game.TileMap;

public class Player {
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double tox;
	private double toy;
	private TileMap tileMap;
	private int width;
	private int height;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean isBombed;
	private double tempx;
	private double tempy;
	private double lastdx;
	private double lastdy;
	private boolean isStart = true;
	private int lastMove;
	private boolean clicked;
	private boolean faceleft = false;
	private PlayerMovementAnimation animation;
	private static int playerNumber;
	private boolean bombLocation;
	private double bombX;
	private double bombY;
	private boolean isAnimatingDeadImage;
	
	private int maxBomb = 1;
	private double moveSpeed;
	private ArrayList<Point> powerupLocations;
	
	public Player(){}
	
	public Player(TileMap tileMap, int spriteSize, int x, int y){
		this.tileMap = tileMap;
		width = spriteSize;
		height = spriteSize;
		moveSpeed = 6;
		
		x = spriteSize;
		y = spriteSize;
		
		animation = new PlayerMovementAnimation(++playerNumber);
		powerupLocations = tileMap.getPowerupLocations();
	}
	
	public void setLeft(boolean bool){ left = bool; }
	public void setRight(boolean bool){ right = bool; }
	public void setUp(boolean bool){ up = bool; }
	public void setDown(boolean bool){ down = bool; }
	
	public void setBombed(boolean bool){
		bombLocation = true;
		if(!isBombed && bool || isBombed && !bool){
			isBombed = bool;
		}
	}
	
	public int calculateDestination(double x, double y){
		int col = tileMap.getColTile((int) x );
		int row = tileMap.getRowTile((int) y );
		//System.out.println(row + ":" + col);
		return tileMap.getTile(row, col);
	}
	
	public void update(){
		if(isBombed){
			isBombed = false;
			if(lastdx != 0){
				putBombHorizontal((int)x, (int)y);
			}else if(lastdy != 0){
				putBombVertical((int)x, (int)y);
			}
		}else{
			
			clicked = false;
			
			tempx = 0;
			tempy = 0;
			
			double tempx2 = 0; //for example move is right, top and bottom part of the tile
			double tempy2 = 0; //should be equal to 1 (in case tile is between two tiles)
			if(up){
				dx =  0;
				dy = -moveSpeed;
				clicked = true;
				faceleft = false;
			}else if(down){
				dx = 0;
				dy = moveSpeed;
				tempy = height - 1;
				clicked = true;
				faceleft = false;
			}else if(right){
				dx = moveSpeed;
				dy = 0;
				tempx = width - 1;
				clicked = true;
				faceleft = false;
			}else if(left){
				dx = -moveSpeed;
				dy = 0;
				clicked = true;
				faceleft = true;
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
			
			if(calculateDestination(tempx, tempy) != 0 && calculateDestination(tempx2, tempy2) == 1){ //is tile walkable
				x = tox;
				y = toy;
				if(clicked){
					lastdx = dx;
					lastdy = dy;
				}
				if(up){
					lastMove = 1;
				}else if(down){
					lastMove = 2;
				}else if(right){
					lastMove = 3;
				}else if(left){
					lastMove = 4;
				}
			}else{								//fitting helper
				if(isStart){
					isStart = false;
					dx = 0;
					dy = 0;
				}else{
					if(lastdx < 0){
						faceleft = true;
					}
					
					tox = x + lastdx;
					toy = y + lastdy;
					dx = lastdx;		//for comparison purposes only
					dy = lastdy;
					if(lastMove == 2){
						tempx = 0;
						tempy = height - 1;
					}else if(lastMove == 3){
						tempx = width - 1;
						tempy = 0;
					}else{
						tempx = 0;
						tempy = 0;
					}
					
					tempx += tox;
					tempy += toy;
					
					if(x == tox){  //move is vertical
						tempx2 = tempx + width - 1;
						tempy2 = tempy;
					}else if(y == toy){ //move is horizontal
						tempx2 = tempx;
						tempy2 = tempy + height - 1;
					}
					
					if(calculateDestination(tempx, tempy) != 0 && calculateDestination(tempx2, tempy2) == 1){ //is tile walkable
						x = tox;
						y = toy;
					}else{
						dx = 0;
						dy = 0;
					}
				}
			}
			if(dy < 0) {  // for bomberman sprite
				animation.setMove(1);
			}else if(dy > 0){
				animation.setMove(2);
			}else if(dx < 0){
				animation.setMove(3);
			}else if(dx > 0){
				animation.setMove(3);
			}else{
				animation.setMove(12);
			}
			
			if(up || down){
				if(bombLocation && (int)tileMap.getExactTileLocation(y) != (int)tileMap.getExactTileLocation(bombY) && (int)tileMap.getExactTileLocation(y + height - 1) != (int)tileMap.getExactTileLocation(bombY)){
					bombLocation = false;
				}
			}else if( right ||  left){
				if(bombLocation && (int)tileMap.getExactTileLocation(x) != (int)tileMap.getExactTileLocation(bombX) && (int)tileMap.getExactTileLocation(x + width - 1) != (int)tileMap.getExactTileLocation(bombX)){
					bombLocation = false;
				}
			}
			
			if(calculateDestination(bombX, bombY) != 3) {
				bombLocation = false;
			}
		
			if(!clicked) {  //make bomberman stop if no keys are pressed
				dx = 0;
				dy = 0;
			}
			
		}
		//System.out.println(maxBomb);
		System.out.println(moveSpeed);
		int x2 = (int)tileMap.getExactTileLocation(y);
		int y2 = (int)tileMap.getExactTileLocation(x);
		for(int i = 0; i < powerupLocations.size(); i++) {
			//System.out.println("x: "+ x2 +" y: "+ y2 +" pX: " + powerupLocations.get(i).x + " py: "+ powerupLocations.get(i).y);
			if(x2 == powerupLocations.get(i).x && y2 == powerupLocations.get(i).y) {

				//System.out.println(tileMap.getPowerTile(x2, y2));
				switch(tileMap.getPowerTile(x2, y2)) {
					case 511: //bomb_powerup;
						if(maxBomb != 2) {
							maxBomb++;
						}
						tileMap.normalizePowerTile(x2, y2);
						powerupLocations.remove(i);
						break;
					case 521: //movespeed_powerup;
						if(moveSpeed != 8.0) {
							moveSpeed += 2.0;
						}
						tileMap.normalizePowerTile(x2, y2);
						powerupLocations.remove(i);
						break;
					case 531: //flame powerup
						tileMap.normalizePowerTile(x2, y2);
						powerupLocations.remove(i);
						System.out.println("fff");
						break;
				}
			}
		}
		
	}
	
	public void putBombHorizontal(int x, int y){
		double dominance = tileMap.getExactTileLocation(x) - (int)tileMap.getExactTileLocation(x);
		int col;
		int row;
		if(dominance < 0.5){
			col = tileMap.getColTile(x);
			row = tileMap.getRowTile(y);
			tileMap.putBomb(col, row);
		}else{
			col = tileMap.getColTile(x + width);
			row = tileMap.getRowTile(y);
			tileMap.putBomb(col, row);
		}
	}
	
	public void putBombVertical(int x, int y){
		double dominance = tileMap.getExactTileLocation(y) - (int)tileMap.getExactTileLocation(y);
		int col;
		int row;
		System.out.println(dominance);
		if(dominance < 0.5){
			col = tileMap.getColTile(x);
			row = tileMap.getRowTile(y);
			tileMap.putBomb(col, row);
		}else{
			col = tileMap.getColTile(x);
			row = tileMap.getRowTile(y + height);
			tileMap.putBomb(col, row);
		}
	}
	
	public void setdx(double a){
		dx = a;
	}
	
	public void setdy(double a){
		dy = a;
	}
	
	public void draw(Graphics2D g){
		if(faceleft){
			g.drawImage(animation.getImage(), (int)x + width, (int)y, -width, height, null);
		}else{
			g.drawImage(animation.getImage(), (int)x, (int)y, width, height, null);
		}
	}
	
}

