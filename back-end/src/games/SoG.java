package games;

import java.io.File;
import java.io.IOException;

import config.LocalConfigs;
import core.FileSplitMerge;
import core.Main;
import core.ZipExtractor;
import download.cloudservices.Discord;
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
		
		switch(serverId) {
			case 0 : downloadGitHub(tempFileFolder); break;
			case 1 : downloadDiscord(tempFileFolder); break;
			default :  throw new Exception("Invalid Server");
		}
	}
	
	private void downloadGitHub(File tempFileFolder) throws Exception {		
		GitHub gh = new GitHub("Uekra", "SoG", "main");
		for(int i = 1; i <= 100; i++) {
			gh.changeFileName("SoG.db" + i);
			GitHub.downloadFile(gh, tempFileFolder.getPath() + "/SoG.db" + i);
//			Electron.response("download " + getId() + " " + i);
		}
		
		LocalConfigs.changeGameState(getId() + "", 1);
	}
	
	private void downloadDiscord(File tempFileFolder) throws IOException {
		Discord disc = new Discord(1157028068553150564l);
		long[] fileIds = getDiscordFileIds();
		
		for(int i = 1; i <= fileIds.length; i++) {
			disc.changeFileId(fileIds[i-1]);
			disc.changeFileName("SoG.db" + i);
			Discord.downloadFile(disc, tempFileFolder.getPath() + "/SoG.db" + i);
			System.out.println("DOWNLOADED " + i + "%");
//			Electron.response("download " + getId() + " " + i);
		}
		
		LocalConfigs.changeGameState(getId() + "", 1);
	}
	
	private long[] getDiscordFileIds() {
		return new long[] {
				1157771494089298192l, 1157771494512926880l, 1157771494949126144l, 1157771495691522138l, 1157771496052236480l, 1157771496454881341l, 1157771496798818344l, 1157771497151143966l, 1157771497516040324l, 1157771497910309054l, 1157771617154383892l, 1157771617510891550l, 1157771617884196934l, 1157771618307817502l, 1157771618672713758l, 1157771619016659075l, 1157771619415113739l, 1157771619763232778l, 1157771620182675476l, 1157771620522410044l, 1157771722502721566l, 1157771722787926058l, 1157771723060543488l, 1157771723370942496l, 1157771723651952660l, 1157771723932958861l, 1157771724260134983l, 1157771724604059648l, 1157771724906037268l, 1157771725220630609l, 1157771821039484978l, 1157771821345677322l, 1157771821639286844l, 1157771821932871740l, 1157771822301978674l, 1157771822616559736l, 1157771822935310357l, 1157771823283445820l, 1157771823572861058l, 1157771823900000317l, 1157771913951719517l, 1157771914362765323l, 1157771914719273011l, 1157771915096764567l, 1157771915398758551l, 1157771915704934470l, 1157771916136951808l, 1157771916417974272l, 1157771916711567460l, 1157771917063893072l, 1157772010231963739l, 1157772010626224231l, 1157772010928218253l, 1157772011234398308l, 1157772011565744241l, 1157772011876130908l, 1157772012207476947l, 1157772012790481048l, 1157772013092479076l, 1157772013486739546l, 1157772101470662788l, 1157772101793615923l, 1157772102221443092l, 1157772102624100382l, 1157772103081267200l, 1157772103567814727l, 1157772103873990811l, 1157772104268263525l, 1157772104561868882l, 1157772104918372462l, 1157772199369916498l, 1157772199785136208l, 1157772200103919678l, 1157772200464613426l, 1157772200795979816l, 1157772201106350110l, 1157772201425109143l, 1157772201689366578l, 1157772201999736982l, 1157772202347855922l, 1157772320417525820l, 1157772320753074346l, 1157772321096990750l, 1157772321587736677l, 1157772321944240158l, 1157772322388848761l, 1157772322749563131l, 1157772323089285251l, 1157772323395481661l, 1157772323743612998l, 1157772403406012456l, 1157772403703816242l, 1157772404047745055l, 1157772404437811311l, 1157772404827889885l, 1157772405134069790l, 1157772405461221386l, 1157772405767413800l, 1157772406241366127l, 1157772406597877840l,
		};
	}
	
	@Override
	public boolean isDownloadAvailable(int serverId) {
		switch(serverId) {
			case 0 : return isDownloadAvailableGitHub();
			case 1 : return isDownloadAvailableDiscord();
			default: return false;
		}
	}
	
	private boolean isDownloadAvailableGitHub() {
		try {
			GitHub gh = new GitHub("Uekra", "SoG", "main");
			gh.changeFileName("SoG.db1");
			return GitHub.isFileAvailable(gh);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean isDownloadAvailableDiscord() {
		try {
			Discord disc = new Discord(1157028068553150564l);
			disc.changeFileId(1157771494089298192l);
			disc.changeFileName("SoG.db1");
			return Discord.isFileAvailable(disc);
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
