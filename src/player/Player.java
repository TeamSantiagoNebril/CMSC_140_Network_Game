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
	
	private boolean bombMax;
	private boolean skate;
	private boolean fire;
	private boolean fireDown;
	private boolean pierceBomb;
	private boolean redBomb;
	private boolean heal;
	private String powerUpCoordinates;
	
	private boolean appear = true;
	
	private String bombCoordinates = "";
	private boolean deadFlag = false;
	private boolean dead;
	private int maxBomb = 1;  
	private double moveSpeed;
	private int flameMultiplier = 1;
	private ArrayList<Point> powerupLocations;
	
	private boolean pierce;
	private int maxBombNumber = 3;
	private int bombNumber = maxBombNumber;
	
	private boolean died;
	private int threadRunning = 0;
	
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
		isBombed = false;
		return temp;
	}
	
	public int calculateDestination(double x, double y){
		int col = tileMap.getColTile((int) x );
		int row = tileMap.getRowTile((int) y );
		return tileMap.getTile(row, col);
	}

	public void update(double x, double y){
		if(appear){
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
	}

	
	public void dead(){
		life--;
		if(life == 0){
			died = true;
		}else{
			x = startX;
			y = startY;
			calculatedX = x;
			calculatedY = y;
		}
		System.out.println("Life: " + life );
	}
	
	public boolean isDiedPlayer(){
		return died;
	}
	
	public void diedPlayer(){
		appear = false;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public boolean isplayerDead(){
		if(calculateDestination((int)x, (int)y) >= 4 && !isAnimatingDeadImage || calculateDestination((int)x, (int)y+height - 1) >= 4 && !isAnimatingDeadImage ||
				calculateDestination((int)x + width -1 , (int)y) >= 4 && !isAnimatingDeadImage || dead){
			dead = false;
			return true;
		}
		return false;
	}
	
	public void killPlayer(){
		dead = true;
	}
	
	public void deadPlayer(){
		if(!isAnimatingDeadImage){
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
					animation.setMove(13);
					dead();
					isAnimatingDeadImage = false;
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

		int x2 = (int)tileMap.getExactTileLocation(calculatedY);
		int y2 = (int)tileMap.getExactTileLocation(calculatedX);
		for(int i = 0; i < powerupLocations.size(); i++) {
			if(x2 == powerupLocations.get(i).x && y2 == powerupLocations.get(i).y) {
				switch(tileMap.getPowerTile(x2, y2)) { 
				case 511: //bomb_powerup;
					powerUpCoordinates = x2 + " " + y2;
					bombMax = true;
					break;
				case 521: 
					if(moveSpeed < 4.0) {
						moveSpeed = 4.0;
					}
					powerUpCoordinates = x2 + " " + y2;
					skate = true;
					break;
				case 531:
					powerUpCoordinates = x2 + " " + y2;
					fire = true;
					break;
				case 541: 
					powerUpCoordinates = x2 + " " + y2;
					pierceBomb = true;
					break;
				case 551: 
					powerUpCoordinates = x2 + " " + y2;
					redBomb = true;
					break;
				case 561: 
					powerUpCoordinates = x2 + " " + y2;
					heal = true;
					break;
				}
			}
		}		
	}
	
	public void pierceBomb(){
		pierce = true;
	}
	
	public String getPowerUp(){
		String temp = "";
		if(skate){
			skate = false;
			temp = powerUpCoordinates + " 1";
		}
		if(fire){
			fire = false;
			temp = powerUpCoordinates + " 2";
		}
		if(bombMax){
			bombMax = false;
			temp = powerUpCoordinates + " 3";
		}
		
		if(pierceBomb){
			pierceBomb = false;
			temp = powerUpCoordinates + " 4";
		}
		if(redBomb){
			redBomb = false;
			temp = powerUpCoordinates + " 5";
		}
		if(heal){
			heal = false;
			temp = powerUpCoordinates + " 6";
		}
		return temp;
	}
	
	public void addMaxBomb(){
		if(maxBombNumber <= 5){
			maxBombNumber++;
			bombNumber++;
		}
	}
	
	public void healPlayer(){
		if(life <= 3)
		life++;
	}
	
	public void maxFlame(){
		flameMultiplier = 26;
	}
	
	public void reloadBomb(){
		bombNumber++;
	}
	
	public void addFlame(){
		flameMultiplier++;
	}
	
	public void descreaseFlame(){
		flameMultiplier--;
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
		if(bombNumber > 0){
			bombLocation = true;
			tileMap.putBomb(col, row, flameMultiplier, this, pierce);
			bombX = col*width;
			bombY = row*height;
			bombNumber--;
		}
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
		if(appear){
			if(faceleft){
				g.drawImage(animation.getImage(), (int)x + width, (int)y, -width, height, null);
			}else{
				g.drawImage(animation.getImage(), (int)x, (int)y, width, height, null);
			}
		}
	}
	
}