package networking;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.xml.stream.events.StartDocument;

import game.GamePanel;
public class Server extends UDPNetwork implements Runnable {
	private int portNumber;
	private GamePanel gamePanel;
	private String playersIP[] = new String[4];
	private int playersPortNumber[] = new int[4];
	private boolean player1Walking;
	private boolean player2Walking;
	private boolean player3Walking;
	private boolean player4Walking;
	public Server(int portNumber, GamePanel gamePanel){
		this.gamePanel = gamePanel;
		this.portNumber = portNumber;

		addNotify();
	}
	
	public void waitForPlayerConnections(){
		int a = 0;
		while(a < 2){
			String receivedString[] = receive(portNumber).split(" ");
			if(receivedString[0].equals("JOIN")){
				playersPortNumber[a] = Integer.parseInt(receivedString[1]);
				playersIP[a] = receivedString[2];
				a++;
			}
		}
	}
	
	public void addNotify(){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		waitForPlayerConnections();
		for(int a = 0; a < 2; a++){
			send(playersIP[a], playersPortNumber[a], "POSITIONS 48,48,96,48,48,96,96,96");
		}
		
		int a = 0;
		while(a<2){
			String received[] = receive(portNumber).split(" ");
			if(received[0].equals("READY")){
				a++;
			}else{
				continue;
			}
		}
	
		for(a = 0; a < 2; a++){
			//System.out.println(playersIP[a] + " " + playersPortNumber[a]);
			send(playersIP[a], playersPortNumber[a], "START_GAME");
		}
		
		ServerSend serverSend = new ServerSend(playersIP, playersPortNumber, gamePanel);
		serverSend.addNotify();
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true){
			String receivedString[] = receive(portNumber).split(" ");
			if(receivedString[0].equals("UPDATE_POSITION")){
				if(Integer.parseInt(receivedString[1]) < 5){					
					new Thread(){
						public void run(){
							if(receivedString[2].equals(playersIP[0] + playersPortNumber[0])){
								player1Walking = true;
								while(player1Walking){
									gamePanel.calculateUpdatePositions("PLAYER1 " + receivedString[1]);
									try {
										Thread.sleep(30);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}else if(receivedString[2].equals(playersIP[1] + playersPortNumber[1])){
								player2Walking = true;
								while(player2Walking){
									gamePanel.calculateUpdatePositions("PLAYER2 " + receivedString[1]);
									try {
										Thread.sleep(30);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}.start();
				}else{
					if(receivedString[2].equals(playersIP[0] + playersPortNumber[0])){
						
						player1Walking = false;
						gamePanel.calculateUpdatePositions("PLAYER1 " + receivedString[1]);
					}else if(receivedString[2].equals(playersIP[1] + playersPortNumber[1])){
						player2Walking = false;
						gamePanel.calculateUpdatePositions("PLAYER2 " + receivedString[1]);
					}
				}
			}
		}
	}
	
	
}
