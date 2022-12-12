import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class client {

	public static void main(String[] args) {
		try {
			DatagramSocket TT1 = new DatagramSocket(4999);
			
				String LT1 = "Hello, minh la C9, chao p2 nhe!";
				
				DatagramPacket BT1 = new DatagramPacket(LT1.getBytes(), LT1.length(), InetAddress.getLocalHost(), 5000);
				TT1.send(BT1);
					
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
