

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExchangeRateServer extends Thread {

	public static void main(String[] args) {
		new ExchangeRateServer();
	}

	Map<String, Double> giatri = new HashMap<String, Double>();

	public ExchangeRateServer() {
		giatri.put("USD", 25000.0);
		giatri.put("EUR", 24000.0);
		giatri.put("JPY", 160.0);
		giatri.put("VND", 1.0);
		this.start();
		try {
			DatagramSocket TT = new DatagramSocket(5000);
			while (true) {
				// Nhan tin
				DatagramPacket lt1 = new DatagramPacket(new byte[100], 100);
				TT.receive(lt1);
				// Xu ly
				String msg = new String(lt1.getData()).substring(0, lt1.getLength());
				String s = Xuly(msg);
				// Phan hoi
				DatagramPacket lt2 = new DatagramPacket(s.getBytes(), s.length(), lt1.getAddress(), lt1.getPort());
				//if (rand.nextDouble()>0.9)
					TT.send(lt2);
			}
		} catch (Exception e) {
		}
	}

	Random rand = new Random();

	public void run() {
		while (true) {
			try {
				for (String s : giatri.keySet()) {
					Double gt = giatri.get(s) * (1 + rand.nextDouble() * 0.02 - 0.01);
					giatri.put(s, gt);
				}
				Thread.sleep(100);
			} catch (Exception e) {

			}
		}
	}

	String Xuly(String s) {
		try {
			String cmd = "ExchangeRate";
			if (s.startsWith(cmd)) {
				String s2 = s.substring(cmd.length());
				String ltien[] = s2.split("to");
				double tg = TyGia(ltien[0], ltien[1]);
				String resp = s + ":" + tg;
				return resp;
			}
		} catch (Exception e) {
		}
		return "Error";
	}

	double TyGia(String t1, String t2) {
		return giatri.get(t1) / giatri.get(t2);
	}
}
