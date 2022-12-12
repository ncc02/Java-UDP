import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

public class Client extends JFrame implements Runnable {

	public static void main(String[] args) {
		new Client();
	}
	Map<String, String> tygia= new HashMap<String, String>();
	public Client() {
		this.setTitle("Bang ty gia");
		this.setSize(800, 500);
		this.setDefaultCloseOperation(3);
		
		new Thread(this).start();
		
		this.setVisible(true);
	}

	@Override
	public void run() {
		try {
			
			DatagramSocket tt=new DatagramSocket();
			
			new ClientReceive(this, tt).start();
			
			
			List<String> ltien= new ArrayList<String>();
			ltien.add("USD");
			ltien.add("EUR");
			ltien.add("JPY");
				
			String cmd="ExchangeRate";
			while(true) {
				
				for(String s:ltien) {
					
					String content= cmd + s + "toVND";
					DatagramPacket lt=new DatagramPacket(content.getBytes(), content.length()
														,InetAddress.getLocalHost(), 5000);
					tt.send(lt);
					
					
				}
				Thread.sleep(1000);
			}

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.BLUE);
		g.setFont(new Font("arial", Font.BOLD, 30));
		
		int y=150;
		for(String key : tygia.keySet()) {
			
			g.drawString("Ty gia " + key + " : " + tygia.get(key), 50, y);
			y += 50;
		}
		
	}
}


class ClientReceive extends Thread{
	Client cl;
	DatagramSocket tt;
	public ClientReceive(Client cl, DatagramSocket tt) {
		this.cl= cl;
		this.tt= tt;
	}
	public void run() {
		try {
			
			
			String cmd="ExchangeRate";
			while(true) {
				
				DatagramPacket lt=new DatagramPacket(new byte[100], 100);
				tt.receive(lt);
				
				String content= new String(lt.getData());
				
				if (!content.startsWith(cmd)) continue;
				content= content.substring(0, lt.getLength()).substring(cmd.length());
				
				String key= content.split(":")[0];
				String value= content.split(":")[1];
				
				cl.tygia.put(key, value);
				
				cl.repaint();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}