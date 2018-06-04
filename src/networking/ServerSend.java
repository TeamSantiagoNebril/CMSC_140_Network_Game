package networking;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import game.GamePanel;
public class ServerSend extends UDPNetwork implements Runnable{
	String playersIP[];
	int playersPortNumber[];
	GamePanel gamePanel;
	public ServerSend(String playersIP[], int playersPortNumber[], GamePanel gamePanel){
		this.playersIP = playersIP;
		this.playersPortNumber = playersPortNumber;
		this.gamePanel = gamePanel;
		
	}
	
	public void addNotify(){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		while(true){
			String temp = gamePanel.getKillPlayer();
			if(!temp.equals("")){
				killPlayer(temp);
			}
			sender();
			sendMonsterCoordinates();
			powerUp();
			playerDied();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void playerDied(){
		String message = gamePanel.getDiedPlayer();
		
		send(playersIP[0], playersPortNumber[0], ("DIED " + message));
		send(playersIP[1], playersPortNumber[1], ("DIED " + message));
	}
	
	public void powerUp(){
		String powerUp = gamePanel.getPowerUp("PLAYER1");
		powerUp += gamePanel.getPowerUp("PLAYER2");
		
		send(playersIP[0], playersPortNumber[0], ("POWERUP " + powerUp));
		send(playersIP[1], playersPortNumber[1], ("POWERUP " + powerUp));
	}
	
	public void sender(){
		String coordinates = gamePanel.getPlayerCoordinates("PLAYER1");
		coordinates += gamePanel.getPlayerCoordinates("PLAYER2");
		//System.out.println(coordinates);
		send(playersIP[0], playersPortNumber[0], ("UPDATE_POSITION " + coordinates));
		send(playersIP[1], playersPortNumber[1], ("UPDATE_POSITION " + coordinates));
	}
	
	public void sendBomb(){
		String bombCoordinates = gamePanel.getCalculatedPlayerBomb("PLAYER1");
		bombCoordinates += gamePanel.getCalculatedPlayerBomb("PLAYER2");
		
		send(playersIP[0], playersPortNumber[0], ("BOMB_COORDINATES " + bombCoordinates));
		send(playersIP[1], playersPortNumber[1], ("BOMB_COORDINATES " + bombCoordinates));
	}
	
	public void sendMonsterCoordinates(){
		String monsterCoordinates = gamePanel.getMonsterCoordinates();
		send(playersIP[0], playersPortNumber[0], ("MONSTER " + monsterCoordinates));
		send(playersIP[1], playersPortNumber[1], ("MONSTER " + monsterCoordinates));	
	}
	
	public void killPlayer(String playerNames){
		send(playersIP[0], playersPortNumber[0], ("KILL " + playerNames));
		send(playersIP[1], playersPortNumber[1], ("KILL " + playerNames));
	}
	
}
