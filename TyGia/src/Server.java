import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//server luong 1: random ty gia, luong 2: nhan tin tu client va phan hoi lai
public class Server implements Runnable{
	public static void main(String args[]) {
		new Server();
	}
	
	Map<String, Double> tygia= new HashMap<String, Double>();
	
	public Server() {
		tygia.put("USD", 25000.0);
		tygia.put("EUR", 24000.0);
		tygia.put("JPY", 160.0);
		tygia.put("VND", 1.0);
		
		new Thread(this).start(); 
		new ServerResponse(this).start();
	}

	Random r= new Random();
	@Override
	public void run() {
		while(true) {
			for(String key : tygia.keySet()) {
				Double val= tygia.get(key) * (1 + r.nextDouble()*0.02 - 0.01);
				tygia.put(key, val);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class ServerResponse extends Thread{
	Server sv;
	public ServerResponse(Server sv) {
		this.sv=sv;
	}
	public void run() {
		try {
			DatagramSocket tt= new DatagramSocket(5000);
			String cmd="ExchangeRate";
			while(true) {
				//nhan tin
				DatagramPacket lt= new DatagramPacket(new byte[100], 100);
				tt.receive(lt);
				String content= new String(lt.getData()).substring(0, lt.getLength());
				
				if (!content.startsWith(cmd)) continue;
				
				//xu li tin
				content= content.substring(cmd.length());
				String[] ltien= content.split("to");
				
				//phan hoi tin
				content= cmd + ltien[0] + "to" + ltien[1] + ":" + (double)sv.tygia.get(ltien[0])/ sv.tygia.get(ltien[1]);
				lt= new DatagramPacket(content.getBytes(), content.length(),
										lt.getAddress(), lt.getPort());
				//if (sv.r.nextDouble()>0.9)
					tt.send(lt);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}

