import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.*;

public class Client extends JFrame implements ActionListener, Runnable{

	public static void main(String[] args) {
		new Client();

	}
	
	JTextField txtRequ;
	JButton btnSubmit;
	JTextField txtResp;
	Socket soc;
	DataInputStream dis;
	DataOutputStream dos;
	
	public Client() {
		this.setTitle("TCP Client");
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
			soc = new Socket("localhost", 5000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		new Thread(this).start();
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeUTF(txtRequ.getText());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	@Override
	public void run() {
		while(true) {
			try {
				dis = new DataInputStream(soc.getInputStream());
				String content = dis.readUTF();
				//if (!content.startsWith("Date")) continue;
				
				txtResp.setText(content);
			
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}

}
