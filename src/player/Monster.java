package player;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Monster extends Player{
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
	private boolean faceleft = false;
	
	private double tempx;
	private double tempy;
	
	public Monster(double x, double y){
		super();
		animation = new PlayerMovementAnimation(5);
		
		this.x = x;
		this.y = y;
	}
	
	public void dead(){
		
	}
	
	public void setLeft(boolean bool){ left = bool; }
	public void setRight(boolean bool){ right = bool; }
	public void setUp(boolean bool){ up = bool; }
	public void setDown(boolean bool){ down = bool; }
	
	public void calculateAvailable(int x, int y){
		available.clear();
		availableCounter = 0;
		if(calculateDestination(x , y - 48) == 1){
			available.add(1);
			availableCounter++;
		}
		if(calculateDestination(x , y + 96) == 1){
			available.add(2);
			availableCounter++;
		}
		if(calculateDestination(x - 48, y) == 1){
			available.add(3);
			availableCounter++;
		}
		if(calculateDestination(x + 96, y) == 1){
			available.add(4);
			availableCounter++;
		}
	}
	
	public void update(){
		calculateAvailable((int)x, (int)y);
		Random rand = new Random();

		int  n = rand.nextInt(availableCounter) + 1;
		if(n == 1){
			setRight(true);
		}else if(n == 2){
			setDown(true);
		}else if(n == 3){
			setLeft(true);
		}else if(n == 4){
			setRight(true);
		}
		
		if(up){
			dx =  0;
			dy = -moveSpeed;
			faceleft = false;
		}else if(down){
			dx = 0;
			dy = moveSpeed;
			//tempy = height - 1;
			faceleft = false;
		}else if(right){
			dx = moveSpeed;
			dy = 0;
			//tempx = width - 1;
			faceleft = false;
		}else if(left){
			dx = -moveSpeed;
			dy = 0;
			faceleft = true;
		}
	}
	
	public void draw(Graphics2D g){
		
	}
	
}
