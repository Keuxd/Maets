package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import mega.CommandsEnum;
import mega.Mega;

public class Electron {

	private static final int PORT_NUMBER = 1234;
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	
	public static void initTCPConnection() {
		if(serverSocket != null) {
			System.out.println("TCP connection called with server socket connected, ignoring call...");
			return;
		}
		
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			System.out.println("Listening to port: " + PORT_NUMBER);
			
			clientSocket = serverSocket.accept();
			System.out.println("Connected to: " + clientSocket.getInetAddress());
			
			Thread electronListener = new Thread(() -> {
				try {
					int code = Mega.run(CommandsEnum.VERSION);
					Electron.response("mega " + code);
					
					while(true) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						
						String message = reader.readLine();
						System.out.println("\n+ Received message: " + message);
						
						// Electron was closed
						if(message == null) break;
						
						Requests.process(message);
					}
					System.out.println("Stop listening to: " + clientSocket.getInetAddress());
//					Mega.run(CommandsEnum.QUIT);
				} catch(IOException e) {
					e.printStackTrace();
				} finally {
					resetConnection();
					initTCPConnection();
				}
			});
			
			electronListener.setName("Electron Listener");
			electronListener.start();
			System.out.println("Start listening to: " + clientSocket.getInetAddress() + "\n");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void resetConnection() {
		try {
			clientSocket.close();
			serverSocket.close();			

			clientSocket = null;
			serverSocket = null;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void response(String message) {
		try {
			PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
			printWriter.println(message);
			System.out.println("\n- Sent message: " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}