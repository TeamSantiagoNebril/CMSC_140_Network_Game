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
	public ClientReceive(int portNumber , GamePanel gamePanel, String hostIPAddress, int hostPortNumber){
		this.portNumber = portNumber;
		this.gamePanel = gamePanel;
		this.hostIPAddress = hostIPAddress;
		this.hostPortNumber = hostPortNumber;
	}
	
	public void run(){
		
		while(true){
			String receivedString[] = receive(portNumber).split(" ");
			if(receivedString[0].equals("UPDATE_POSITION")){
				gamePanel.updatePositions(receivedString[1]);
			}else if(receivedString[0].equals("BOMB")){
				gamePanel.setPlayerBomb(receivedString[1]);
			}
		}
	}
	
	public void addNotify(){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	
}
