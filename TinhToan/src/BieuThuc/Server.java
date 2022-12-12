package BieuThuc;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Server {

	public static void main(String[] args) {
		new Server();

	}

	public Server() {
		try {
			DatagramSocket tt= new DatagramSocket(5000);
			while(true) {
				//nhan thu tu client
				DatagramPacket lt= new DatagramPacket(new byte[100], 100);
				tt.receive(lt);
				
				//xu li la thu
				String bieuThuc= new String(lt.getData()).substring(0, lt.getLength());
				String content= "= " + String.valueOf(tinh(bieuThuc));
				
				//phan hoi cho client
				lt= new DatagramPacket(content.getBytes(), content.length(), lt.getAddress(), lt.getPort());
				tt.send(lt);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public double tinh(String input) {
//	    ScriptEngineManager mgr = new ScriptEngineManager();    
//	    ScriptEngine engine = mgr.getEngineByName("JavaScript");        
//	    try {
//			return (Double)engine.eval(input);
//		} catch (ScriptException e) {
//			return -9e9;
//		}
//	}
	public static double tinh (String str) {
		double kq = 0; 
		if (str.contains("(")||str.contains(")")) {
			char[] charArr = str.toCharArray();
			int l=0, r=0, dem, dau;
			for (int i=0; i< str.length(); i++) {
				if (charArr[i]=='(') {
					dem=1; 
					dau=1; 
					l = i;
					r = 0;
					for (int j=i+1; j<str.length(); j++) {
						if (charArr[j]=='(') {
							if (dau == 1) dem++;
							else dau = 1;
						}
						if (charArr[j]==')') {
							if (dau==-1) dem--;
							else dau=-1;
						}
						if (dem == 1&&dau==-1) {
							r = j;
							break;
						}
					}
					double kqsub = tinh(str.substring(l+1, r)); 
					str = str.substring(0, l) + String.valueOf(kqsub) + str.substring(r+1); 
					charArr = str.toCharArray();
				}
			}
			return tinhkhongngoac(str);
		} 
		else {
			return tinhkhongngoac(str);
		}
	}
	private static double tinhkhongngoac(String str) {
		String temp = "", tmp = "";
		char[] charArr = str.toCharArray();
		char[] chArr;
		double kq = 0, kqsub = 1;
		int l = 0, ls = 0;
		// tinh kq
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] == '+' || charArr[i] == '-' || i == charArr.length - 1) {
				if (charArr[i] == '+' || charArr[i] == '-') {
					if (i == 0) {
						l = 1;
						continue;
					}
					// i la '+'
					temp = str.substring(l, i);
				} else {
					temp = str.substring(l, charArr.length);
				}
				chArr = temp.toCharArray();
				ls = 0;
				kqsub = 1;
				// tinh kqsub
				for (int j = 0; j < chArr.length; j++) {
					if (chArr[j] == '*' || chArr[j] == '/') {
						// j la '*'
						tmp = temp.substring(ls, j);
						if (ls == 0 || chArr[ls - 1] == '*')
							kqsub *= Double.parseDouble(tmp);
						else
							kqsub /= Double.parseDouble(tmp);
						ls = j + 1;
					} else if (j == chArr.length - 1) {
						tmp = temp.substring(ls, chArr.length);
						if (ls == 0 || chArr[ls - 1] == '*')
							kqsub *= Double.parseDouble(tmp);
						else
							kqsub /= Double.parseDouble(tmp);
					}
				}
				if (l == 0 || charArr[l - 1] == '+')
					kq += kqsub;
				else
					kq -= kqsub;
				l = i + 1;
			}
		}
		return kq;
	}
}


