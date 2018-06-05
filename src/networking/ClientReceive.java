package networking;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import game.GamePanel;
public class ClientReceive extends UDPNetwork implements Runnable{
	private int portNumber;
	private GamePanel gamePanel;
	private String hostIPAddress;
	private int hostPortNumber;
	private String receivedBombId = "";
	public ClientReceive(int portNumber , GamePanel gamePanel, String hostIPAddress, int hostPortNumber){
		this.portNumber = portNumber;
		this.gamePanel = gamePanel;
		this.hostIPAddress = hostIPAddress;
		this.hostPortNumber = hostPortNumber;
	}
	
	public void run(){
		int counter = 0;
		while(true){
			String receivedString[] = receive(portNumber).split(" ");
			//new Thread(){
				//public void run(){
					if(receivedString[0].equals("UPDATE_POSITION")){
						gamePanel.updatePositions(receivedString[1]);
					}else if(receivedString[0].equals("BOMB_COORDINATES")){
						if(!receivedBombId.equals(receivedString[1])){
							gamePanel.setBombLocation(receivedString);
							receivedBombId = receivedString[1];
						}
					}else if(receivedString[0].equals("KILL")){
						gamePanel.killPlayer(receivedString[1]);
					}else if(receivedString[0].equals("MONSTER")){
						gamePanel.setMonsterCoordinates(receivedString[1]);
					}else if(receivedString[0].equals("POWERUP")){
						gamePanel.setPowerUp(receivedString);
					}else if(receivedString[0].equals("DIED")){
						gamePanel.setDiedPlayer(receivedString);
					}
				//}
			//}.start();
		}
	}
	
	public void addNotify(){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	
}
