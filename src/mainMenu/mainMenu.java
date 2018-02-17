package mainMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class mainMenu extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel base1;
	private JPanel leftBase1;
	private JPanel rightBase1;
	
	private JPanel leftBase2Top; //Title Panel
	private JPanel leftBase2Bottom; // Play Button
	private JPanel rightBase2Top; //User Info Panel
	private JPanel rightBase2Center; // Settings Button
	private JPanel rightBase2Bottom;
	
	private JPanel rightBase2BottomLeft; //Credits Panel
	private JPanel rightBase2BottomRight; //Quit Button
	
	
	public mainMenu()
	{
		super();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Bomberman");
		this.getContentPane().setBackground(Color.DARK_GRAY);
		
		this.setLayout(new BorderLayout());
		initializeComponents();
		
	}
	
	private void initializeComponents()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		int screenMarginWidth = (int)(screenSize.getWidth() * 0.10);
		int screenMarginHeight = (int)(screenSize.getHeight() * 0.10);
		
		//show left and right division
		base1 = new JPanel();
		GridLayout g1 = new GridLayout(0, 2, 5 , 0);
		base1.setLayout(g1);
		base1.setBorder(new EmptyBorder(screenMarginHeight, screenMarginWidth, screenMarginWidth, screenMarginWidth)); //EmptyBorder(top,left,bottom,right)
		leftBase1 = new JPanel();
		//leftBase1.setBackground(Color.BLUE);
		rightBase1 = new JPanel();
		//rightBase1.setBackground(Color.GREEN);
		base1.add(leftBase1);
		base1.add(rightBase1);
		this.add(base1, BorderLayout.CENTER);
		
		//show left top and bot division
		GridLayout g2 = new GridLayout(2,0,0,5);
		leftBase1.setLayout(g2);
		leftBase2Top = new JPanel();
		leftBase2Top.setBackground(Color.DARK_GRAY);
		leftBase2Bottom = new JPanel();
		leftBase2Bottom.setBackground(Color.GRAY);
		leftBase1.add(leftBase2Top);
		leftBase1.add(leftBase2Bottom);
		
		//show right top, center, bot division
		GridLayout g3 = new GridLayout(3,0,0,5);
		rightBase1.setLayout(g3);
		rightBase2Top = new JPanel();
		rightBase2Top.setBackground(Color.DARK_GRAY);
		rightBase2Center = new JPanel();
		rightBase2Center.setBackground(Color.GRAY);
		rightBase2Bottom = new JPanel();
		//rightBase2Bottom.setBackground(Color.LIGHT_GRAY);
		rightBase1.add(rightBase2Top);
		rightBase1.add(rightBase2Center);
		rightBase1.add(rightBase2Bottom);
		
		//show right bottom left,right division
		GridLayout g4 = new GridLayout(0,2,5,0);
		rightBase2Bottom.setLayout(g4);
		rightBase2BottomLeft = new JPanel();
		rightBase2BottomLeft.setBackground(Color.DARK_GRAY);
		rightBase2BottomRight = new JPanel();
		rightBase2BottomRight.setBackground(Color.GRAY);
		rightBase2Bottom.add(rightBase2BottomLeft);
		rightBase2Bottom.add(rightBase2BottomRight);
		
		
	}
}
