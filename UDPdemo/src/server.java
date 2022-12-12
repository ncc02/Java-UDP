import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class server {

	public static void main(String[] args) {
		try {
			DatagramSocket TT2 = new DatagramSocket(5000);
			
			while (true) {
				DatagramPacket BT2 = new DatagramPacket(new byte[100], 100);
				TT2.receive(BT2);
				
				String LT2= new String(BT2.getData());
				System.out.println(LT2);
			}
			
					
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}