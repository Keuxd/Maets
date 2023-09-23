package download.cloudservices;

import java.net.URL;

import core.Main;
import download.CloudStorageDownloader;

public class GitHub extends CloudStorageDownloader {
	
	private String user;
	private String repository;
	private String branch;
	private String fileName;
	
	public GitHub(String user, String repository, String branch, String fileName) {
		this.user = user;
		this.repository = repository;
		this.branch = branch;
		this.fileName = fileName;
	}

	public static void downloadFile(GitHub gh, String outputFilePath) throws Exception {
		String link = String.format("https://github.com/%s/%s/raw/%s/%s", gh.getUser(), gh.getRepository(), gh.getBranch(), gh.getFileName());
		downloadFromUrl(link, outputFilePath);
	}
	
	public static boolean isFileAvailable(GitHub gh) throws Exception {
		String link = String.format("https://github.com/%s/%s/raw/%s/%s", gh.getUser(), gh.getRepository(), gh.getBranch(), gh.getFileName());
		
		URL url = new URL(link);
		long fileSize = url.openConnection().getContentLengthLong();
		System.out.println(fileSize);
		
		if(fileSize == -1 || fileSize == 221599)
			return false;
		
		return true;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getRepository() {
		return this.repository;
	}
	
	public String getBranch() {
		return this.branch;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void changeFileName(String fileName) {
		this.fileName = fileName;
	}

}