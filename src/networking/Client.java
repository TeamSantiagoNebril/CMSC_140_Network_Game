package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import game.GamePanel;
public class Client extends UDPNetwork{
	private String hostIPAddress;
	private int hostPortNumber;
	private int portNumber;
	private GamePanel gamePanel;
	private String ipAddress;
	public Client(String hostIPAddress, int hostPortNumber, int portNumber, GamePanel gamePanel){
		this.hostIPAddress = hostIPAddress;
		this.hostPortNumber = hostPortNumber;
		this.portNumber = portNumber;
		this.gamePanel = gamePanel;
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		send(hostIPAddress, hostPortNumber, "JOIN " + portNumber + " " + ipAddress);
		
		String received[] = receive(portNumber).split(" ");
		if(received[0].equals("POSITIONS")){
			gamePanel.init(received[1]);
		}
		
		send(hostIPAddress, hostPortNumber, "READY");
		
		String xCoordinates = "";
		String yCoordinates = "";
		String powerUp = "";
		
		String received2[] = receive(portNumber).split(" ");
		if(received2[0].equals("XCOORDINATE")){
			xCoordinates = received2[1];
		}
		send(hostIPAddress, hostPortNumber, "RECEIVED");

		received2 = receive(portNumber).split(" ");
		if(received2[0].equals("YCOORDINATE")){
			yCoordinates = received2[1];
		}
		send(hostIPAddress, hostPortNumber, "RECEIVED");
		
		received2 = receive(portNumber).split(" ");
		if(received2[0].equals("POWERUP")){
			powerUp = received2[1];
		}
		send(hostIPAddress, hostPortNumber, "RECEIVED");
		
		gamePanel.setMap(xCoordinates, yCoordinates, powerUp);
		
		while(true){
			String received3[] = receive(portNumber).split(" ");
			if(received3[0].equals("START_GAME")){
				break;
			}
		}
		gamePanel.setController(this);
		ClientReceive clientReceive = new ClientReceive(portNumber, gamePanel, hostIPAddress, hostPortNumber);
		clientReceive.addNotify();
	}
	
	public void killPlayer(String playerName){
		send(hostIPAddress, hostPortNumber, ("KILL " + playerName + " " + ipAddress + portNumber));
	}
	
	public void requestMovementUpdate(int move){
		send(hostIPAddress, hostPortNumber, ("UPDATE_POSITION " + move + " " + ipAddress + portNumber));
	}

	public void requestBomb(){
		send(hostIPAddress, hostPortNumber, ("BOMB " + ipAddress + portNumber));
	}
	
}
