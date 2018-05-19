package networking;
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
			String coordinates = gamePanel.getPlayerCoordinates("PLAYER1");
			coordinates += gamePanel.getPlayerCoordinates("PLAYER2");
			send(playersIP[0], playersPortNumber[0], ("UPDATE_POSITION " + coordinates));
			send(playersIP[1], playersPortNumber[1], ("UPDATE_POSITION " + coordinates));
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendBomb(){
		String bombCoordinates = gamePanel.getCalculatedPlayerBomb("PLAYER1");
		bombCoordinates += gamePanel.getCalculatedPlayerBomb("PLAYER2");
		send(playersIP[0], playersPortNumber[0], ("BOMB " + bombCoordinates));
		send(playersIP[1], playersPortNumber[1], ("BOMB " + bombCoordinates));
	}

}
