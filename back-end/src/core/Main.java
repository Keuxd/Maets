package core;

import mega.Mega;
import mega.CommandsEnum;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		Electron.initTCPConnection();
		
		int code = Mega.run(CommandsEnum.VERSION);
		Electron.response("mega " + code);
		
		System.out.println("Finish main thread");
	}
}