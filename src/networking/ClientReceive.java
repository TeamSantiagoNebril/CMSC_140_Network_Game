package networking;
import game.GamePanel;
public class ClientReceive extends UDPNetwork implements Runnable{
	private int portNumber;
	private GamePanel gamePanel;
	public ClientReceive(int portNumber , GamePanel gamePanel){
		this.portNumber = portNumber;
		this.gamePanel = gamePanel;
	}
	
	public void run(){
		
		while(true){
			String receivedString[] = receive(portNumber).split(" ");
			if(receivedString[0].equals("UPDATE_POSITION")){
				gamePanel.updatePositions(receivedString[1]);
			}
		}
	}
	
	public void addNotify(){
		Thread thread = new Thread(this);
		thread.start();
	}
}
