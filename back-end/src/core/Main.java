package core;

import java.io.IOException;

import config.LocalConfigs;
import config.OnlineConfigs;

public class Main {
	
	public static final String MAETS_FOLDER_PATH = System.getenv("LOCALAPPDATA") + "/Maets/";
	
	public static void main(String[] args) throws IOException {
		Electron.initTCPConnection();
		
		OnlineConfigs.initialize(MAETS_FOLDER_PATH);
		LocalConfigs.initialize(MAETS_FOLDER_PATH);
		
		System.out.println("Finish main thread");
	}
}