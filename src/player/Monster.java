package player;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import game.TileMap;

public class Monster extends Thread{
	private PlayerMovementAnimation animation;
	private ArrayList<Integer> available = new ArrayList<Integer>();
	private int availableCounter;
	private double x;
	private double y;
	private double calculatedX;
	private double calculatedY;
	
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
	private boolean appear = true;
	private TileMap tileMap;
	private int width;
	private int height;
	private int lastMove;
	private Player players[];
	private boolean faceleft;
	private boolean first = true;
	public Monster(TileMap tileMap, double x, double y,  int width, int height, Player player[]){
		animation = new PlayerMovementAnimation(5);
		this.tileMap = tileMap;
		this.calculatedX = x;
		this.calculatedY = y;
		this.width = width;
		this.height = height;
		players = player;
		moveSpeed = 3;
	}
	
	public void run(){
		//TO EDIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		while(true){
			for(int a = 0; a < 2; a++){
				if((x + width - 1 >= players[a].getX() && x <= players[a].getX() && y + height - 1 >= players[a].getY() && y <= players[a].getY() ||
					x + width - 1 >= players[a].getX() + width - 1 && x <= players[a].getX() + width - 1 && y + height - 1 >= players[a].getY() && y <= players[a].getY() || 
					x + width - 1 >= players[a].getX() && x <= players[a].getX() && y + height - 1 >= players[a].getY() + height - 1 && y <= players[a].getY() + height - 1 ||
					x + width - 1 >= players[a].getX() + width - 1 && x <= players[a].getX() + width - 1&& y + height - 1 >= players[a].getY() + height - 1&& y <= players[a].getY() + height - 1) && appear){
					players[a].killPlayer();
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
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
	
	public void kill(){
		appear = false;
	}
	
	public String calculateMonsterCoordinates(){
		if(first){
			first = false;
			this.start();
		}
		calculateMovement();
		if(up){
			calculatedY -= moveSpeed;
		}else if(down){
			calculatedY += moveSpeed;
		}else if(left){
			calculatedX -= moveSpeed;
		}else if(right){
			calculatedX += moveSpeed;
		}
		
		if((calculateDestination(calculatedX, calculatedY) > 5 && calculateDestination(calculatedX, calculatedY) <= 500 || calculateDestination(calculatedX + width - 1, calculatedY) > 5 && calculateDestination(calculatedX + width - 1, calculatedY) <= 500) || 
				(calculateDestination(calculatedX, calculatedY + height - 1) > 5 && calculateDestination(calculatedX, calculatedY + height - 1) <= 500|| calculateDestination(calculatedX + width - 1, calculatedY + height -1) > 5 && calculateDestination(calculatedX + width - 1, calculatedY + height -1) <= 500)){
			return "DEAD";
		}
		
		return calculatedX + "," + calculatedY;
	}
	
	public void calculateMovement(){
		Random rand = new Random();
		int alternate = 0;
		boolean clear = true;
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		if(calculatedY - moveSpeed > 0){
			if((calculateDestination(calculatedX, calculatedY - moveSpeed) == 1 && calculateDestination(calculatedX + width - 1, calculatedY - moveSpeed) == 1) || 
					(calculateDestination(calculatedX + width-1, calculatedY - moveSpeed) > 5 && calculateDestination(calculatedX + width-1, calculatedY - moveSpeed) > 5)){
				if(lastMove != 2){
					possibleMoves.add(1);
				}else{
					alternate = 1;
				}
			}
		}
		if(calculatedY + moveSpeed < tileMap.getMapHeight() * height){
			if((calculateDestination(calculatedX , calculatedY + moveSpeed) == 1 && calculateDestination(calculatedX + width-1, calculatedY + moveSpeed) == 1 &&
					calculateDestination(calculatedX , calculatedY + moveSpeed + height -1) == 1 && calculateDestination(calculatedX + width-1, calculatedY + moveSpeed + height -1) == 1) || 
					(calculateDestination(calculatedX, calculatedY + moveSpeed) > 5 && calculateDestination(calculatedX + width-1, calculatedY + moveSpeed) > 5 &&
					calculateDestination(calculatedX, calculatedY + moveSpeed + height - 1) > 5 && calculateDestination(calculatedX + width-1, calculatedY + moveSpeed + height - 1) > 5)){
				if(lastMove != 1){
					possibleMoves.add(2);
				}else{
					alternate = 2;
				}
			}
		}
		if(calculatedX - moveSpeed > 0){
			if((calculateDestination(calculatedX - moveSpeed, calculatedY) == 1 && calculateDestination(calculatedX - moveSpeed, calculatedY + height-1) == 1) || 
					(calculateDestination(calculatedX - moveSpeed, calculatedY) > 5 && calculateDestination(calculatedX - moveSpeed, calculatedY + height-1) > 5)){
				if(lastMove != 4){
					possibleMoves.add(3);
				}else{
					alternate = 3;
				}
			}
		}
		if(calculatedX + moveSpeed < tileMap.getMapWidth() * width){
			if((calculateDestination(calculatedX + moveSpeed, calculatedY ) == 1 && calculateDestination(calculatedX + moveSpeed, calculatedY + height -1) == 1 &&
					calculateDestination(calculatedX + moveSpeed + width - 1, calculatedY ) == 1 && calculateDestination(calculatedX + moveSpeed + width - 1, calculatedY + height -1) == 1) || 
					(calculateDestination(calculatedX + moveSpeed, calculatedY ) > 5 && calculateDestination(calculatedX + moveSpeed, calculatedY + height-1) > 5 &&
					calculateDestination(calculatedX + moveSpeed + width - 1, calculatedY ) > 5 && calculateDestination(calculatedX + moveSpeed + width - 1, calculatedY + height-1) > 5)){
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
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void update(double tox, double toy){

		
		if(toy - y < 0){
			faceleft = false;
			animation.setMove(1);
		}else if(toy - y > 0){
			faceleft = false;
			animation.setMove(2);
		}else if(tox - x < 0){
			faceleft = true;
			animation.setMove(3);
		}else if(tox - x > 0){
			faceleft = false;
			animation.setMove(3);
		}else{
			animation.setMove(12);
		}
		x = tox;
		y = toy;
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
