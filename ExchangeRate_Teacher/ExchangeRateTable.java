package ExchangeRate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

public class ExchangeRateTable extends JFrame implements Runnable {

	public static void main(String[] args) {
		new ExchangeRateTable();
	}

	public ExchangeRateTable() {
		this.setTitle("Exchange Rate Table");
		this.setSize(800, 500);
		this.setDefaultCloseOperation(3);
		new Thread(this).start();

		this.setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLUE);
		g.setFont(new Font("arial", Font.BOLD, 30));
		int y = 100;
		for (String s : tygia.keySet()) {
			g.drawString("Ty gia cua " + s + "doi voi VND:" + tygia.get(s), 50, y);
			y += 50;
		}
	}

	Map<String, Double> tygia = new HashMap<String, Double>();

	public void run() {
		try {
			DatagramSocket TT = new DatagramSocket();
			new PacketRecieve(this, TT).start();
			Set<String> rateName = new HashSet<String>();
			rateName.add("JPY");
			rateName.add("USD");
			rateName.add("EUR");
			while (true) {
				for (String s : rateName) {
					// Gui
					String msg = "ExchangeRate" + s + "toVND";
					DatagramPacket lt1 = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getLocalHost(),
							5000);
					TT.send(lt1);
				}
				Thread.sleep(1000);
			}

		} catch (Exception e) {

		}
	}
}

class PacketRecieve extends Thread {
	ExchangeRateTable ert;
	DatagramSocket TT;

	public PacketRecieve(ExchangeRateTable ert, DatagramSocket TT) {
		this.ert = ert;
		this.TT = TT;
	}

	public void run() {
		while (true) {
			try {
				// Phan hoi 100%???
				DatagramPacket lt2 = new DatagramPacket(new byte[100], 100);
				TT.receive(lt2);
				String resp = new String(lt2.getData()).substring(0, lt2.getLength());
				String s = resp.substring("ExchangeRate".length()).split(":")[0];
				double tg = Double.parseDouble(resp.split(":")[1]);
				ert.tygia.put(s, tg);
				ert.repaint();
			} catch (Exception e) {

			}
		}
	}
}
