package networking;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import game.GamePanel;
public class Server extends UDPNetwork implements Runnable {
	private int portNumber;
	private GamePanel gamePanel;
	private String playersIP[] = new String[4];
	private int playersPortNumber[] = new int[4];
	public Server(int portNumber, GamePanel gamePanel){
		this.gamePanel = gamePanel;
		this.portNumber = portNumber;
		waitForPlayerConnections();
		for(int a = 0; a < 1; a++){
			send(playersIP[a], playersPortNumber[a], "POSITIONS 48,48,96,48,48,96,96,96");
		}
		
		int a = 0;
		while(a<1){
			String received[] = receive(portNumber).split(" ");
			if(received[0].equals("READY")){
				a++;
			}else{
				continue;
			}
			
		}
	
		for(a = 0; a < 1; a++){
			System.out.println(playersIP[a] + " " + playersPortNumber[a]);
			send(playersIP[a], playersPortNumber[a], "START_GAME");
		}
	}
	
	public void waitForPlayerConnections(){
		int a = 0;
		while(a < 1){
			String receivedString[] = receive(portNumber).split(" ");
			if(receivedString[0].equals("JOIN")){
				playersPortNumber[a] = Integer.parseInt(receivedString[1]);
				playersIP[a] = receivedString[2].split("/")[1];
				a++;
			}
		}
	}
	
	public void addNotify(){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		
	}
}
