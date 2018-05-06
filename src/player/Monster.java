package player;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import game.TileMap;

public class Monster{
	private PlayerMovementAnimation animation;
	private ArrayList<Integer> available = new ArrayList<Integer>();
	private int availableCounter;
	private double x;
	private double y;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	private double dx;
	private double dy;
	private double tox;
	private double toy;
	
	private double moveSpeed;
	
	private double tempx;
	private double tempy;
	
	private TileMap tileMap;
	private int width;
	private int height;
	private int lastMove;
	
	private boolean faceleft;
	public Monster(TileMap tileMap, double x, double y,  int width, int height){
		animation = new PlayerMovementAnimation(5);
		this.tileMap = tileMap;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		moveSpeed = 6;
	}
	
	public int calculateDestination(double x, double y){
		int col = tileMap.getColTile((int) x );
		int row = tileMap.getRowTile((int) y );
		return tileMap.getTile(row, col);
	}
	
	public void setLeft(boolean bool){ left = bool; }
	public void setRight(boolean bool){ right = bool; }
	public void setUp(boolean bool){ up = bool; }
	public void setDown(boolean bool){ down = bool; }
	
	public void calculateMovement(){
		Random rand = new Random();
		int alternate = 0;
		boolean clear = true;
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		if(y - moveSpeed > 0){
			if((calculateDestination(x, y - moveSpeed) == 1 && calculateDestination(x + width - 1, y - moveSpeed) == 1) || 
					(calculateDestination(x + width-1, y - moveSpeed) > 5 && calculateDestination(x + width-1, y - moveSpeed) > 5)){
				if(lastMove != 2){
					possibleMoves.add(1);
				}else{
					alternate = 1;
				}
			}
		}
		if(y + moveSpeed < tileMap.getMapHeight() * height){
			if((calculateDestination(x , y + moveSpeed) == 1 && calculateDestination(x + width-1, y + moveSpeed) == 1 &&
					calculateDestination(x , y + moveSpeed + height -1) == 1 && calculateDestination(x + width-1, y + moveSpeed + height -1) == 1) || 
					(calculateDestination(x, y + moveSpeed) > 5 && calculateDestination(x + width-1, y + moveSpeed) > 5 &&
					calculateDestination(x, y + moveSpeed + height - 1) > 5 && calculateDestination(x + width-1, y + moveSpeed + height - 1) > 5)){
				if(lastMove != 1){
					possibleMoves.add(2);
				}else{
					alternate = 2;
				}
			}
		}
		if(x - moveSpeed > 0){
			if((calculateDestination(x - moveSpeed, y) == 1 && calculateDestination(x - moveSpeed, y + height-1) == 1) || 
					(calculateDestination(x - moveSpeed, y) > 5 && calculateDestination(x - moveSpeed, y + height-1) > 5)){
				if(lastMove != 4){
					possibleMoves.add(3);
				}else{
					alternate = 3;
				}
			}
		}
		if(x + moveSpeed < tileMap.getMapWidth() * width){
			if((calculateDestination(x + moveSpeed, y ) == 1 && calculateDestination(x + moveSpeed, y + height -1) == 1 &&
					calculateDestination(x + moveSpeed + width - 1, y ) == 1 && calculateDestination(x + moveSpeed + width - 1, y + height -1) == 1) || 
					(calculateDestination(x + moveSpeed, y ) > 5 && calculateDestination(x + moveSpeed, y + height-1) > 5 &&
					calculateDestination(x + moveSpeed + width - 1, y ) > 5 && calculateDestination(x + moveSpeed + width - 1, y + height-1) > 5)){
				if(lastMove != 3){
					
					possibleMoves.add(4);
				}else{
					alternate = 4;
				}
			}
		}
		
		if(possibleMoves.size() == 0){ //bagan repel
			if(lastMove == 1 && alternate == 2){
				alternate = 0;
				lastMove = 2;
				possibleMoves.add(2);
			}else if(lastMove == 2 && alternate == 1){
				alternate = 0;
				lastMove = 1;
				possibleMoves.add(1);
			}else if(lastMove == 3 && alternate == 4){
				alternate = 0;
				lastMove = 4;
				possibleMoves.add(4);
			}else if(lastMove == 4 && alternate == 3){
				alternate = 0;
				lastMove = 3;
				possibleMoves.add(3);
			}
		}
		
		if(possibleMoves.size() == 0){
			lastMove = 0;
			setUp(false);
			setLeft(false);
			setRight(false);
			setDown(false);
		}else{
			int num = rand.nextInt(possibleMoves.size());
			int move = possibleMoves.get(num);
			if(move == 1){
				lastMove = 1;
				setUp(true);
				setLeft(false);
				setRight(false);
				setDown(false);
			}else if(move == 2){
				lastMove = 2;
				setUp(false);
				setLeft(false);
				setRight(false);
				setDown(true);
			}else if(move == 3){
				lastMove = 3;
				setUp(false);
				setLeft(true);
				setRight(false);
				setDown(false);
			}else if(move == 4){
				lastMove = 4;
				setUp(false);
				setLeft(false);
				setRight(true);
				setDown(false);
			}
		}
	}
	
	public void update(){
		calculateMovement();
		
		if(up){
			y -= moveSpeed;
			faceleft = false;
			animation.setMove(1);
		}else if(down){
			y += moveSpeed;
			faceleft = false;
			animation.setMove(2);
		}else if(left){
			x -= moveSpeed;
			faceleft = true;
			animation.setMove(3);
		}else if(right){
			x += moveSpeed;
			faceleft = false;
			animation.setMove(3);
		}else{
			animation.setMove(12);
		}
	}
	public void draw(Graphics2D g){
		if(faceleft){
			g.drawImage(animation.getImage(), (int)x + width, (int)y, -width, height, null);
		}else{
			g.drawImage(animation.getImage(), (int)x, (int)y, width, height, null);
		}
	}
}
