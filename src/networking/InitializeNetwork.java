package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import game.GamePanel;

public class InitializeNetwork {
	private GamePanel gamePanel;
	public InitializeNetwork(GamePanel gamePanel){
		Scanner scan = new Scanner(System.in);
		
		System.out.println("[1] Create Game");
		System.out.println("[2] Join Game");
		System.out.print("Enter choice: ");
		int choice = scan.nextInt();
		scan.nextLine();
		
		int portNumberInput;
		Client client;
		switch(choice){
			case 1:
				System.out.print("Enter port number: ");
				portNumberInput = scan.nextInt();
				scan.nextLine();
				
				String hostIP;
				try {
					hostIP = InetAddress.getLocalHost().getHostAddress();
					Server server = new Server(portNumberInput, gamePanel);
					client = new Client(hostIP, portNumberInput, portNumberInput - 1, gamePanel);
					break;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				
			case 2:
				System.out.print("Enter port number: ");
				portNumberInput = scan.nextInt();
				scan.nextLine();
				System.out.print("Enter host IP and port number: ");
				String input[] = scan.nextLine().split(" ");
				client = new Client(input[0], Integer.parseInt(input[1]), portNumberInput, gamePanel);
				break;
		}
	}
}
