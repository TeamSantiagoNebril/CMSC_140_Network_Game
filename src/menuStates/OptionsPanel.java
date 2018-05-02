package menuStates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainMenu.ButtonPanel;
import mainMenu.MainMenuManager;

public class OptionsPanel extends JPanel{
	
	private MainMenuManager manager;
	private JPanel sideButtonsArea;
	private JPanel[] subSideButtonsArea = new JPanel[4];
	private JPanel mainArea;
	private JLabel optionsTitleLabel;
	private ButtonPanel[] sideButtonPanels = new ButtonPanel[3];
	private ButtonPanel backPanelButton;
	
	public OptionsPanel() {
		this.setLayout(new BorderLayout());
		
		
		sideButtonsArea = new JPanel();
		sideButtonsArea.setLayout(new GridLayout(5, 0, 0, 30)); //rows, cols, hgap, vgap
		for(int counter = 0; counter < 4; counter++) {
			subSideButtonsArea[counter] = new JPanel();
			subSideButtonsArea[counter].setLayout(new GridLayout(2,0,0,30));
		}
		optionsTitleLabel = new JLabel("   Options   ");
		optionsTitleLabel.setFont(new Font("FREEDOM", Font.BOLD, 40));
		sideButtonsArea.add(optionsTitleLabel);
		
		sideButtonPanels[0] = new ButtonPanel("Audio");
		sideButtonPanels[0].addTitleLabel("Audio", 30);
		sideButtonPanels[0].setBackgroundModified(Color.YELLOW);
		sideButtonPanels[1] = new ButtonPanel("Controls");
		sideButtonPanels[1].addTitleLabel("Controls", 30);
		sideButtonPanels[1].setBackgroundModified(Color.GREEN);
		sideButtonPanels[2] = new ButtonPanel("Guide");
		sideButtonPanels[2].addTitleLabel("Guide", 30);
		sideButtonPanels[2].setBackgroundModified(Color.CYAN);
		
		subSideButtonsArea[0].add(new JPanel());
		subSideButtonsArea[0].add(sideButtonPanels[0]);
		subSideButtonsArea[1].add(sideButtonPanels[1]);
		subSideButtonsArea[1].add(sideButtonPanels[2]);
		
		
		backPanelButton = new ButtonPanel("Back");
		backPanelButton.addTitleLabel("Back", 30);
		backPanelButton.setBackgroundModified(Color.DARK_GRAY);
		
		subSideButtonsArea[2].add(new JPanel());
		subSideButtonsArea[2].add(new JPanel());
		subSideButtonsArea[3].add(backPanelButton);
		subSideButtonsArea[3].add(new JPanel());
		
		for(int counter = 0; counter < 4; counter++) { //1 -> 4 only
			sideButtonsArea.add(subSideButtonsArea[counter]);
		}
		
		
		
		mainArea = new JPanel();
		mainArea.setBackground(Color.BLUE);

		
		this.add(sideButtonsArea, BorderLayout.WEST);
		this.add(mainArea,  BorderLayout.CENTER);
		this.repaint();
	}
	
}
