package networking;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.stream.events.StartDocument;

import game.GamePanel;
public class Server extends UDPNetwork implements Runnable {
	private int portNumber;
	private GamePanel gamePanel;
	private String playersIP[] = new String[4];
	private int playersPortNumber[] = new int[4];
	
	private int map[][];
	private int powerupMap[][];  
	private ArrayList<Point> powerupLocations;
	private int mapWidth;
	private int mapHeight;
	private String file = "assets/GameInit/tileMap.txt";
	
	private boolean player1Walking;
	private boolean player2Walking;
	private boolean player3Walking;
	private boolean player4Walking;
	ServerMovementThread movement1 = null;
	ServerMovementThread movement2 = null;
	ServerMovementThread movement3 = null;
	ServerMovementThread movement4 = null;
	int lastMove1 = 0;
	int lastMove2 = 0;
	int lastMove3 = 0;
	int lastMove4 = 0;
	private int counter;
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
		
		generateMap();
	
		for(a = 0; a < 2; a++){
			send(playersIP[a], playersPortNumber[a], "START_GAME");
		}
		
		ServerSend serverSend = new ServerSend(playersIP, playersPortNumber, gamePanel);
		serverSend.addNotify();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(true){
		
			String receivedString[] = receive(portNumber).split(" ");
			if(receivedString[0].equals("UPDATE_POSITION")){
				if(Integer.parseInt(receivedString[1]) < 5){	
					if(receivedString[2].equals(playersIP[0] + playersPortNumber[0])){
						if(player1Walking){
							movement1.stopThread(lastMove1 + 5 + "");
						}
						movement1 = new ServerMovementThread("PLAYER1", receivedString[1], gamePanel, serverSend);
						lastMove1 = Integer.parseInt(receivedString[1]);
						player1Walking = true;
						movement1.start();
					}else if(receivedString[2].equals(playersIP[1] + playersPortNumber[1])){
						if(player2Walking){
							movement2.stopThread(lastMove2 + 5 + "");
						}
						movement2 = new ServerMovementThread("PLAYER2", receivedString[1], gamePanel, serverSend);
						lastMove2 = Integer.parseInt(receivedString[1]);
						player2Walking = true;
						movement2.start();
					}
				}else{
					if(receivedString[2].equals(playersIP[0] + playersPortNumber[0])){
						if(player1Walking){
							movement1.stopThread(receivedString[1]);
							player1Walking = false;
						}
					}else if(receivedString[2].equals(playersIP[1] + playersPortNumber[1])){
						if(player2Walking){
							movement2.stopThread(receivedString[1]);
							player2Walking = false;
						}
					}
				}
			}else if(receivedString[0].equals("BOMB")){
				new Thread(){
					public void run(){
						if(receivedString[1].equals(playersIP[0] + playersPortNumber[0])){
							gamePanel.calculatePlayerBomb("PLAYER1");
						}else if(receivedString[1].equals(playersIP[1] + playersPortNumber[1])){
							gamePanel.calculatePlayerBomb("PLAYER2");
						}
						serverSend.sendBomb();
					}
				}.start();
			}
		}
	}
	
	public void generateMap(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int [mapHeight][mapWidth];
			powerupMap = new int [mapHeight][mapWidth];
			ArrayList<Point> powerupRandomizerVariable = new ArrayList<Point>();		 
			ArrayList<Integer> powerupType = new ArrayList<Integer>();
			powerupLocations = new ArrayList<Point>();
		 
			String mapString = mapWidth + " " + mapHeight + " ";
			for(int row = 0; row < mapHeight; row++){
				String line = br.readLine();
				mapString += line;
				String tokens[] = line.split(" ");
				for(int col = 0; col < mapWidth; col++){
					if(Integer.parseInt(tokens[col]) == 2) {
						powerupRandomizerVariable.add(new Point(row, col));
					}
				}
			}
			
			int[] pNumType = {520, 520, 520};
			for(int counter = 0; counter < 3; counter++) {		 
				for(int counter2 = 0; counter2 < 8; counter2++) {
					powerupType.add(pNumType[counter]);
				}
			}
			
			Random rand = new Random();
			for(int counter = 0; counter < 24; counter++) {
				int position = rand.nextInt(powerupRandomizerVariable.size());
				int pValue = rand.nextInt(powerupType.size());
		        powerupMap[powerupRandomizerVariable.get(position).x][powerupRandomizerVariable.get(position).y] = powerupType.get(pValue);
		        powerupLocations.add(new Point(powerupRandomizerVariable.get(position).x , powerupRandomizerVariable.get(position).y));
		        powerupRandomizerVariable.remove(position);
		        powerupType.remove(pValue);
			}
			br.close();
		}catch(FileNotFoundException e){
			System.err.println("File Not Found");
			System.exit(-1);
		}catch(IOException e){}
	}
}
