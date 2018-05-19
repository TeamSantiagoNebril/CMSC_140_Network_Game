package player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import game.TileMap;

public class Player{
	private double calculatedX;
	private double calculatedY;
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
	private int life = 3;
	private double startX;
	private double startY;
	private PlayerMovementAnimation animation;
	private static int playerNumber;
	private boolean bombLocation;
	private double bombX;
	private double bombY;
	private boolean isAnimatingDeadImage;
	
	private String bombCoordinates = "";
	
	private int maxBomb = 1;  
	private double moveSpeed;
	private int flameMultiplier;
	private ArrayList<Point> powerupLocations;
	
	 
	public Player(){}
	 
	public Player(TileMap tileMap, int spriteSize, int x, int y){
		this.tileMap = tileMap;
		width = spriteSize;
		height = spriteSize;
		moveSpeed = 3;
		
		this.x = x;
		this.y = y;
		calculatedX = x;
		calculatedY = y;
		startX = x;
		startY = y;
		
		animation = new PlayerMovementAnimation(++playerNumber);
		powerupLocations = tileMap.getPowerupLocations();
		 
	}
	
	public void setLeft(boolean bool){ 
		left = bool; }
	public void setRight(boolean bool){ 
		right = bool; }
	public void setUp(boolean bool){ 
		up = bool; }
	public void setDown(boolean bool){ 
		down = bool; }
	public void calculateBomb(){
		if(lastdx != 0){
			bombCoordinates = putBombHorizontal((int)x, (int)y);

		}else if(lastdy != 0){
			bombCoordinates = putBombVertical((int)x, (int)y);
		}
	}
	
	public String getBombCoordinates(){
		String temp = bombCoordinates;
		bombCoordinates = "";
		return temp;
	}
	
	public int calculateDestination(double x, double y){
		int col = tileMap.getColTile((int) x );
		int row = tileMap.getRowTile((int) y );
		return tileMap.getTile(row, col);
	}
	
	public Boolean isDead(int x, int y){
		if(calculateDestination(x, y) >= 4)
			return true;
		return false;
	}
	
	public void dead(){
		life--;
		x = startX;
		y = startY;
		System.out.println("Life: " + life );
	}
	
	public void update(double x, double y){
		faceleft = false;
		double dx = x - this.x;
		double dy = y - this.y;
		if(dy < 0){  // for bomberman sprite
			lastdx = 0;
			lastdy = -moveSpeed;
			animation.setMove(1);
		}else if(dy > 0){
			lastdx = 0;
			lastdy = moveSpeed;
			animation.setMove(2);
		}else if(dx > 0){
			
			lastdx = moveSpeed;
			lastdy = 0;
			animation.setMove(3);
		}else if(dx < 0){
			lastdx = -moveSpeed;
			lastdy = 0;
			faceleft = true;
			animation.setMove(3);
		}else{
			animation.setMove(12);
		}
		
		this.x = x;
		this.y = y;

	}
	
