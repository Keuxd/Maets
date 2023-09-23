package core;

import java.io.File;
import java.io.IOException;

import config.LocalConfigs;
import config.OnlineConfigs;

public class Main {
	
	public static final String MAETS_FOLDER_PATH = System.getenv("LOCALAPPDATA") + "/Maets/";
	public static final String GAMES_FOLDER_PATH = MAETS_FOLDER_PATH + "Games/";
	
	public static Thread currentRunningGame;
	
	public static void main(String[] args) throws IOException {
		Electron.init();
		
		initializeFiles();
		
		System.out.println("Finish main thread");
	}
	
	private static void initializeFiles() throws IOException {
		OnlineConfigs.initialize(MAETS_FOLDER_PATH);
		LocalConfigs.initialize(MAETS_FOLDER_PATH);
		
		// Games Folder
		new File(GAMES_FOLDER_PATH).mkdir();
	}
}