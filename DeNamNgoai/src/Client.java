import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.*;

public class Client extends JFrame implements ActionListener, Runnable{

	public static void main(String[] args) {
		new Client();
	}

	JTextField txt;
	DatagramSocket tt;
	JLabel res;
	
	public Client() {
		this.setTitle("Client Sent / Server Response");
		this.setSize(800, 300);
		this.setDefaultCloseOperation(3);
		
		this.setLayout(null);
		
		try {
			tt= new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lbl= new JLabel("Nhập chuỗi cần gửi : ");
		txt= new JTextField();
		JButton btn= new JButton("Submit");
		res= new JLabel("server response");
		
		lbl.setBounds(50,50, 150, 50);
		txt.setBounds(230,50, 250, 50);
		btn.setBounds(550,50, 150, 50);
		res.setBounds(230,150, 270, 50);
		
		
		this.add(lbl);
		this.add(txt);
		this.add(btn);
		this.add(res);
		
		btn.addActionListener(this);
		
		new Thread(this).start();
		
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			DatagramPacket lt= new DatagramPacket(txt.getText().getBytes(), txt.getText().length(),
													InetAddress.getLocalHost(), 5000);
			tt.send(lt);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	
	@Override
	public void run() {
		while(true) {
			DatagramPacket lt= new DatagramPacket(new byte[100], 100);
			try {
				tt.receive(lt);
				String content= new String(lt.getData()).substring(0, lt.getLength());
				if (!content.startsWith("Date")) continue; //ko phai server phan hoi thi bo qua
				
				res.setText(content);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
