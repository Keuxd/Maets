package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Scanner;

import mega.CommandsEnum;
import mega.Mega;

public class Electron {

	private static final int PORT_NUMBER = 1234;
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	
	public static void init() {
		if(serverSocket != null) {
			System.out.println("TCP connection called with server socket connected, program probably running, ignoring call...");
			return;
		}
		
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			System.out.println("Running Electron..");
			System.out.println("Listening to port: " + PORT_NUMBER);
			runExe();
			
			clientSocket = serverSocket.accept();
			System.out.println("Connected to: " + clientSocket.getInetAddress());
			
			initTCPThread();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void initTCPThread() {
			Thread electronListener = new Thread(() -> {
				try {
					while(true) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						
						String message = reader.readLine();
						System.out.println("\n+ Received message: " + message);
						
						// Electron was closed
						if(message == null) break;
						
						Requests.process(message);
					}
					System.out.println("Stop listening to: " + clientSocket.getInetAddress());
					Mega.run(CommandsEnum.QUIT);
				} catch(IOException e) {
					e.printStackTrace();
				} finally {
					resetConnection();
				}
			});
			
			electronListener.setName("Electron Listener");
			electronListener.start();
			System.out.println("Start listening to: " + clientSocket.getInetAddress() + "\n");
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
	
	private static void runExe() throws URISyntaxException, IOException {
		String maetsFolder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();

		Process process = new ProcessBuilder(maetsFolder + "\\fe.exe", "java").start();
		
		Thread logReader = new Thread(() -> {
			Scanner scanner = new Scanner(process.getInputStream(), "UTF-8");
			while(scanner.hasNextLine()) {
				String lastOutput;
				lastOutput = scanner.nextLine();
				System.out.println("Electron Console: " + lastOutput);
			}
			scanner.close();
		});
		
		logReader.setName("ElectronPrompt listener");
		logReader.start();
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