import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client extends JFrame implements ActionListener, Runnable{

	private JPanel contentPane;
	private JTextField txtRequire;
	private JTextField txtResponse;
	DatagramSocket tt;
	private JButton btnSubmit;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client() {
		try {
			tt= new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setTitle("UDP Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblClient = new JLabel("Client require :");
		lblClient.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblServer = new JLabel("Server response :");
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtRequire = new JTextField();
		txtRequire.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtRequire.setColumns(10);
		
		txtResponse = new JTextField();
		txtResponse.setEditable(false);
		txtResponse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtResponse.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(8)
							.addComponent(lblServer, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtResponse, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblClient, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtRequire, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)))
					.addGap(31))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(324, Short.MAX_VALUE)
					.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addGap(146))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(49, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtRequire, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblClient, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addGap(64)
							.addComponent(txtResponse, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(46))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblServer, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addGap(79))))
		);
		contentPane.setLayout(gl_contentPane);
		
		btnSubmit.addActionListener(this);
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(true) {
			DatagramPacket lt= new DatagramPacket(new byte[100], 100);
			try {
				tt.receive(lt);
				String content= new String(lt.getData()).substring(0, lt.getLength());
				if (!content.startsWith("Date")) continue; //ko phai server phan hoi thi bo qua
				
				System.out.println(content);
				txtResponse.setText(content);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			DatagramPacket lt= new DatagramPacket(txtRequire.getText().getBytes(), txtRequire.getText().length(),
													InetAddress.getLocalHost(), 5000);
			tt.send(lt);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
