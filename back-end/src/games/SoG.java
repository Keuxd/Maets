package games;

import java.io.File;

import config.LocalConfigs;
import core.FileSplitMerge;
import core.Main;
import core.ZipExtractor;
import download.cloudservices.GitHub;

public class SoG extends AbstractGame {

	public SoG() {
		super(
				0,
				"Secrets Of Grindea",
				"Secrets of Grindea is an old-school Action RPG with co-op support for up to 4 players.\nIt is a tribute to and sometimes a parody of the old SNES games so many of us grew up with and loved!\nJourney through fantastical lands and battle tons of different enemies and bosses in your quest for truth, friendship and, above all, finding the world's rarest treasures!",
				Main.GAMES_FOLDER_PATH + "Secrets Of Grindea/"
				);
		
	}

	@Override
	public void download(int serverId) throws Exception {
		createLocalGameFolderPath();
		File tempFileFolder = new File(getLocalGameFolderPath() + "Temp/");
		tempFileFolder.mkdir();
		
		GitHub gh = new GitHub("Uekra", "SoG", "main", "SoG.db");
		for(int i = 1; i <= 100; i++) {
			gh.changeFileName("SoG.db" + i);
			GitHub.downloadFile(gh, tempFileFolder.getPath() + "/SoG.db" + i);
//			Electron.response("download " + getId() + " " + i);
		}
		
		LocalConfigs.changeGameState(getId() + "", 1);
	}
	
	@Override
	public boolean isDownloadAvailable() {
		try {
			GitHub gh = new GitHub("Uekra", "SoG", "main", "SoG.db1");
			return GitHub.isFileAvailable(gh);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void install() throws Exception {
		// Installing
		File tempFileFolder = new File(getLocalGameFolderPath() + "Temp/");
		File mergedFile = new File(tempFileFolder.getPath() + "/SoG.zip");
		File fileOne = new File(tempFileFolder + "/SoG.db1");
		FileSplitMerge.mergeFilesInPercentage(fileOne, mergedFile);
		ZipExtractor.extract(mergedFile.getPath(), getLocalGameFolderPath());		
		
		// Cleaning installation folder
		deleteDir(tempFileFolder);
		mergedFile.delete();
		
		LocalConfigs.changeGameState(getId() + "", 2);
	}

	@Override
	public void uninstall() throws Exception {
		deleteDir(new File(getLocalGameFolderPath()));
	}

	@Override
	public void run() throws Exception {
		File exe = new File(getLocalGameFolderPath() + "database_maets/Secrets of Grindea/Game/Secrets Of Grindea.exe");
		Process game = Runtime.getRuntime().exec(exe.getPath(), null, exe.getParentFile());
		
		if(Main.currentRunningGame == null) {
			Main.currentRunningGame = new Thread() {
				@Override
				public void run() {
					try {
						game.waitFor();
						Main.currentRunningGame = null;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			Main.currentRunningGame.setName("Current Running Game Thread:" + getId());
			Main.currentRunningGame.run();
		}
	}
	
}
