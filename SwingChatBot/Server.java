import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Server {

	//local variable declaration 
	private JFrame frmServerChat;
	private JTextField textField;
	private static   JTextArea textArea;
	private JButton btnNewButton;
	static ServerSocket server ;
	static Socket con;
	private JScrollPane scrollPane;
	

	
	public static void main(String[] args) throws IOException   {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
					window.frmServerChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		 serverConnection();
	}

	
	
	private static void serverConnection() throws IOException {
		server = new ServerSocket(8080);
		
		 con = server.accept();
		 while (true) {
			try {
				
				DataInputStream input = new DataInputStream(con.getInputStream());
				String string = input.readUTF();
				textArea.setText(textArea.getText() + "\n " + "Server: " + string);
			} catch (Exception ev) {
				 textArea.setText(textArea.getText()+ "\n" +"Network issues ");
				 
					try {
						Thread.sleep(2000);
						System.exit(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		}
	}

	

	public Server() throws IOException {
		initialize();	 
	}

	

	private void initialize() throws IOException {
		
		
		frmServerChat = new JFrame();
		frmServerChat.setTitle("Server Chat");
		frmServerChat.setBounds(100, 100, 605, 403);
		frmServerChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerChat.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 67, 344, 38);
		frmServerChat.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Send");
		
		btnNewButton.addActionListener(new ActionListener() {
			 
			public void actionPerformed(ActionEvent e) {
				
				if (textField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please write some text !");
			 
				}
				else if(textField.isFocusable()){
					btnNewButton.setEnabled(true);
					textArea.setText(textArea.getText() + "\n" + "Server : " + textField.getText());
					try {
						DataOutputStream output = new DataOutputStream(con.getOutputStream());
						output.writeUTF(textField.getText());
					} catch (IOException e1) {
						textArea.setText(textArea.getText() + "\n " + " Network issues");
						try {
							Thread.sleep(2000);
							System.exit(0);
						} catch (InterruptedException e2) {
							
							e2.printStackTrace();
						}

					}
					textField.setText("");
				}
			}
		});
		btnNewButton.setBounds(390, 66, 164, 38);
		frmServerChat.getContentPane().add(btnNewButton);
		 
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 134, 557, 157);
		frmServerChat.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	}
}