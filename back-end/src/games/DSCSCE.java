package games;

import core.Main;

public class DSCSCE extends AbstractGame {

	public DSCSCE() {
		super(
				2,
				"Digimon Story Cyber Sleuth Complete Edition",
				"With engaging storylines, classic turn-based battles, and tons of Digimon to collect, Digimon Story Cyber Sleuth: Complete Edition delivers everything fans loved about Digimon Story: Cyber Sleuth and Digimon Story: Cyber Sleuth – Hacker’s Memory.\nGet the full experience with the Complete Edition which includes both titles in one!\n",
				Main.GAMES_FOLDER_PATH + "Digimon Story Cyber Sleuth Complete Edition/"
				);
	}

	@Override
	public void download(int serverId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDownloadAvailable(int serverId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void install() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uninstall() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean[] getAvailableDownloads() {
		// TODO Auto-generated method stub
		return null;
	}

}