	public void deadPlayer(){
		if(isDead((int)calculatedX, (int)calculatedY) && !isAnimatingDeadImage || isDead((int)calculatedX, (int)calculatedY + height - 1) && !isAnimatingDeadImage ||
				isDead((int)calculatedX + width - 1, (int)calculatedY) && !isAnimatingDeadImage ){
			new Thread(){
				public void run(){
					isAnimatingDeadImage = true;
					for(int a = 0; a < 8; a++){
						animation.setMove(4+a);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					isAnimatingDeadImage = false;
					dead();
				}				
			}.start();
		}
	}
	
	public void calculateMovement(){
		String testing = "";
		double tempCalX = calculatedX;
		double tempCalY = calculatedY;
		if(!isAnimatingDeadImage){
			if (up || down || left || right){
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

				tox = calculatedX + dx;
				toy = calculatedY + dy;

				tempx += tox;
				tempy += toy;

				if(calculatedX == tox){  //move is vertical
					tempx2 = tempx + width - 1;
					tempy2 = tempy;
				}else if(calculatedY == toy){ //move is horizontal
					tempx2 = tempx;
					tempy2 = tempy + height - 1;
				}

				boolean allow = false;

				if(((int)tileMap.getExactTileLocation(bombX) == (int)tileMap.getExactTileLocation(calculatedX) || (int)tileMap.getExactTileLocation(bombX) == (int)tileMap.getExactTileLocation(calculatedX + width -1) ||                     //kun kabubutang la niya bomb pwede pa hiya magmove
						(int)tileMap.getExactTileLocation(bombY) == (int)tileMap.getExactTileLocation(calculatedY) || (int)tileMap.getExactTileLocation(bombY) == (int)tileMap.getExactTileLocation(calculatedY + height - 1)) && bombLocation){
					if(calculateDestination(tempx, tempy) == 3 && ((int)tileMap.getExactTileLocation(tempx) == (int)tileMap.getExactTileLocation(bombX) && (int)tileMap.getExactTileLocation(tempy) == (int)tileMap.getExactTileLocation(bombY))){
						allow = true;
					}
					if(calculateDestination(tempx2, tempy2) == 3 && ((int)tileMap.getExactTileLocation(tempx2) == (int)tileMap.getExactTileLocation(bombX) && (int)tileMap.getExactTileLocation(tempy2) == (int)tileMap.getExactTileLocation(bombY))){
						allow = true;
					}
				}

				if(calculateDestination(tempx, tempy) == 1 && calculateDestination(tempx2, tempy2) == 1 ||
						(calculateDestination(tempx, tempy) >= 4 && calculateDestination(tempx2, tempy2) >= 4) ||allow){ //is tile walkable
					
					testing = "first";
					
					calculatedX = tox;
					calculatedY = toy;
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
						tempx = calculatedX;
						tempy = calculatedY;
						
						tox = calculatedX;
						toy = calculatedY;
						if((up || down) && lastMove == 4){
							tox = calculatedX + (width - (calculatedX % width));
							if(up){
								toy -= moveSpeed;
							}else{
								toy += moveSpeed;
							}
							tempx += moveSpeed;
						}else if((up || down) && lastMove == 3){
							tox = calculatedX - (calculatedX % width);
							if(up){
								toy -= moveSpeed;
							}else{
								toy += moveSpeed;
							}
							tempx -= moveSpeed;
						}else if((right || left) && lastMove == 1){
							toy = calculatedY + (height - (calculatedY % height));
							if(left){
								tox -= moveSpeed;
							}else{
								tox += moveSpeed;
							}
							tempy += moveSpeed;
						}else if((right || left) && lastMove == 2){
							toy = calculatedY - (calculatedY % height);
							if(left){
								tox -= moveSpeed;
							}else{
								tox += moveSpeed;
							}
							tempy -= moveSpeed;
						}

						if(checkDominance() && calculateDestination((int)tox, (int)toy) == 1 && calculateDestination((int)tox + width - 1, (int)toy + height - 1) == 1 ){
							calculatedX = tempx;
							calculatedY = tempy;
							testing = "second";

						}else{
							tox = calculatedX + lastdx;
							toy = calculatedY + lastdy;
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

							if(calculatedX == tox){  //move is vertical
								tempx2 = tempx + width - 1;
								tempy2 = tempy;
							}else if(calculatedY == toy){ //move is horizontal
								tempx2 = tempx;
								tempy2 = tempy + height - 1;
							}

							if((calculateDestination(tempx, tempy) == 1 && calculateDestination(tempx2, tempy2) == 1)  ||
									(calculateDestination(tempx, tempy) >= 4 && calculateDestination(tempx2, tempy2) >= 4)){ //is tile walkable
								calculatedX = tox;
								calculatedY = toy;
								testing = "third";
							}else{
								dx = 0;
								dy = 0;
							}
						}
					}
				}
			}
			
			if(up || down){
				if(bombLocation && (int)tileMap.getExactTileLocation(calculatedY) != (int)tileMap.getExactTileLocation(bombY) && (int)tileMap.getExactTileLocation(calculatedY + height - 1) != (int)tileMap.getExactTileLocation(bombY)){
					bombLocation = false;
				}
			}else if( right ||  left){
				if(bombLocation && (int)tileMap.getExactTileLocation(calculatedX) != (int)tileMap.getExactTileLocation(bombX) && (int)tileMap.getExactTileLocation(calculatedX + width - 1) != (int)tileMap.getExactTileLocation(bombX)){
					bombLocation = false;
				}
			}

			if(calculateDestination(bombX, bombY) != 3){
				bombLocation = false;
			}

			if(!clicked){  //make bomberman stop if no keys are pressed
				dx = 0;
				dy = 0;
			}
		}


			if(tempCalX - calculatedX < -3 || tempCalX - calculatedX > 3){
				System.out.println("pota yawa nimeroy");
				System.out.println(testing);
			}
			if(tempCalY - calculatedY < -3 || tempCalY - calculatedY > 3){
				System.out.println("pota yawa nimeroy part 2: " + (tempCalY - calculatedY));
				System.out.println(testing);
			}
		
		/*int x2 = (int)tileMap.getExactTileLocation(calculatedY);
		int y2 = (int)tileMap.getExactTileLocation(calculatedX);
		for(int i = 0; i < powerupLocations.size(); i++) {
			if(x2 == powerupLocations.get(i).x && y2 == powerupLocations.get(i).y) {
				switch(tileMap.getPowerTile(x2, y2)) { 
				case 511: //bomb_powerup;
					if(maxBomb != 2) {
						maxBomb++;
					}
					tileMap.normalizePowerTile(x2, y2);
					powerupLocations.remove(i);
					break;
				case 521: //movespeed_powerup;
					if(moveSpeed < 4.0) {
						moveSpeed += 1.0;
					}
					tileMap.normalizePowerTile(x2, y2);
					powerupLocations.remove(i);
					break;
				case 531: //flame powerup
					tileMap.normalizePowerTile(x2, y2);
					powerupLocations.remove(i);
					if(flameMultiplier < 4) {
						flameMultiplier++;
					}
					System.out.println("fff");
					break;
				}
			}
		}*/		
	}
	
