package core;

import java.io.IOException;

import config.LocalConfigs;
import config.OnlineConfigs;

public class Main {
	
	public static final String MAETS_FOLDER_PATH = System.getenv("LOCALAPPDATA") + "/Maets/";
	public static final String GAMES_FOLDER_PATH = MAETS_FOLDER_PATH + "Games/";
	
	public static void main(String[] args) throws IOException {
		Electron.init();
		
		OnlineConfigs.initialize(MAETS_FOLDER_PATH);
		LocalConfigs.initialize(MAETS_FOLDER_PATH);
		
		System.out.println("Finish main thread");
	}
}