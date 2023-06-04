package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	
	public static void main(String[] args) {
		final int PORT_NUMBER = 1234;
		ServerSocket serverSocket = null;
		int executions = 0;
		
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			System.out.println("Listening to port: " + PORT_NUMBER);
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client Connected: " + clientSocket.getInetAddress());
			while(true) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				
				String message = reader.readLine();
				System.out.println("Received message: " + message);
				if(message == null) break;
				
				String response = dealWithRequest(message) + " " + executions;
				if(response != null) {
					System.out.println("Sending: " + response);
					printWriter.println(response);
				}
				
				executions++;
				System.out.println("Code passed: " + executions);			
			}
			clientSocket.close();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static String dealWithRequest(String requestMessage) {
		switch(requestMessage) {
		 	case "buttonPress" : return "buttonPress Received";
			default: return null;
		}
	}
}