	public String getUpdatedCoordinates(){
		return calculatedX + "," + calculatedY + ",";
	}
	
	public String putBombHorizontal(int x, int y){
		double dominance = tileMap.getExactTileLocation(x) - (int)tileMap.getExactTileLocation(x);
		int col;
		int row;
		if(dominance < 0.5){
			col = tileMap.getColTile(x);
			row = tileMap.getRowTile(y);
		}else{
			col = tileMap.getColTile(x + width);
			row = tileMap.getRowTile(y);
		}
		return col + " " + row + " ";
	}
	
	public String putBombVertical(int x, int y){
		double dominance = tileMap.getExactTileLocation(y) - (int)tileMap.getExactTileLocation(y);
		int col;
		int row;
		if(dominance < 0.5){
			col = tileMap.getColTile(x);
			row = tileMap.getRowTile(y);
		}else{
			col = tileMap.getColTile(x);
			row = tileMap.getRowTile(y + height);
		}
		return col + " " + row + " ";
		
	}
	
	public void putBomb(int col, int row){
		bombLocation = true;
		tileMap.putBomb(col, row);
		bombX = col*width;
		bombY = row*height;
	}
	
	public int checkDominanceParams(){  //to return kun 70 percent or 30 percent han tile an kailangan igkita
		if(((up || down) && lastMove == 4) || ((right || left) && lastMove == 1)){
			return 70;
		}
		return 30;
	}
	
	public boolean checkDominance(){
		int check = checkDominanceParams();
		double dominance;
		if(lastMove == 1 || lastMove == 2){
			dominance = tileMap.getExactTileLocation(y) - (int)tileMap.getExactTileLocation(y);
		}else{
			dominance = tileMap.getExactTileLocation(x) - (int)tileMap.getExactTileLocation(x);
		}
		
		if(check == 70){
			if(dominance > 0.7 ){
				return true;
			}
			return false;
		}
		
		if(dominance < 0.3){
			return true;
		}
		return false;
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

