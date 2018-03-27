package Game;

import javax.swing.JFrame;

public class Temp {
	public static void main(String args[]){
		JFrame window = new JFrame("Platformer");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new GamePanel());
		window.pack();
		window.setVisible(true);
	}
}
