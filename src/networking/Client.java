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
	public Client(String hostIPAddress, int hostPortNumber, int portNumber, GamePanel gamePanel){
		this.hostIPAddress = hostIPAddress;
		this.hostPortNumber = hostPortNumber;
		this.portNumber = portNumber;
		
		send(hostIPAddress, hostPortNumber, "JOIN " + portNumber);
		
		
		String received[] = receive(portNumber).split(" ");
		if(received[0].equals("POSITIONS")){
			gamePanel.init(received[1]);
		}
		
		send(hostIPAddress, hostPortNumber, "READY");
		
		while(true){
			System.out.println(portNumber);
			String received2[] = receive(portNumber).split(" ");
			if(received2[0].equals("START_GAME")){
				break;
			}
		}
		
		
	}
	
	
}
