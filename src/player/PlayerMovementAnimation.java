package player;

import java.awt.Image;
import java.awt.Toolkit;

public class PlayerMovementAnimation {
	private int frame;
	private int move;
	private int lastMove;
	private Image lastImage;
	private Image walkUp1;
	private Image walkUp2;
	private Image walkUp3;
	private Image walkUp4;
	private Image walkUp5;
	private Image walkUp6;
	private Image walkUp7;
	private Image walkUp8;
	private Image walkDown1;
	private Image walkDown2;
	private Image walkDown3;
	private Image walkDown4;
	private Image walkDown5;
	private Image walkDown6;
	private Image walkDown7;
	private Image walkDown8;
	private Image walkSide1;
	private Image walkSide2;
	private Image walkSide3;
	private Image walkSide4;
	private Image walkSide5;
	private Image walkSide6;
	private Image walkSide7;
	private Image walkSide8;
	private Image dead1;
	private Image dead2;
	private Image dead3;
	private Image dead4;
	private Image dead5;
	private Image dead6;
	private Image dead7;
	private Image dead8;
	private int playerNumber;
	public PlayerMovementAnimation(int playerNumber){
		this.playerNumber = playerNumber;
		Toolkit t=Toolkit.getDefaultToolkit();
		if(playerNumber == 1 || playerNumber == 2){
			walkUp1 = t.getImage("assets/images/Sprites/Bomberman/Back/Bman_B_f00.png");
			walkUp2 = t.getImage("assets/images/Sprites/Bomberman/Back/Bman_B_f01.png");
			walkUp3 = t.getImage("assets/images/Sprites/Bomberman/Back/Bman_B_f02.png");
			walkUp4 = t.getImage("assets/images/Sprites/Bomberman/Back/Bman_B_f03.png");
			walkUp5 = t.getImage("assets/images/Sprites/Bomberman/Back/Bman_B_f04.png");
			walkUp6 = t.getImage("assets/images/Sprites/Bomberman/Back/Bman_B_f05.png");
			walkUp7 = t.getImage("assets/images/Sprites/Bomberman/Back/Bman_B_f06.png");
			walkUp8 = t.getImage("assets/images/Sprites/Bomberman/Back/Bman_B_f07.png");
			walkDown1 = t.getImage("assets/images/Sprites/Bomberman/Front/Bman_F_f00.png");
			walkDown2 = t.getImage("assets/images/Sprites/Bomberman/Front/Bman_F_f01.png");
			walkDown3 = t.getImage("assets/images/Sprites/Bomberman/Front/Bman_F_f02.png");
			walkDown4 = t.getImage("assets/images/Sprites/Bomberman/Front/Bman_F_f03.png");
			walkDown5 = t.getImage("assets/images/Sprites/Bomberman/Front/Bman_F_f04.png");
			walkDown6 = t.getImage("assets/images/Sprites/Bomberman/Front/Bman_F_f05.png");
			walkDown7 = t.getImage("assets/images/Sprites/Bomberman/Front/Bman_F_f06.png");
			walkDown8 = t.getImage("assets/images/Sprites/Bomberman/Front/Bman_F_f07.png");
			walkSide1 = t.getImage("assets/images/Sprites/Bomberman/Side/Bman_F_f00.png");
			walkSide2 = t.getImage("assets/images/Sprites/Bomberman/Side/Bman_F_f01.png");
			walkSide3 = t.getImage("assets/images/Sprites/Bomberman/Side/Bman_F_f02.png");
			walkSide4 = t.getImage("assets/images/Sprites/Bomberman/Side/Bman_F_f03.png");
			walkSide5 = t.getImage("assets/images/Sprites/Bomberman/Side/Bman_F_f04.png");
			walkSide6 = t.getImage("assets/images/Sprites/Bomberman/Side/Bman_F_f05.png");
			walkSide7 = t.getImage("assets/images/Sprites/Bomberman/Side/Bman_F_f06.png");
			walkSide8 = t.getImage("assets/images/Sprites/Bomberman/Side/Bman_F_f07.png");
			dead1 = t.getImage("assets/images/Sprites/Bomberman/Dead/Bman_D_f00.png");
			dead2 = t.getImage("assets/images/Sprites/Bomberman/Dead/Bman_D_f01.PNG");
			dead3 = t.getImage("assets/images/Sprites/Bomberman/Dead/Bman_D_f01_5.PNG");
			dead4 = t.getImage("assets/images/Sprites/Bomberman/Dead/Bman_D_f02.PNG");
			dead5 = t.getImage("assets/images/Sprites/Bomberman/Dead/Bman_D_f02_5.PNG");
			dead6 = t.getImage("assets/images/Sprites/Bomberman/Dead/Bman_D_f03.PNG");
			dead7 = t.getImage("assets/images/Sprites/Bomberman/Dead/Bman_D_f04.PNG");
			dead8 = t.getImage("assets/images/Sprites/Bomberman/Dead/Bman_D_f05.PNG");
		}else if(playerNumber == 5){
			walkUp1 = t.getImage("assets/images/Sprites/creep/Back/Creep_B_f00.png");
			walkUp2 = t.getImage("assets/images/Sprites/creep/Back/Creep_B_f01.png");
			walkUp3 = t.getImage("assets/images/Sprites/creep/Back/Creep_B_f02.png");
			walkUp4 = t.getImage("assets/images/Sprites/creep/Back/Creep_B_f03.png");
			walkUp5 = t.getImage("assets/images/Sprites/creep/Back/Creep_B_f04.png");
			walkUp6 = t.getImage("assets/images/Sprites/creep/Back/Creep_B_f05.png");
			walkDown1 = t.getImage("assets/images/Sprites/creep/Front/Creep_F_f00.png");
			walkDown2 = t.getImage("assets/images/Sprites/creep/Front/Creep_F_f01.png");
			walkDown3 = t.getImage("assets/images/Sprites/creep/Front/Creep_F_f02.png");
			walkDown4 = t.getImage("assets/images/Sprites/creep/Front/Creep_F_f03.png");
			walkDown5 = t.getImage("assets/images/Sprites/creep/Front/Creep_F_f04.png");
			walkDown6 = t.getImage("assets/images/Sprites/creep/Front/Creep_F_f05.png");
			walkSide1 = t.getImage("assets/images/Sprites/creep/Side/Creep_S_f00.png");
			walkSide2 = t.getImage("assets/images/Sprites/creep/Side/Creep_S_f01.png");
			walkSide3 = t.getImage("assets/images/Sprites/creep/Side/Creep_S_f02.png");
			walkSide4 = t.getImage("assets/images/Sprites/creep/Side/Creep_S_f03.png");
			walkSide5 = t.getImage("assets/images/Sprites/creep/Side/Creep_S_f04.png");
			walkSide6 = t.getImage("assets/images/Sprites/creep/Side/Creep_S_f05.png");
		}
		lastImage = walkDown1;
	}
	
