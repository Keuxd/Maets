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
	public void download() throws Exception {
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
	
}
