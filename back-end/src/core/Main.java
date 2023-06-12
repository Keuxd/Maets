package core;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		Electron.initTCPConnection();
		
		System.out.println("Finish main thread");
	}
}