	public void setMove(int move){
		if(move != lastMove || (frame > 7 && playerNumber != 5) || (frame == 5 && playerNumber == 5)){
			frame = 0;
		}
		lastMove = move;
		this.move = move;
	}
	
	private Image getImageUp(){
		lastImage = walkUp1;
		frame++;
		if(frame == 0){
			return walkUp1;
		}else if(frame == 1){
			return walkUp2;
		}else if(frame == 2){
			return walkUp3;
		}else if(frame == 3){
			return walkUp4;
		}else if(frame == 4){
			return walkUp5;
		}else if(frame == 5){
			return walkUp6;
		}else if(frame == 6){
			return walkUp7;
		}
		return walkUp8;
	}
	
	private Image getImageDown(){
		lastImage = walkDown1;
		frame++;
		if(frame == 0){
			return walkDown1;
		}else if(frame == 1){
			return walkDown2;
		}else if(frame == 2){
			return walkDown3;
		}else if(frame == 3){
			return walkDown4;
		}else if(frame == 4){
			return walkDown5;
		}else if(frame == 5){
			return walkDown6;
		}else if(frame == 6){
			return walkDown7;
		}
		return walkDown8;
	}
	
	private Image getImageSide(){
		lastImage = walkSide1;
		frame++;
		if(frame == 0){
			return walkSide1;
		}else if(frame == 1){
			return walkSide2;
		}else if(frame == 2){
			return walkSide3;
		}else if(frame == 3){
			return walkSide4;
		}else if(frame == 4){
			return walkSide5;
		}else if(frame == 5){
			return walkSide6;
		}else if(frame == 6){
			return walkSide7;
		}
		return walkSide8;
	}
	
	private Image getImageIdle(){
		return lastImage;
	}
	
	public void setDead(int a){
		move = a;
	}
	
	public Image getImageDead(){
		if(move == 4){
			return dead1;
		}else if(move == 5){
			return dead2;
		}else if(move == 6){
			return dead3;
		}else if(move == 7){
			return dead4;
		}else if(move == 8){
			return dead5;
		}else if(move == 9){
			return dead6;
		}else if(move == 10){
			return dead7;
		}
		return dead8;
	}
	
	public Image getImage(){
		if(move == 1){
			return getImageUp();
		}else if(move == 2){
			return getImageDown();
		}else if(move == 3){
			return getImageSide();
		}else if(move > 3 && move < 12){
			return getImageDead();
		}
		return getImageIdle();
	}
	
	
}
