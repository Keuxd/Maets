package games;

import core.Main;

public class VS extends AbstractGame {
	
	public VS() {
		super(
				1,
				"Vampire Survivors",
				"Vampire Survivors is a casual gothic horror game with roguelite elements where your choices can make you grow quickly and annihilate the thousands of monsters that appear along the way.\nVampire Survivors is a survival game with minimalist gameplay and roguelite elements.\nHell is empty. All the demons are here, and there is no way to hide. All he can do is survive as long as he can until death finally ends his suffering. Collect gold in matches to buy upgrades and help the next survivor.\nThe supernatural indie phenomenon that turns you into a hail of bullets!",
				Main.GAMES_FOLDER_PATH + "Vampire Survivors/"
				);
	}

	@Override
	public void download(int serverId) throws Exception {}

	@Override
	public boolean isDownloadAvailable(int serverId) {
		return false;
	}

	@Override
	public void install() throws Exception {}

	@Override
	public void uninstall() throws Exception {}

	@Override
	public void run() throws Exception {}

	@Override
	public boolean[] getAvailableDownloads() {
		// TODO Auto-generated method stub
		return null;
	}

}
