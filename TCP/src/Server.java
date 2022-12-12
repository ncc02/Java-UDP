import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Server {

	public static void main(String[] args) {
		new Server();

	}

	public Server() {
		try {
			ServerSocket server = new ServerSocket(5000);
			Socket soc = server.accept();
			while (true) {
				
			//
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				String content = dis.readUTF();
				System.out.println(content);
				//
				if (isDateValid(content)) {
					if (isStringPrime(content))
						content= "Day la 1 ngay tuyet voi";
					else 
						content="Chuoi theo dinh dang dd/MM/yyyy";
				}
				else 
					content="Chuoi khong theo dinh dang dd/MM/yyyy";
				
				//phan hoi cho client
				DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
				dos.writeUTF(content);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	final static String DATE_FORMAT = "dd/MM/yyyy";
	public static boolean isDateValid(String dateString) 
	{
	        try {
	        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT); //dinh dang format
	        	LocalDate ldate= LocalDate.parse(dateString, dtf);
	        	
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	}
	
	public Boolean isStringPrime(String s) {
		String[] a=s.split("/");
		int n0= Integer.parseInt(a[0]);
		int n1= Integer.parseInt(a[1]);
		int n2= Integer.parseInt(a[2]);
		return isPrime(n0) && isPrime(n1) && isPrime(n2);
	}
	public Boolean isPrime(int n) {
		for(int i=2; i*i<=n; i++)
			if (n%i==0) return false;
		return n>=2;
	}
}


