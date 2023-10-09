package games;

import java.io.File;
import java.nio.file.Files;

public abstract class AbstractGame {
	
	// FrontEnd Data
	private int id;
	private String name;
	private String description;
	
	private String localGameFolderPath;
	
	public AbstractGame(int id, String name, String description, String localGameFolderPath) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.localGameFolderPath = localGameFolderPath;
	}
	
	// 0 == GitHub | 1 == Discord
	public abstract void download(int serverId) throws Exception;
	public abstract boolean isDownloadAvailable(int serverId);	
	public abstract boolean[] getAvailableDownloads();
	
	public abstract void install() throws Exception;
	
	public abstract void uninstall() throws Exception;
	
	public abstract void run() throws Exception;

	public boolean deleteDir(File file) {
		try {
			File[] contents = file.listFiles();
			if(contents != null) {
				for(File f : contents) {
					if(!Files.isSymbolicLink(f.toPath())) {
						deleteDir(f);
					}
				}
			}
			file.delete();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getLocalGameFolderPath() {
		return this.localGameFolderPath;
	}
	
	public boolean createLocalGameFolderPath() {
		return new File(this.localGameFolderPath).mkdir();
	}
}
