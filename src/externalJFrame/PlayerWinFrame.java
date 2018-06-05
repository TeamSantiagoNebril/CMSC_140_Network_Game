package externalJFrame;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class PlayerWinFrame extends JFrame{

	
	public PlayerWinFrame() {
		super("You lose");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		JLabel loseLabel = new JLabel("You Lose!");
		add(loseLabel);
		//setOpaque(false);

		setBackground(new Color(0,0,0,0));
		setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}
