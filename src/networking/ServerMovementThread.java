package networking;

import game.GamePanel;

public class ServerMovementThread extends Thread{
	private boolean running = true;
	private String playerName;
	private String receivedString;
	private GamePanel gamePanel;
	private ServerSend serverSend;
	public ServerMovementThread(String playerName, String receivedString, GamePanel gamePanel, ServerSend serverSend){
		this.playerName = playerName;
		this.receivedString = receivedString;
		this.gamePanel = gamePanel;
		this.serverSend = serverSend;
	}
	
	public void run(){
		while(running){
			gamePanel.calculateUpdatePositions(playerName + " " + receivedString);
			serverSend.sender();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopThread(String receivedString){
		running = false;
		gamePanel.calculateUpdatePositions(playerName + " " + receivedString);
	}
}
