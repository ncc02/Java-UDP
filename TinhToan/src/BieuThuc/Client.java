package BieuThuc;
import javax.swing.JFrame;

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
	
	JTextField txtRequ;
	JButton btnSubmit;
	JTextField txtResp;
	DatagramSocket tt;
	
	public Client() {
		this.setTitle("UDP Client");
		this.setSize(400, 300);
		this.setDefaultCloseOperation(3);
		this.setLayout(null);
		
		txtRequ= new JTextField("Client require");
		txtRequ.setBounds(100, 25, 200, 50);
		btnSubmit= new JButton("Submit");
		btnSubmit.addActionListener(this);
		btnSubmit.setBounds(100, 100, 200, 50);
		txtResp= new JTextField("Server response");
		txtResp.setEditable(false);
		txtResp.setBounds(100, 175, 200, 50);
		
		this.add(txtRequ);
		this.add(btnSubmit);
		this.add(txtResp);
		
		try {
			tt= new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		new Thread(this).start();
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			DatagramPacket lt= new DatagramPacket(txtRequ.getText().getBytes(), txtRequ.getText().length(), InetAddress.getLocalHost(), 5000);
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
				if (!content.startsWith("=")) continue;
				
				txtResp.setText(content);
			
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}

}
