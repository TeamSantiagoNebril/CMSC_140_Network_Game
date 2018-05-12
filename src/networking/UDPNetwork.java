package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPNetwork {
	public String receive(int portNumber){
		DatagramSocket socket;
		String received  = "";
		try {
			socket = new DatagramSocket(portNumber);
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			
			System.out.println("yawaaaaaaaaa");
			
			received = new String(packet.getData(), 0, packet.getLength());
			received = received + " " + packet.getAddress();
			socket.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return received;
	}
	
	public void send(String ipAddress, int portNumber, String message){
		byte[] buf = new byte[256];
		buf = message.getBytes();
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
			InetAddress address = InetAddress.getByName(ipAddress);
			DatagramPacket packet= new DatagramPacket(buf, buf.length, address, portNumber);
			socket.send(packet);
			socket.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
