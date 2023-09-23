package games;

public abstract class AbstractGame {
	
	// FrontEnd Data
	private int id;
	private String name;
	private String description;
	
	public AbstractGame(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	
	public abstract void download() throws Exception;
	
	public abstract boolean isDownloadAvailable();

	public abstract void install() throws Exception;
	
	public abstract void uninstall() throws Exception;
	
	public abstract void run() throws Exception;

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
}
