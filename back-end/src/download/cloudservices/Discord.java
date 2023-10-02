package download.cloudservices;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import download.CloudStorageDownloader;

public class Discord extends CloudStorageDownloader {
	
	private long channelId;
	private long fileId;
	private String fileName;
	
	public Discord(long channelId) {
		this.channelId = channelId;
	}
	
	public static void downloadFile(Discord disc, String outputFilePath) throws IOException {
		String link = String.format("https://cdn.discordapp.com/attachments/%d/%d/%s", disc.getChannelId(), disc.getFileId(), disc.getFileName());
		downloadFromUrl(link, outputFilePath);
	}
	
	public static boolean isFileAvailable(Discord disc) throws Exception {
		String link = String.format("https://cdn.discordapp.com/attachments/%d/%d/%s", disc.getChannelId(), disc.getFileId(), disc.getFileName());
		
		URL url = new URL(link);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
		
		String contentType = connection.getContentType();
		
		if(contentType.equals("application/octet-stream"))
			return true;
		
		return false;
	}
	
	public long getChannelId() {
		return this.channelId;
	}
	
	public long getFileId() {
		return this.fileId;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void changeFileId(long fileId) {
		this.fileId = fileId;
	}
	
	public void changeFileName(String fileName) {
		this.fileName = fileName;
	}
}
